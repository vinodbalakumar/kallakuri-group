package com.example.kallakurigroup.utils;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Usage:
 * 
 * 1. Get the Foreground Singleton, passing a Context or Application object unless you
 * are sure that the Singleton has definitely already been initialised elsewhere.
 * 
 * 2.a) Perform a direct, synchronous check: Foreground.isForeground() / .isBackground()
 * 
 * or
 * 
 * 2.b) Register to be notified (useful in Service or other non-UI components):
 * 
 *   Foreground.Listener myListener = new Foreground.Listener(){
 *       public void onBecameForeground(){
 *           // ... whatever you want to do
 *       }
 *       public void onBecameBackground(){
 *           // ... whatever you want to do
 *       }
 *   }
 * 
 *   public void onCreate(){
 *      super.onCreate();
 *      Foreground.get(this).addListener(listener);
 *   }
 * 
 *   public void onDestroy(){
 *      super.onCreate();
 *      Foreground.get(this).removeListener(listener);
 *   }
 */


public class Foreground implements Application.ActivityLifecycleCallbacks {

    public static final long CHECK_DELAY = 500;
    public static final String TAG = Foreground.class.getName();

    Activity activity;

    public interface Listener {

        public void onBecameForeground();

        public void onBecameBackground();

    }

    private static Foreground instance;

    private boolean foreground = false, paused = true;
    private Handler handler = new Handler();
    private List<Listener> listeners = new CopyOnWriteArrayList<Listener>();
    private Runnable check;


    protected static LocationManager locationManager = null;
    protected static final int REQUEST_CHECK_SETTINGS = 100;

    /**
     * Its not strictly necessary to use this method - _usually_ invoking
     * get with a Context gives us a path to retrieve the Application and
     * initialise, but sometimes (e.g. in test harness) the ApplicationContext
     * is != the Application, and the docs make no guarantees.
     *
     * @param application
     * @return an initialised Foreground instance
     */

    public static Foreground init(Application application){
        if (instance == null) {
            instance = new Foreground();
            application.registerActivityLifecycleCallbacks(instance);
        }


        return instance;
    }

    public static Foreground get(Application application){
        if (instance == null) {
            init(application);
        }
        return instance;
    }

    public static Foreground get(Context ctx){
        if (instance == null) {
            Context appCtx = ctx.getApplicationContext();
            if (appCtx instanceof Application) {
                init((Application)appCtx);
            }
            throw new IllegalStateException(
                "Foreground is not initialised and " +
                "cannot obtain the Application object");
        }
        return instance;
    }

    public static Foreground get(){
        if (instance == null) {
            throw new IllegalStateException(
                "Foreground is not initialised - invoke " +
                "at least once with parameterised init/get");
        }
        return instance;
    }

    public boolean isForeground(){
        return foreground;
    }

    public boolean isBackground(){
        return !foreground;
    }

    public void addListener(Listener listener){
        listeners.add(listener);
    }

    public void removeListener(Listener listener){
        listeners.remove(listener);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        paused = false;
        boolean wasBackground = !foreground;
        foreground = true;

        this.activity= activity;

        if (check != null)
            handler.removeCallbacks(check);

        if (wasBackground){
            Log.i(TAG, "went foreground");
            for (Listener l : listeners) {
                try {
                    l.onBecameForeground();
                } catch (Exception exc) {
                    Log.e(TAG, "Listener threw exception!", exc);
                }
            }
           /* try {
                try {
                    new GoogleLocationApi(activity, activity);
                }catch (Exception e){
                    e.printStackTrace();
                }

                openService(activity);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }*/

        } else {
            Log.i(TAG, "still foreground");

        }


    }

/*
    void  openService(Context context){

        trackLoc(activity, activity);

        try {

          context.registerReceiver(gpsReceiver, new IntentFilter("android.location.PROVIDERS_CHANGED"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
*/
    @Override
    public void onActivityPaused(final Activity activity) {
        paused = true;

        if (check != null)
            handler.removeCallbacks(check);

        handler.postDelayed(check = new Runnable(){
            @Override
            public void run() {
                if (foreground && paused) {
                    foreground = false;
                    Log.i(TAG, "went background");
                    for (Listener l : listeners) {
                        try {
                            l.onBecameBackground();
                        } catch (Exception exc) {
                            Log.e(TAG, "Listener threw exception!", exc);
                        }
                    }

                 /*   Intent intent = new Intent(activity, FloatingOverMapIconService.class);
                    activity.stopService(intent);*/
                } else {
                    Log.i(TAG, "still foreground");

                  /*  try {

                        trackLoc(activity, activity);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }*/
                }

            }
        }, CHECK_DELAY);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

    @Override
    public void onActivityStarted(Activity activity) {}

    @Override
    public void onActivityStopped(Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

    @Override
    public void onActivityDestroyed(Activity activity) {}

   /* private BroadcastReceiver gpsReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {

                    if(isForeground()) {
                        trackLoc(context, (Activity) context);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };


    public static void displayLocationSettingsRequest(final Activity context) {

        try {
            GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(1000);
            locationRequest.setFastestInterval(1000 / 2);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(context, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackLoc(Context context, Activity act){

        if(locationManager == null) {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        }
        // getting GPS status
        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(!isGPSEnabled){
            displayLocationSettingsRequest(act);
        }
    }
*/

}