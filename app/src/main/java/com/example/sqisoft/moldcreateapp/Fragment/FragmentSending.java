package com.example.sqisoft.moldcreateapp.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.format.Formatter;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqisoft.moldcreateapp.R;
import com.example.sqisoft.moldcreateapp.data.ResponseListener;
import com.example.sqisoft.moldcreateapp.domain.DeviceInformation;
import com.example.sqisoft.moldcreateapp.domain.ResultCustom;
import com.example.sqisoft.moldcreateapp.domain.SendData;
import com.example.sqisoft.moldcreateapp.manager.DataManager;
import com.example.sqisoft.moldcreateapp.moldutil.CommandUtil;
import com.example.sqisoft.moldcreateapp.moldutil.FragmentUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSending.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSending#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSending extends Fragment implements View.OnTouchListener, GestureDetector.OnGestureListener, Animation.AnimationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View mFragmentWaitngView;

    TextView touchPad, textX, textY;
    private GestureDetector gd;

    private Boolean mTouchFlag = false;
    private ImageView imageView;

    private Bitmap mCapturingBitmap;

    private boolean sendingFlag = true;
    // Animation
    private Animation animFadein, arrowBlink, moldBlink, fadeOut, upSliding;
    private ImageView arrow_imageview;
    private TextView mSendingTextView, mSendingTextView2;
    private Handler mHandler = new Handler();


    public FragmentSending() {
        // Required empty public constructor
    }

    public FragmentSending(Bitmap caturingBitmap) {
        // Required empty public constructor
       mCapturingBitmap = caturingBitmap;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentWaitng.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSending newInstance(String param1, String param2) {
        FragmentSending fragment = new FragmentSending();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentWaitngView = inflater.inflate(R.layout.fragment_sending, container, false);


        attachViewAndListener();

        attachAnimation();

        imageView =  (ImageView) mFragmentWaitngView.findViewById(R.id.sending_imageView);
        imageView.setImageBitmap(mCapturingBitmap);



        arrow_imageview.startAnimation(arrowBlink);
        imageView.startAnimation(moldBlink);

        imageView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1)
            {
               imageTouchFlag = true;
                return false;
            }
        });

        return mFragmentWaitngView;

    }

    private void attachAnimation(){
        // load the animation
        animFadein = AnimationUtils.loadAnimation(getActivity(),R.anim.fadein);
        animFadein.setAnimationListener(this);

        arrowBlink = AnimationUtils.loadAnimation(getActivity(),R.anim.arrow_blink);
        arrowBlink.setAnimationListener(this);

        upSliding = AnimationUtils.loadAnimation(getActivity(),R.anim.up_sliding);
        upSliding.setAnimationListener(this);

        moldBlink = AnimationUtils.loadAnimation(getActivity(),R.anim.mold_blink);
        moldBlink.setAnimationListener(this);

        fadeOut = AnimationUtils.loadAnimation(getActivity(),R.anim.fadeout);
        fadeOut.setAnimationListener(this);
    }


    private void attachViewAndListener(){
        textX = (TextView) mFragmentWaitngView.findViewById(R.id.text_X);
        textY = (TextView) mFragmentWaitngView.findViewById(R.id.text_Y);
        touchPad = (TextView) mFragmentWaitngView.findViewById(R.id.touch_pad);
        arrow_imageview = (ImageView) mFragmentWaitngView.findViewById(R.id.arrow_imageview);
        mSendingTextView = (TextView) mFragmentWaitngView.findViewById(R.id.send_information_textview);
        mSendingTextView2 = (TextView) mFragmentWaitngView.findViewById(R.id.send_information_textview2);

        ((TextView) mFragmentWaitngView.findViewById(R.id.send_information_textview)).setTypeface(Typeface.createFromAsset(DataManager.getInstance().getActivity().getAssets(), "fonts/NotoSansCJKkr-Medium.otf"));
        ((TextView) mFragmentWaitngView.findViewById(R.id.send_information_textview2)).setTypeface(Typeface.createFromAsset(DataManager.getInstance().getActivity().getAssets(), "fonts/NotoSansCJKkr-Medium.otf"));


        touchPad.setOnTouchListener(this);
    }

    double touchDownY = 0,touchUpY = 0;
    boolean imageTouchFlag = false;
    @Override
    public boolean onTouch(View view, MotionEvent event) {


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                //final double tempTouchY = event.getY();
                if(imageTouchFlag)touchDownY = event.getY();


                break;

            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                touchUpY =  event.getY();

                Log.d("unTouch touchDownY = ", "" + touchDownY);
                Log.d("unTouch touchUpY   = ", "" + touchUpY);

                            if(touchDownY > touchUpY) {
                                imageView.animate().translationY(-200).withLayer();
                                imageView.animate().alpha(0);

                                sendImageToServer();
                                mTouchFlag = false;

                                Log.d("unTouch mTouchFlag = ", "" + mTouchFlag);

                            }

                imageTouchFlag = false;

                         //     Toast.makeText(getContext(), " 모션 ACTION_UP", Toast.LENGTH_SHORT).show();


                break;

            default:
                return false;
        }

        return true;

    }




    public void sendImageToServer() {


        if(sendingFlag) {
            Log.d("onTouch mTouchFlag ========== ", "" + mTouchFlag);


            imageView.setVisibility(View.INVISIBLE);
            sendingFlag = false;
            imageView.startAnimation(fadeOut);

            if(isOnline()){


            //UBC로 부터 받아와야하는 DeviceCode.
            CommandUtil.customAPI(mCapturingBitmap, null, new ResponseListener<ResultCustom>() {
                @Override
                public void response(boolean success, ResultCustom data) {

                    if (success && data != null) {
                        Log.w("data customName : ", "" + data.getCustomFileName() + "\ncustomFileName : " + data.getCustomFileName() + "\ncustomFile : " + data.getCustomFile());

                        //sendData {"nettype":17, "sporetype":18, "url":http://192/data/xxx.png"}

                        SendData sendData = new SendData();
                        int seletedMold = DataManager.getInstance().getSeletedMoldIndex();
                        sendData.setNettype(DataManager.getInstance().nettype);
                        sendData.setSporetype(seletedMold);
                        sendData.setUrl(DataManager.getInstance().API_SERVER_URL + "/data" + data.getCustomFile());


                        CommandUtil.sendAPI(sendData, new ResponseListener<DeviceInformation[]>() {
                            @Override
                            public void response(boolean success, DeviceInformation[] data) {

                                if (success && data != null) {

                                    Log.w("dataa: " + data.toString(), "");
                                    pageRedirectAndUserTextShow();
                                   // Toast.makeText(getActivity(), "유니티 성공", Toast.LENGTH_SHORT).show();

                                } else {
                                    pageRedirectAndUserTextShow();
                                    //네트워크 연결, 서버와의 통신은 됬는데 유니티가 문제일때

                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            pageRedirectAndUserTextShow();
                                            dialog.dismiss();     //닫기
                                        }
                                    });
                                    alert.setMessage("서버와 유니티 통신을 확인해주세요.\n잠시 후 다시 시도해 주세요.");
                                    alert.show().getWindow().setLayout(500,160);

                                    Toast.makeText(getActivity(), "유니티 전송 실패", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


                    } else {
                        //네트워크 연결은 됬으나, 서버와의 통신이 안될 때.

                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                pageRedirectAndUserTextShow();
                                dialog.dismiss();     //닫기
                            }
                        });
                        alert.setMessage("서버와 통신이 원활하지 않습니다.\n잠시 후 다시 시도해 주세요.");
                        alert.show().getWindow().setLayout(500,160);


                    }
                }
            });

            }else{
                //네트워크 연결 실패.
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pageRedirectAndUserTextShow();
                        dialog.dismiss();     //닫기
                    }
                });
                alert.setMessage("네트워크 연결이 원활하지 않습니다.\nWIFI 연결이 되었는지 확인해 주세요.");
                alert.show().getWindow().setLayout(500,160);


            }



        }

    }



    private void pageRedirectAndUserTextShow(){
        mHandler.postDelayed(mMyTask, 3500);


        mSendingTextView.setVisibility(View.INVISIBLE);
        mSendingTextView2.setVisibility(View.VISIBLE);

    }



    private Runnable mMyTask = new Runnable() {
        @Override public void run() {
            //비지니스 로직
            FragmentUtil.clear();
            FragmentUtil.addFragment(new FragmentMain());

            } };



    public boolean isOnline()
    {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeMessages(0);

    }

    @Override
    public String toString() {
        return super.toString();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }





}
