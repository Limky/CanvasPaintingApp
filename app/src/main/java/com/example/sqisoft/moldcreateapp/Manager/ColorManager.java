package com.example.sqisoft.moldcreateapp.Manager;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

/**
 * Created by SQISOFT on 2017-04-06.
 */

public class ColorManager {
    private static ColorManager instance = null;
    private static String mSeletedColor = "#F6402C";
    private String[] mColorList = new String[20];

    public ColorManager(){
        settingColorList();
    }

    public static ColorManager getInstance(){
        if(instance == null){
            instance = new ColorManager();
        }
        return instance;
    }



    public GradientDrawable getSelectedColor(int pos){


        this.mSeletedColor = mColorList[pos];

        final GradientDrawable selected = new GradientDrawable();
        selected.setShape(GradientDrawable.OVAL);
        selected.setColor(Color.parseColor(mSeletedColor));
        selected.setSize(100, 100);
        selected.setStroke(10, Color.parseColor("#D2D1D2"));

   //     System.out.println("getSelectedColor. pos = "+mSeletedColor);

        DataManager.getInstance().getmDrawingView().setupDrawing();
        return selected;

    }

    public GradientDrawable getUnselectedColor(int pos){

        this.mSeletedColor = mColorList[pos];

        final GradientDrawable unselected = new GradientDrawable();
        unselected.setShape(GradientDrawable.OVAL);
        unselected.setColor(Color.parseColor(mSeletedColor));
        unselected.setSize(100, 100);

     //  System.out.println("getUnselectedColor. pos = "+mSeletedColor);

        DataManager.getInstance().getmDrawingView().setupDrawing();
        return unselected;

    }


    public String getmSeletedColor() {
        return mSeletedColor;
    }
    public static void setmSeletedColor(String mSeletedColor) {
        ColorManager.mSeletedColor = mSeletedColor;
    }


    public void settingColorList(){

        mColorList[0] ="#F6402C";//red
        mColorList[1] ="#EB1460";//pink
        mColorList[2] ="#9C1AB1";//purple
        mColorList[3] ="#6633B9";//deep_purple
        mColorList[4] ="#3D4DB7";//indigo
        mColorList[5] ="#1093F5";//blue
        mColorList[6] ="#00A6F6";//light_blue
        mColorList[7] ="#00BBD5";//cyan
        mColorList[8] ="#009687";//teal
        mColorList[9] ="#46AF4A";//green
        mColorList[10] ="#88C440";//light_green
        mColorList[11] ="#CCDD1E";//lime
        mColorList[12] ="#FFEC16";//yellow
        mColorList[13] ="#FFC100";//amber
        mColorList[14] ="#FF9800";//orange
        mColorList[15] ="#FF5505";//deep_orange
        mColorList[16] ="#7A5547";//brown
        mColorList[17] ="#9D9D9D";//grey
        mColorList[18] ="#5E7C8B";//blue_grey
        mColorList[19] ="#000000";//black

    }


}
