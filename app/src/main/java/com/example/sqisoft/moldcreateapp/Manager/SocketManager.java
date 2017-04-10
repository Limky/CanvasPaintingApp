package com.example.sqisoft.moldcreateapp.Manager;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by SQISOFT on 2017-04-07.
 */

public class SocketManager {


    private static SocketManager instance = null;
    private static String html = "";
    private static Socket socket;

    private static BufferedReader networkReader;
    private static BufferedWriter networkWriter;

    private static String ip = "192.168.2.171"; // IP
    private static int port = 6778; // PORT번호

    private static ImageView mImageView;

    private SocketManager(){

    }


    public static SocketManager getInstance(){
        if(instance == null){
            instance = new SocketManager();
        }
        return instance;
    }

    private void disconnectSocket(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void connectSocket(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    setSocket();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }

    public void setSocket() throws IOException {

        try {
            socket = new Socket(ip, port);
            networkWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            networkReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Log.w("************************************* Socket connection is success ", "*******************************************");


        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }


    public void sendToUnity(ImageView imageView){

        mImageView = imageView;
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Log.w("******************************************* Sending Image", "*******************111************************");
                    Bitmap bmp = ((BitmapDrawable) mImageView.getDrawable()).getBitmap(); //String str = et.getText().toString();

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                    byte[] array = bos.toByteArray();
                    OutputStream out = socket.getOutputStream();

                    DataOutputStream dos = new DataOutputStream(out);
                    dos.writeInt(array.length);
                    dos.write(array, 0, array.length);
                    dos.flush();
                    dos.close();


                    Log.w("******************************************* Sending Image", "**********************222*********************");
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }


    public boolean checkSocketConnection() {

        if (socket != null) {
            Log.w("************************************* 소켓이 연결 되어 있습니다. ( O )", "*******************************************");
            return true;
        } else {
            Log.w("************************************* 소켓이 연결이 되지 않았습니다. ( X )", "*******************************************");
            return false;
        }
    }



}
