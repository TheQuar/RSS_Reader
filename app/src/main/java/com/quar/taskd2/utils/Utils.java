package com.quar.taskd2.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;

public class Utils {

    public static boolean check_network(Context context) {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            try {
                InetAddress ipAddr = InetAddress.getByName("104.26.12.149");
                connected = !ipAddr.equals("");
            } catch (Exception e) {
                connected = false;
            }

        }
        return connected;
    }
}
