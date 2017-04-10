package com.example.sqisoft.moldcreateapp.Manager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.TextView;

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
    private Bitmap mCapturingBitmap;

    private TextView text_X,text_Y;

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

        //System.out.println("데이타 메니져 getmDrawingView. pos = 호출 ");
        return mDrawingView;
    }

    public void setmDrawingView(DrawingView mDrawingView) {
        this.mDrawingView = mDrawingView;
    }

    public Bitmap getmCapturingBitmap() {
        return mCapturingBitmap;
    }

    public void setmCapturingBitmap(Bitmap mCapturingBitmap) {
        this.mCapturingBitmap = mCapturingBitmap;
    }

    public void setTextXY(TextView x,TextView y){
        this.text_X = x;
        this.text_Y = y;
    }
    public TextView getText_X(){
        return this.text_X;
    }
    public TextView getText_Y(){
        return this.text_Y;
    }

}
