package com.example.sqisoft.moldcreateapp.Manager;

import android.app.Activity;

import com.example.sqisoft.moldcreateapp.view.DrawingView;

/**
 * Created by SQISOFT on 2017-03-24.
 */

public class DataManager {

    private static DataManager instance = null;
    private Activity activity;
    private DrawingView mDrawingView;
    private static String mSeletedColor = "#F6402C";
    private String[] colorList = new String[20];

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

    public DrawingView getmDrawingView() {

        System.out.println("데이타 메니져 getmDrawingView. pos = 호출 ");
        return mDrawingView;
    }

    public void setmDrawingView(DrawingView mDrawingView) {
        this.mDrawingView = mDrawingView;
    }


}
