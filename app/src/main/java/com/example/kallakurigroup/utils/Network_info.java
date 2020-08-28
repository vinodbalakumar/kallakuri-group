package com.example.kallakurigroup.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*import com.facebook.network.connectionclass.ConnectionClassManager;
import com.facebook.network.connectionclass.ConnectionQuality;
import com.facebook.network.connectionclass.DeviceBandwidthSampler;*/

/**
 * Created by admin on 11-05-2016.
 */
public class Network_info /*implements ConnectionClassManager.ConnectionClassStateChangeListener*/{

  //  private ConnectionQuality mConnectionClass = ConnectionQuality.UNKNOWN;

    static ConnectivityManager connectivity;

   /* public static boolean isNetworkAvailable(Activity activity) {
         connectivity = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }*/

    public static boolean valid(String phoneno) {

        Pattern pattern = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher matcher = pattern.matcher(phoneno);

        return (matcher.find() && matcher.group().equals(phoneno));
    }

    public static boolean isNetworkAvailable(Context context) {
         connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                        //new
                        try {
                            Log.e("netState",info[i].getDetailedState()+","+info[i].getExtraInfo());

                            if (info[i].getDetailedState()== NetworkInfo.DetailedState.CONNECTED/*VERIFYING_POOR_LINK*/){
                                return true;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        //new

                       /* if(connectionQuality()==true){
                            return true;
                        }else {
                            return false;
                        }*/

                       //old comm return true;

                    }
                }
            }
        }
        return false;
    }

   /* public static boolean serviveNetworkAvailable(Service service) {
         connectivity = (ConnectivityManager)service.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }*/

   /* static String connectionQuality=null;

    @Override
    public void onBandwidthStateChange(ConnectionQuality bandwidthState) {
        mConnectionClass = bandwidthState;
        connectionQuality = mConnectionClass.toString();

    }

  static boolean connectionQuality(){

      boolean networkAvailable = true;

      switch (connectionQuality) {
          case "POOR":
              // double val1 = mConnectionClassManager.getDownloadKBitsPerSecond();
              networkAvailable = false;
              break;
          case "MODERATE":
              //double val2 = mConnectionClassManager.getDownloadKBitsPerSecond();
              networkAvailable = true;
              break;
          case "GOOD":
              // double val3 = mConnectionClassManager.getDownloadKBitsPerSecond();
              networkAvailable = true;
              break;
          case "EXCELLENT":
              // double val4 = mConnectionClassManager.getDownloadKBitsPerSecond();
              networkAvailable = true;
              break;
          case "UNKNOWN":
              //"Sorry we are getting nothing";
              networkAvailable = false;
              break;
      }

      return networkAvailable;
  }*/
}