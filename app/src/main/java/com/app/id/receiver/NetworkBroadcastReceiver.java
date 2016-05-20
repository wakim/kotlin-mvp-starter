package com.app.id.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import com.app.id.application.Application;

public class NetworkBroadcastReceiver extends BroadcastReceiver {

    Application app;
    ConnectivityManager connectivityManager;

    public NetworkBroadcastReceiver(Application app, ConnectivityManager connectivityManager) {
        this.app = app;
        this.connectivityManager = connectivityManager;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        app.setNetworkConnected(isNetworkConnected(), isWifiConnected());
    }

    public boolean isNetworkConnected() {
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    @SuppressWarnings("deprecation")
    public boolean isWifiConnected() {
        if (Build.VERSION.SDK_INT < 21) {
            NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return wifiInfo.isConnectedOrConnecting();
        } else {
            Network[] networks = connectivityManager.getAllNetworks();

            for (Network network : networks) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);

                if (networkInfo == null) {
                    continue;
                }

                if (networkInfo.getType() != ConnectivityManager.TYPE_WIFI) {
                    continue;
                }

                if (!networkInfo.isConnectedOrConnecting()) {
                    continue;
                }

                return true;
            }

            return false;
        }
    }
}
