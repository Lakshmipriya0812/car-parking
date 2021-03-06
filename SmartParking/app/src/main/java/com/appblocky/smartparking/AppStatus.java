package com.appblocky.smartparking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class AppStatus {
    static Context context;

    private static AppStatus instance = new AppStatus();
    ConnectivityManager connectivityManager;
    NetworkInfo wifiInfo,mobileInfo;
    boolean connected = false;

    public static AppStatus getInstance(Context ctx){
        context=ctx.getApplicationContext();
        return instance;
    }


    public boolean isOnline(){
        try {
            connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();
            connected=networkInfo!=null && networkInfo.isAvailable() && networkInfo.isConnected();
            return connected;

        }
        catch (Exception e){
            System.out.println("CheckConnectivity Exception: "+e.getMessage());
            Log.v("Connectivity ",e.toString());
        }
        return connected;
    }
}
