package com.example.sqisoft.moldcreateapp.manager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sqisoft.moldcreateapp.R;
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
    private Context mContext;

    public static String API_SERVER_URL = "http://192.168.2.200:8000";
    public static int nettype = 30; //임시 디바이스 정보 유니티에서 요청한 디바이스 타입정보.
    public static String deviceCode = "SMA-60000";
    public static String  targetDeviceType = "STEP011";


    public Typeface notoOtf;
    public Typeface gothamOtf;

    private ImageView mSeletecPaletteColorView;
    private int seletedMoldIndex;

    private boolean wifiConnect = false;

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



    public Typeface getNotoOtf() {
        return notoOtf;
    }

    public void setNotoOtf(Typeface notoOtf) {
        this.notoOtf = notoOtf;
    }
    public Typeface getGothamOtf() {
        return gothamOtf;
    }

    public void setGothamOtf(Typeface gothamOtf) {
        this.gothamOtf = gothamOtf;
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

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public ImageView getmSeletecPaletteColorView() {
        return mSeletecPaletteColorView;
    }

    public void setmSeletecPaletteColorView(ImageView mSeletecPaletteColorView) {
        this.mSeletecPaletteColorView = mSeletecPaletteColorView;
    }

    public int getSeletedMoldIndex() {
        return seletedMoldIndex;
    }

    public void setSeletedMoldIndex(int seletedMoldIndex) {
        this.seletedMoldIndex = seletedMoldIndex;
    }

    public void setEraserButton(){
        mEraserButton.setBackgroundResource(R.drawable.palette_20_n);
    }

    public Button mEraserButton;
    public void setEraser(Button eraserButton) {
        this.mEraserButton = eraserButton;
    }

    public boolean isWifiConnect() {
        return wifiConnect;
    }

    public void setWifiConnect(boolean wifiConnect) {
        this.wifiConnect = wifiConnect;
    }


}
