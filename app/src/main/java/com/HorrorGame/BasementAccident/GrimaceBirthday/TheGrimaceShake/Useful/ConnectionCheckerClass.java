package com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.Useful;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionCheckerClass {

    public static boolean isMyGameConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null) {
                for (int i = 0; i < networkInfo.length; i++) {
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
