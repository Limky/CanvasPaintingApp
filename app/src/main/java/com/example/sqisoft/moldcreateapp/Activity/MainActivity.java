package com.example.sqisoft.moldcreateapp.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqisoft.moldcreateapp.Fragment.FragmentDrawing;
import com.example.sqisoft.moldcreateapp.Fragment.FragmentMain;
import com.example.sqisoft.moldcreateapp.Fragment.FragmentSending;
import com.example.sqisoft.moldcreateapp.R;
import com.example.sqisoft.moldcreateapp.manager.ColorManager;
import com.example.sqisoft.moldcreateapp.manager.DataManager;
import com.example.sqisoft.moldcreateapp.moldutil.FragmentUtil;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements FragmentMain.OnFragmentInteractionListener, FragmentDrawing.OnFragmentInteractionListener,
        FragmentSending.OnFragmentInteractionListener, View.OnTouchListener, GestureDetector.OnGestureListener{

    long backKeyPressedTime = 0;
    private Toast toast;
     private static int CHECKTIME = 10000 * 6;

    private static  String CLOSING = "18:00";
    private static  String OPEN = "08:30";
    public int appFinish_touchSum = 0;
    public int timeConfig_toushSum = 0;

    private ImageView mBlind_view;

    Timer mTimer;

    @Override
    protected void onPause() {
        super.onPause();

    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        Log.d("Focus debug", "Focus changed !");

        if(!hasFocus) {
            Log.d("Focus debug", "Lost focus !");

            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);


        }

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int X = (int) event.getX();
        int Y = (int) event.getY();

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    break;

                case MotionEvent.ACTION_MOVE:

                    break;
                case MotionEvent.ACTION_UP:
                    if(Y < 100 && X < 100) {
                    appFinish_touchSum++;
                    if(appFinish_touchSum ==30 ) {
                        ActivityCompat.finishAffinity(this);

                    }
                    }else if(Y < 100 && X > 1820){
                        timeConfig_toushSum++;
                        if(timeConfig_toushSum >= 30 ) {
                           showTimeConfigDialog();
                        }
                    }

                    break;

                default:
                    return false;
            }

            onWindowFocusChanged(true);
        //    Toast.makeText(this, "ACTION_DOWN AT COORDS " + "X: " + X + " Y: " + Y, Toast.LENGTH_SHORT).show();


            if(closingTimeFlag) {

                CHECKTIME = 10000 * 30 * 2;
                displayBrightness(100);
            }



              return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
     //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);

        mBlind_view = (ImageView) findViewById(R.id.blind_view);


        DataManager.getInstance().setActivity(this);
        DataManager.getInstance().setmContext(getApplicationContext());

        //프래그먼트 유틸(프래그먼트간 이동 공통 모듈) 초기화
        FragmentUtil.init(R.id.replaced_layout, getSupportFragmentManager());

        Typeface gothamtf = Typeface.createFromAsset(getAssets(),  "fonts/GothamBoldRegular.ttf");
        Typeface nototf = Typeface.createFromAsset(getAssets(), "fonts/NotoSansCJKkr-Medium.otf");

        DataManager.getInstance().setNotoOtf(gothamtf);
        DataManager.getInstance().setNotoOtf(nototf);


        MainTimerTask timerTask = new MainTimerTask();
        mTimer = new Timer();
        mTimer.schedule(timerTask, 5000, CHECKTIME);



        FragmentUtil.addFragment(new FragmentMain());

    }

    private void showTimeConfigDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.time_dialog_layout, null);
        alertDialog.setView(view);

         final EditText open_edit = (EditText) view.findViewById(R.id.opening_input);
         final EditText clos_edit = (EditText) view.findViewById(R.id.closing_input);

        open_edit.setHint("현재 저장된 시작 시간 : "+OPEN+"   ex) 08:30");
        clos_edit.setHint("현재 저장된 종료 시간 : "+CLOSING+"   ex) 19:30");

        /* When positive (yes/ok) is clicked */
        alertDialog.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                String open = open_edit.getText().toString();
                String close = clos_edit.getText().toString();

               // Toast.makeText(MainActivity.this, "open_edit"+open+"\nclos_edit"+close, Toast.LENGTH_SHORT).show();

                if(checkInvailedTime(open, close)){
                    Toast.makeText(MainActivity.this, "시간이 설정되었습니다. :)", Toast.LENGTH_SHORT).show();

                    Toast.makeText(MainActivity.this, "오픈시간  : "+OPEN+"\n종료시간  : "+CLOSING, Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(MainActivity.this, "시간을 잘못입력하셨습니다. :(", Toast.LENGTH_SHORT).show();

                }
                timeConfig_toushSum = 0;
                dialog.cancel(); // Your custom code
            }
        });

        /* When negative (No/cancel) button is clicked*/
        alertDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "취소했습니다.", Toast.LENGTH_SHORT).show();
                timeConfig_toushSum = 0;
              //  finish(); // Your custom code
                dialog.cancel(); // Your custom code
            }
        });
        alertDialog.show();
    }


    private Boolean checkInvailedTime(String open, String close){

        Boolean vaildOpenFlag = false;
        Boolean vaildClosFlag = false;

        String re1="((?:(?:[0-1][0-9])|(?:[2][0-3])|(?:[0-9])):(?:[0-5][0-9])(?::[0-5][0-9])?(?:\\s?(?:am|AM|pm|PM))?)";	// HourMinuteSec 1

        Pattern p = Pattern.compile(re1,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher openMatch = p.matcher(open);
        Matcher closeMatch = p.matcher(close);
        if (openMatch.find())
        {
            OPEN    = open;
            vaildOpenFlag =  true;
        }
        if (closeMatch.find())
        {
            CLOSING = close;
            vaildClosFlag = true;
        }

        if(vaildOpenFlag || vaildClosFlag){

            return true;
        }

        return false;

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
        // DataManager.getInstance().getActivity().onBackPressed();
        backButtonFunction();

    }

    public void backButtonFunction(){
        ColorManager.getInstance().startFlag = true;
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
    //    Toast.makeText(getApplicationContext(), ""+backStackCount, Toast.LENGTH_SHORT).show();
        //currentTimeMillis 현재시간이 버튼을 눌린 시간 + 2초 보다 흘럿다면 2초내 클릭 안한것임.
        if(backStackCount == 1) return;

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

    Boolean closingTimeFlag = false;
    Date nowTime = null;
    Date colsingTime = null;
    Date openTime = null;
    int closingCompare, openCompare;
    boolean displayClosingTime = false;
    private Handler mHandler = new Handler();
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            Date rightNow = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            String dateString = formatter.format(rightNow);

            try {
                Date nowTime = formatter.parse(dateString);
                colsingTime = formatter.parse(CLOSING);
                openTime = formatter.parse(OPEN);
                closingCompare = nowTime.compareTo(colsingTime);
                openCompare = nowTime.compareTo(openTime);

            } catch (ParseException e) {
                e.printStackTrace();
            }


            if( closingCompare > 0 ){
                //현재시간이 종료시간 이후인 경우.
                closingTimeFlag = true;
                displayBrightness(0);
            //   Toast.makeText(getApplicationContext(), "종료시간 이 후인 경우 ", Toast.LENGTH_SHORT).show();
            }else if(closingCompare < 0 ){
                //현재시간이 종료시간 전 일 경우.
              // Toast.makeText(getApplicationContext(), "종료시간 이 전인 경우", Toast.LENGTH_SHORT).show() ;

                if(openCompare < 0 ){
                    // 이 전
                    //새벽일 경우
                    //종료시간 전이지만, 오픈시간 전이기 때문에 새벽시간임.
                //  Toast.makeText(getApplicationContext(), "종료시간 전이지만, 오픈시간 전이기 때문에 새벽시간임.", Toast.LENGTH_SHORT).show();
                    closingTimeFlag = true;
                    displayBrightness(0);
                }else if(openCompare > 0){
                    // 이 후
                    //오픈시간 이후이면서 종료시간 전인 경우 -> 즉 오픈시간과 종료시간 사이
                  //Toast.makeText(getApplicationContext(), "즉 오픈시간과 종료시간 사이", Toast.LENGTH_SHORT).show();
                    closingTimeFlag = false;
                    CHECKTIME = 10000 * 6;
                    displayBrightness(100);

                }

            }

        }
    };

    class MainTimerTask extends TimerTask {
        public void run() {
            mHandler.post(mUpdateTimeTask);
        }
    }

    private void displayBrightness(int brightness){
        if(brightness == 100){
            mBlind_view.setVisibility(View.INVISIBLE);

            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        }else{
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);

            mBlind_view.setVisibility(View.VISIBLE);
        }
        Settings.System.putInt(getContentResolver(),"screen_brightness",brightness);
        WindowManager.LayoutParams myLayoutParams =  getWindow().getAttributes();
        myLayoutParams.screenBrightness = brightness;
        getWindow().setAttributes(myLayoutParams);

    }



}