/*
package com.example.kallakurigroup.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.util.Date;

public class GoogleLocationApi implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    boolean firedLocation = false;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String PREFERENCE = "KALLAKURI";
    Thread thread = null;

    private static final String TAG = "LocationActivity";
    private static long INTERVAL = 10000 * 30;
    private static long FASTEST_INTERVAL = 10000 * 10;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;

    Double accuracy = 0.0;

    Context context;
    Activity act;

    public GoogleLocationApi(Context context, Activity act) {
        this.context = context;
        this.act = act;

        registerPreferences();

        checkGooglePlayService();

    }

    public GoogleLocationApi() {
       stopLocationUpdates();
    }


    void registerPreferences(){
        try {
            sharedpreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void createLocationRequest() {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    void checkGooglePlayService() {

        if (isGooglePlayServicesAvailable(context, act)) {
            createLocationRequest();
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            mGoogleApiClient.connect();
        }

    }


    void disconnect() {
        mGoogleApiClient.disconnect();
    }

    private boolean isGooglePlayServicesAvailable(Context context, Activity act) {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, act, 0).show();
            return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }else {

            try {
               Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient);

                if (mLastLocation != null) {
                    editor.putString("Latitude", mLastLocation.getLatitude() + "");
                    editor.putString("Longitude", mLastLocation.getLatitude() + "");
                    editor.commit();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "Location update started ..............: ");

     }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            Log.d(TAG, "Firing onLocationChanged..............................................");
            mCurrentLocation = location;
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            accuracy = Double.valueOf(location.getAccuracy());

            firedLocation = true;
            updateUI();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private LatLng updateUI() {

        if(firedLocation) {

            firedLocation = false;

            editor.putString("Latitude", mCurrentLocation.getLatitude() + "");
            editor.putString("Longitude", mCurrentLocation.getLongitude() + "");
            editor.commit();
        }
        return new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        Log.d(TAG, "Location update stopped .......................");
    }

}*/
