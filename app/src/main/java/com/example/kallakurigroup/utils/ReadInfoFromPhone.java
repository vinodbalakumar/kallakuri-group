package com.example.kallakurigroup.utils;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;

/**
 * Created by android on 3/3/17.
 */

public class ReadInfoFromPhone {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;


    final long MIN_TIME_BW_UPDATES = 1000 * 60;
    ConnectivityManager connectivityManager;
    NetworkInfo activeNetworkInfo;
    PackageInfo pInfo;
    public TelephonyManager telephonyManager;
    String appversion;
    WifiManager wimanager;
    String simstate = "";
    String networktyp = "";
    String phntype = "";
    String Netoptr = "";
    public String ssdata;
    String mDeviceID;

    String imsiSIM1, imsiSIM2;

    boolean isSIM1Ready, isSIM2Ready, isDualSIM;

    public int signalStrengthValue;
     /*
     * SIM type constants
     */

    String SIM_ABSENT = "Absent";
    String SIM_READY = "Ready";
    String SIM_PIN_REQUIRED = "PIN required";
    String SIM_PUK_REQUIRED = "PUK required";
    String SIM_NETWORK_LOCKED = "Network locked";
    String SIM_UNKNOWN = "Unknown";

    /*
     * Network type constants
     */
    String NETWORK_CDMA = "CDMA: Either IS95A or IS95B (2G)";
    String NETWORK_EDGE = "EDGE (2.75G)";
    String NETWORK_GPRS = "GPRS (2.5G)";
    String NETWORK_UMTS = "UMTS (3G)";
    String NETWORK_EVDO_0 = "EVDO revision 0 (3G)";
    String NETWORK_EVDO_A = "EVDO revision A (3G - Transitional)";
    String NETWORK_EVDO_B = "EVDO revision B (3G - Transitional)";
    String NETWORK_1X_RTT = "1xRTT  (2G - Transitional)";
    String NETWORK_HSDPA = "HSDPA (3G - Transitional)";
    String NETWORK_HSUPA = "HSUPA (3G - Transitional)";
    String NETWORK_HSPA = "HSPA (3G - Transitional)";
    String NETWORK_IDEN = "iDen (2G)";
    String NETWORK_LTE = "LTE (4G)";
    String NETWORK_EHRPD = "EHRPD (3G)";
    String NETWORK_HSPAP = "HSPAP (3G)";
    String NETWORK_UNKOWN = "Unknown";

    /*
        * Phone type constants
        */
    String PHONE_CDMA = "CDMA";
    String PHONE_GSM = "GSM";
    String PHONE_SIP = "SIP";
    String PHONE_NONE = "No radio";
    public String netInfo = "";
    public String mobTyp = "";

    public static String android_id;

    ActivityManager manager;
    myPhoneStateListener psListener;

    Context context;

    public ReadInfoFromPhone(Context context) {

        this.context = context;

        manager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);

        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mDeviceID = telephonyManager.getDeviceId();
        psListener = new myPhoneStateListener();

        telephonyManager.listen(psListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

          android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        TelephonyInfo telephonyInfo = TelephonyInfo.getInstance(context);
        isDualSIM = telephonyInfo.isDualSIM();
        if(isDualSIM){
            isSIM1Ready = telephonyInfo.isSIM1Ready();
            isSIM2Ready = telephonyInfo.isSIM2Ready();
            if(isSIM1Ready){
                imsiSIM1 = telephonyInfo.getImsiSIM1();
                imsiSIM2 = telephonyInfo.getImsiSIM2();
            }
        }


        wimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        try {
             pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appversion = pInfo.versionName + "-" + String.valueOf(pInfo.versionCode);
            ClassicSingleton.getInstance().VersionCode = appversion;

        } catch (Exception e) {

            e.printStackTrace();

            ClassicSingleton.getInstance().VersionCode = "0.1 - 1";
        }

        netInfo = checkNet();
        mobTyp = checkMobTyp();
    }


    private String mapSimStateToName(int simState) {
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                return SIM_ABSENT;
            case TelephonyManager.SIM_STATE_READY:
                return SIM_READY;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                return SIM_PIN_REQUIRED;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                return SIM_PUK_REQUIRED;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                return SIM_NETWORK_LOCKED;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                return SIM_UNKNOWN;

            default:
                // shouldn't happen.
                return null;
        }
    }

   String mapNetworkTypeToName(int networkType) {

        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return NETWORK_CDMA;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return NETWORK_EDGE;
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return NETWORK_EDGE;
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return NETWORK_UMTS;
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return NETWORK_EVDO_0;
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return NETWORK_EVDO_A;
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return NETWORK_EVDO_B;
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return NETWORK_1X_RTT;
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return NETWORK_HSDPA;
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return NETWORK_HSPA;
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return NETWORK_HSUPA;
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORK_IDEN;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NETWORK_LTE;
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return NETWORK_EHRPD;
//	            case TelephonyManager.NETWORK_TYPE_HSPAP:
            //              return NETWORK_HSPAP;
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
            default:
                return NETWORK_UNKOWN;
        }
    }

    public String checkNet() {
        boolean netInfo;
        String netMsg = "internet not available";
         connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
         activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        netInfo = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        NetworkInfo[] netTypInfo = connectivityManager.getAllNetworkInfo();
        if (netInfo) {
            for (NetworkInfo ni : netTypInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected()) {
                        netMsg = ni.getTypeName();
                    }
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected()) {
                        netMsg = ni.getTypeName();
                    }
             }

        } else {
            netMsg = "no internet";
        }

        return netMsg;
    }

    public String checkMobTyp() {

        simstate = "SIM State: " + mapSimStateToName(telephonyManager.getSimState());


        networktyp = " Network Type: " + mapNetworkTypeToName(telephonyManager.getNetworkType());


        phntype = " Phone Type: " + mapDeviceTypeToName(telephonyManager.getPhoneType());


        Netoptr = " Network Operator: " + telephonyManager.getNetworkOperatorName();
        String mobTyp = simstate + networktyp + phntype + Netoptr;
        return mobTyp;
    }

    String mapDeviceTypeToName(int device) {

        switch (device) {
            case TelephonyManager.PHONE_TYPE_CDMA:
                return PHONE_CDMA;
            case TelephonyManager.PHONE_TYPE_GSM:
                return PHONE_GSM;
            case TelephonyManager.PHONE_TYPE_SIP:
                return PHONE_SIP;
            case TelephonyManager.PHONE_TYPE_NONE:
                return PHONE_NONE;
            default:
                // shouldn't happen.
                return null;
        }
    }


    class myPhoneStateListener extends PhoneStateListener {


        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            if (signalStrength.isGsm()) {

                ssdata = signalStrength.toString();

                if (signalStrength.getGsmSignalStrength() != 99) {
                    signalStrengthValue = signalStrength.getGsmSignalStrength() * 2 - 113;
                } else {
                    signalStrengthValue = signalStrength.getGsmSignalStrength();
                }

            } else {
                signalStrengthValue = signalStrength.getCdmaDbm();
            }

        }
    }

    //free memory (GC) manually
    void unregister(){
        try {
            telephonyManager = null;
            wimanager = null;
            manager = null;
            psListener = null;
            connectivityManager = null;
            telephonyManager = null;
            context = null;
            activeNetworkInfo = null;
        }catch (Exception e){
            e.printStackTrace();
        }


    }


}
