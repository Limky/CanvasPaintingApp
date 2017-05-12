package com.example.sqisoft.moldcreateapp.Activity;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqisoft.moldcreateapp.Fragment.FragmentDrawing;
import com.example.sqisoft.moldcreateapp.Fragment.FragmentMain;
import com.example.sqisoft.moldcreateapp.Fragment.FragmentSending;
import com.example.sqisoft.moldcreateapp.R;
import com.example.sqisoft.moldcreateapp.manager.ColorManager;
import com.example.sqisoft.moldcreateapp.manager.DataManager;
import com.example.sqisoft.moldcreateapp.util.FragmentUtil;
import com.tsengvn.typekit.TypekitContextWrapper;

public class MainActivity extends AppCompatActivity implements FragmentMain.OnFragmentInteractionListener, FragmentDrawing.OnFragmentInteractionListener,
        FragmentSending.OnFragmentInteractionListener, View.OnTouchListener, GestureDetector.OnGestureListener{

    long backKeyPressedTime = 0;
    private Toast toast;
    private String ip = "192.168.2.171"; // IP
    private int port = 6778; // PORT번호





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);



     //   setGlobalFont(this, getWindow().getDecorView());


       // getDPI();

        DataManager.getInstance().setActivity(this);
        DataManager.getInstance().setmContext(getApplicationContext());

        //프래그먼트 유틸(프래그먼트간 이동 공통 모듈) 초기화
        FragmentUtil.init(R.id.replaced_layout, getSupportFragmentManager());


        FragmentUtil.addFragment(new FragmentMain());

    }

    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }




    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }


    @Override
    public void onBackPressed(){
        System.out.println("( RemoteCameraActivity Back Event )");
        // DataManager.getInstance().getActivity().onBackPressed();
        backButtonFunction();

    }

    public void backButtonFunction(){
        ColorManager.getInstance().startFlag = true;
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
    //    Toast.makeText(getApplicationContext(), ""+backStackCount, Toast.LENGTH_SHORT).show();
        //currentTimeMillis 현재시간이 버튼을 눌린 시간 + 2초 보다 흘럿다면 2초내 클릭 안한것임.
        if(backStackCount == 1) finish();

        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis(); //backKeyPressedTime 버튼을 누른 시간을 입력
        //   toast = Toast.makeText(getApplicationContext(), "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        //    toast.show();

            super.onBackPressed();
            return;
        }

        if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
        //    finish();
       //     toast.cancel();
        }


        if (backStackCount > 0) {
            //  FragmentUtil.goBack();
            super.onBackPressed();

        }

    }


    private void getDPI(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int deviceWidth = displayMetrics.widthPixels;
        int deviceHeight = displayMetrics.heightPixels;
        // 꼭 넣어 주어야 한다. 이렇게 해야 displayMetrics가 세팅이 된다.

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int dipWidth  = (int) (120  * displayMetrics.density);
        int dipHeight = (int) (90 * displayMetrics.density);

        System.out.println("displayMetrics.density ( xhdpi ) : " + displayMetrics.density);
        System.out.println("deviceWidth : " + deviceWidth +", deviceHeight : "+deviceHeight);

    }

    public static void setGlobalFont(Context context, View view){
        if (view != null) {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                int len = vg.getChildCount();
                for (int i = 0; i < len; i++) {
                    View v = vg.getChildAt(i);
                    if (v instanceof TextView) {
                        ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf"));
                    }
                    setGlobalFont(context, v);
                }
            }
        } else {

        }

    }


}