package com.example.sqisoft.moldcreateapp.Activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sqisoft.moldcreateapp.Fragment.FragmentDrawing;
import com.example.sqisoft.moldcreateapp.Fragment.FragmentMain;
import com.example.sqisoft.moldcreateapp.Fragment.FragmentSelecting;
import com.example.sqisoft.moldcreateapp.Fragment.FragmentWaitng;
import com.example.sqisoft.moldcreateapp.Manager.DataManager;
import com.example.sqisoft.moldcreateapp.R;
import com.example.sqisoft.moldcreateapp.Util.FragmentUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements FragmentMain.OnFragmentInteractionListener,
        FragmentSelecting.OnFragmentInteractionListener, FragmentDrawing.OnFragmentInteractionListener,
        FragmentWaitng.OnFragmentInteractionListener, View.OnTouchListener, GestureDetector.OnGestureListener{

    long backKeyPressedTime = 0;
    private Toast toast;

    public MainActivity my = MainActivity.this;
    private String html = "";
    private Handler mHandler;

    private Socket socket;

    private BufferedReader networkReader;
    private BufferedWriter networkWriter;

    private String ip = "192.168.2.200"; // IP
    private int port = 6778; // PORT번호





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       DataManager.getInstance().setActivity(this);


        //프래그먼트 유틸(프래그먼트간 이동 공통 모듈) 초기화
        FragmentUtil.init(R.id.replaced_layout, getSupportFragmentManager());

        FragmentUtil.addFragment(new FragmentMain());

        mHandler = new Handler();


        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {

                        setSocket(ip, port);
                        checkUpdate.start();
                    //Your code goes here
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();




    }

    private void disconnectSocket(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void setSocket(String ip, int port) throws IOException {

        try {
            socket = new Socket(ip, port);
            networkWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            networkReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Log.w("Socket connection is success ", "*******************************************");
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }

    private Runnable showUpdate = new Runnable() {

        public void run() {
          Toast.makeText(getApplicationContext(), "Coming word: " + html, Toast.LENGTH_SHORT).show();
        }

    };

    private Thread checkUpdate = new Thread() {

        public void run() {
            try {
                String line;
                Log.w("ChattingStart", "Start Thread");
                while (true) {
                    Log.w("Chatting is running", "chatting is running");
                    line = networkReader.readLine();
                    html = line;
                    mHandler.post(showUpdate);
                }
            } catch (Exception e) {

            }
        }
    };

    public void sendToUnity(ImageView imageView){
        if(socket != null) {
            Bitmap bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap(); //String str = et.getText().toString();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] array = bos.toByteArray();

            OutputStream out = null;
            try {
                String sendingMessage = "Hello HanSu";
                out = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(out);
                dos.writeInt(array.length);
                dos.write(array, 0, array.length);
                Log.w("Sending Image", "*******************************************");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();

        //currentTimeMillis 현재시간이 버튼을 눌린 시간 + 2초 보다 흘럿다면 2초내 클릭 안한것임.
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis(); //backKeyPressedTime 버튼을 누른 시간을 입력
            toast = Toast.makeText(getApplicationContext(), "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();

            super.onBackPressed();
            return;
        }

        if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            finish();
            toast.cancel();
        }


        if(backStackCount == 1) finish();

        if (backStackCount > 0) {
            //  FragmentUtil.goBack();
            super.onBackPressed();

        }

    }



}
