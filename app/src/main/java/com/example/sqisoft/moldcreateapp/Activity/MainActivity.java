package com.example.sqisoft.moldcreateapp.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.sqisoft.moldcreateapp.Fragment.FragmentDrawing;
import com.example.sqisoft.moldcreateapp.Fragment.FragmentMain;
import com.example.sqisoft.moldcreateapp.Fragment.FragmentSelecting;
import com.example.sqisoft.moldcreateapp.Fragment.FragmentWaitng;
import com.example.sqisoft.moldcreateapp.Manager.DataManager;
import com.example.sqisoft.moldcreateapp.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import static android.R.id.message;

public class MainActivity extends AppCompatActivity implements FragmentMain.OnFragmentInteractionListener,
        FragmentSelecting.OnFragmentInteractionListener, FragmentDrawing.OnFragmentInteractionListener,
        FragmentWaitng.OnFragmentInteractionListener, View.OnTouchListener, GestureDetector.OnGestureListener{

    public MainActivity my = MainActivity.this;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("192.168.2.200:6778");
            Log.i("Socket Success:"+ "Success","");
        } catch (URISyntaxException e) {
            Log.i("Socket Fail:"+ e.getMessage(),"");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataManager.getInstance().setActivity(this);



        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.replaced_layout, new FragmentMain()).commit();

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



    public void connectSocket(){
        // mSocket.on("message", onNewMessage);
        mSocket.connect();
        mSocket.on("event", onNewMessage);
    }


    public void attemptSend() {
        //   mInputMessageView = (EditText)findViewById(R.id.sending_message);
        // String message = mInputMessageView.getText().toString().trim();
        if (TextUtils.isEmpty("안녕 한수야ㅎㅎ")) {
            return;
        }
        String name = "Limky_Android";
        mSocket.emit("identify", name);
        mSocket.emit("message", message);
    }


    public Emitter.Listener onNewMessage = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            my.runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    try {
                        username = data.getString("name");
                        message = data.getString("text");

                        Log.i("name : ","message\n"+"text : "+message);

                        //TextView textView = (TextView) findViewById(R.id.incoming_message);

                        //  textView.setText("\n\n"+textView.getText()+"\n"+message);
                        //   String str = (String) textView.getText();

                        //    textView.setText(str.substring(2,str.length()));

                        //      Log.i("username:"+username+"\n","message:"+message+"\n");



                    } catch (JSONException e) {
                        return;
                    }


                    // add the message to view
                    //    addMessage(username, message);
                }
            });
        }
    };



}
