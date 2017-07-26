package com.example.sqisoft.moldcreateapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.example.sqisoft.moldcreateapp.manager.DataManager;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by SQI on 2017-06-20.
 */

public class WifiReceiver extends BroadcastReceiver{

    ConnectivityManager cm = (ConnectivityManager) DataManager.getInstance().getmContext().getSystemService(CONNECTIVITY_SERVICE);
    NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


    @Override
    public void onReceive(Context context, Intent intent) {
//
//        if (wifi.isConnected()) {
//            // Your code here
//            Toast.makeText(context, "와이파이 연결 성공", Toast.LENGTH_SHORT).show();
//            DataManager.getInstance().setWifiConnect(true);
//        }else{
//            Toast.makeText(context, "와이파이 실패 성공", Toast.LENGTH_SHORT).show();
//            DataManager.getInstance().setWifiConnect(false);
//
//        }


    }


}
