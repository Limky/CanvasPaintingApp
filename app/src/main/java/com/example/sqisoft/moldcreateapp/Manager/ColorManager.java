package com.example.sqisoft.moldcreateapp.manager;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;

import com.example.sqisoft.moldcreateapp.R;

/**
 * Created by SQISOFT on 2017-04-06.
 */

public class ColorManager {
    private static ColorManager instance = null;
    private static String mSeletedColor = "#F6402C";
    private String[] mColorList = new String[20];
    private int[] mColorPaletteList = new int[21];
    public static Boolean startFlag = true;
    public static Boolean startFlagBug = true;

    public ColorManager(){
        settingColorList();
    }

    public static ColorManager getInstance(){
        if(instance == null){
            instance = new ColorManager();
        }
        return instance;
    }



    public LayerDrawable getSelectedColor(int pos){


        this.mSeletedColor = mColorList[pos];

        LayerDrawable layerDrawable = (LayerDrawable) DataManager.getInstance().getActivity().getResources().getDrawable(R.drawable.colorpicker_selected);

        GradientDrawable selected1 = (GradientDrawable)layerDrawable.findDrawableByLayerId(R.id.shape_1);
        selected1.setColor(Color.TRANSPARENT);
        selected1.setStroke(3,Color.parseColor(mSeletedColor));

        GradientDrawable selected2 = (GradientDrawable)layerDrawable.findDrawableByLayerId(R.id.shape_2);
        selected2.setColor(Color.parseColor(mSeletedColor));
        selected2.setSize(57,57);

        System.out.println("getSelectedColor. pos = "+mSeletedColor);

        DataManager.getInstance().getmSeletecPaletteColorView().setBackgroundResource(mColorPaletteList[pos]);

        //     System.out.println("getSelectedColor. pos = "+mSeletedColor);

        DataManager.getInstance().getmDrawingView().setupDrawing();

        return layerDrawable;

    }

    public GradientDrawable getUnselectedColor(int pos){

        String UnselectedColor = mColorList[pos];

        if(startFlag){
        if(pos ==19){
            this.mSeletedColor = "#311B92";
            startFlag = !startFlag;
        }}

        final GradientDrawable unselected = new GradientDrawable();
        unselected.setShape(GradientDrawable.OVAL);
        unselected.setColor(Color.parseColor(UnselectedColor));
        unselected.setSize(70, 70);

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

        mColorList[0] ="#ff2929";//red
        mColorList[1] ="#ff8124";//pink
        mColorList[2] ="#ffdc1d";//purple
        mColorList[3] ="#fffe8a";//deep_purple
        mColorList[4] ="#96ef4e";//indigo
        mColorList[5] ="#3636fe";//blue
        mColorList[6] ="#3681fe";//light_blue
        mColorList[7] ="#3dbdff";//cyan
        mColorList[8] ="#0cd18e";//teal
        mColorList[9] ="#0eba12";//green
        mColorList[10] ="#8636fe";//light_green
        mColorList[11] ="#b536fe";//lime
        mColorList[12] ="#ee36fe";//yellow
        mColorList[13] ="#ff53b4";//amber
        mColorList[14] ="#ff8f8f";//orange
        mColorList[15] ="#000000";//deep_orange
        mColorList[16] ="#403f3f";//brown
        mColorList[17] ="#6e6e6e";//grey
        mColorList[18] ="#ffffff";//blue_grey
        mColorList[19] ="#000000";//black



        mColorPaletteList[0] = R.drawable.color_01;
        mColorPaletteList[1] = R.drawable.color_02;
        mColorPaletteList[2] = R.drawable.color_03;
        mColorPaletteList[3] = R.drawable.color_04;
        mColorPaletteList[4] = R.drawable.color_05;
        mColorPaletteList[5] = R.drawable.color_06;
        mColorPaletteList[6] = R.drawable.color_07;
        mColorPaletteList[7] = R.drawable.color_08;
        mColorPaletteList[8] = R.drawable.color_09;
        mColorPaletteList[9] = R.drawable.color_10;
        mColorPaletteList[10] = R.drawable.color_11;
        mColorPaletteList[11] = R.drawable.color_12;
        mColorPaletteList[12] = R.drawable.color_13;
        mColorPaletteList[13] = R.drawable.color_14;
        mColorPaletteList[14] = R.drawable.color_15;
        mColorPaletteList[15] = R.drawable.color_16;
        mColorPaletteList[16] = R.drawable.color_17;
        mColorPaletteList[17] = R.drawable.color_18;
        mColorPaletteList[18] = R.drawable.color_19;
        mColorPaletteList[19] = R.drawable.color_20;


    }


}
