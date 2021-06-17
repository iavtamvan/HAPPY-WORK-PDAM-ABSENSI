package com.pdamkotasmg.goodday.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.net.InetAddress;

public class Connectivity {
    private String connectionType;
    private boolean connectionFast;

    public Connectivity(String connectionType, boolean connectionFast) {
        this.connectionType = connectionType;
        this.connectionFast = connectionFast;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public boolean isConnectionFast() {
        return connectionFast;
    }

    /**
     * Get the network info
     *
     * @param context
     * @return
     */
    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }


    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            if (!ipAddr.equals("")){
                Log.d("debug", "isInternetAvailable: tidak aktif");
            } else {
                Log.d("debug", "isInternetAvailable: tidak aktif");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if there is any connectivity
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        boolean connectStatus = true;
        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ConnectivityManager ConnectionManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            connectStatus = true;
            editor.putString(Config.SHARED_STATUS_CONNECTION, "online");
            editor.apply();
        }
        else {
            connectStatus = false;
            editor.putString(Config.SHARED_STATUS_CONNECTION, "offline");
            editor.apply();
        }
        return connectStatus;
    }

    /**
     * Check if there is any connectivity to a Wifi network
     *
     * @param context
     * @return
     */
    public static boolean isConnectedWifi(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Check if there is any connectivity to a mobile network
     *
     * @param context
     * @return
     */
    public static boolean isConnectedMobile(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * Check if there is fast connectivity
     *
     * @param context
     * @return
     */
//    public static boolean isConnectedFast(Context context) {
//        NetworkInfo info = Connectivity.getNetworkInfo(context);
//        return (info != null && info.isConnected() && Connectivity.isConnectionFast(info.getType(), info.getSubtype(), context));
//    }

    /**
     * Check if the connection is fast
     *
     * @return
     */
    public static Connectivity isConnectionFast(Context context) {
        String typeConnection;
        int type;
        int subType;
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        type = info.getType();
        subType = info.getSubtype();
        if (type == ConnectivityManager.TYPE_WIFI) {
            WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo connectionInfo = wm.getConnectionInfo();
            typeConnection = connectionInfo.getSSID().replace("\"", "");
            return new Connectivity(typeConnection, true);
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    typeConnection = "1xRTT";
                    return new Connectivity(typeConnection, false); // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    typeConnection = "CDMA";
                    return new Connectivity(typeConnection, false); // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    typeConnection = "EDGE";
                    return new Connectivity(typeConnection, false); // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    typeConnection = "EVDO_0";
                    return new Connectivity(typeConnection, true); // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    typeConnection = "EVDO_A";
                    return new Connectivity(typeConnection, true); // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    typeConnection = "GPRS";
                    return new Connectivity(typeConnection, false); // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    typeConnection = "HSDPA";
                    return new Connectivity(typeConnection, true); // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    typeConnection = "HSPA";
                    return new Connectivity(typeConnection, true); // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    typeConnection = "HSUPA";
                    return new Connectivity(typeConnection, true); // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    typeConnection = "UMTS";
                    return new Connectivity(typeConnection, true); // ~ 400-7000 kbps
                /*
                 * Above API level 7, make sure to set android:targetSdkVersion
                 * to appropriate level to use these
                 */
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    typeConnection = "EHRPD";
                    return new Connectivity(typeConnection, true); // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    typeConnection = "EVDO_B";
                    return new Connectivity(typeConnection, true); // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    typeConnection = "HSPAP";
                    return new Connectivity(typeConnection, true); // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    typeConnection = "IDEN";
                    return new Connectivity(typeConnection, false); // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    typeConnection = "LTE";
                    return new Connectivity(typeConnection, true); // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    typeConnection = "UNKNOWN";
                    return new Connectivity(typeConnection, false);
            }
        } else {
            typeConnection = null;
            return new Connectivity(typeConnection, false);
        }
    }

}