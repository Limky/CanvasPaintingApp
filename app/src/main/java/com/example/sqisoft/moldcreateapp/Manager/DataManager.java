package com.example.sqisoft.moldcreateapp.Manager;

import android.app.Activity;

/**
 * Created by SQISOFT on 2017-03-24.
 */

public class DataManager {

    private static DataManager instance = null;
    private Activity activity;

    private DataManager(){

    }

    public static DataManager getInstance(){
        if(instance == null){
            instance = new DataManager();
        }
        return instance;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
