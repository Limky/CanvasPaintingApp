package com.example.sqisoft.moldcreateapp.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqisoft.moldcreateapp.Activity.MainActivity;
import com.example.sqisoft.moldcreateapp.Manager.DataManager;
import com.example.sqisoft.moldcreateapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentWaitng.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentWaitng#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWaitng extends Fragment implements View.OnTouchListener, GestureDetector.OnGestureListener{
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
    private Handler mHandler;

    private boolean sendingCount = true;

    public FragmentWaitng() {
        // Required empty public constructor
    }

    public FragmentWaitng(Bitmap caturingBitmap) {
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
    public static FragmentWaitng newInstance(String param1, String param2) {
        FragmentWaitng fragment = new FragmentWaitng();
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
        mFragmentWaitngView = inflater.inflate(R.layout.fragment_waitng, container, false);


        attachViewAndListener();

     //   ((MainActivity)DataManager.getInstance().getActivity()).connectSocket();

        imageView =  (ImageView) mFragmentWaitngView.findViewById(R.id.sending_imageView);
       imageView.setImageBitmap(mCapturingBitmap);


       ((MainActivity) DataManager.getInstance().getActivity()).sendToUnity(imageView);

        return mFragmentWaitngView;


    }




    private void attachViewAndListener(){
        textX = (TextView) mFragmentWaitngView.findViewById(R.id.text_X);
        textY = (TextView) mFragmentWaitngView.findViewById(R.id.text_Y);
        touchPad = (TextView) mFragmentWaitngView.findViewById(R.id.touch_pad);

        touchPad.setOnTouchListener(this);
    }
    float dX, dY =0;

    @Override
    public boolean onTouch(View view, MotionEvent event) {


        if(view.getId() == R.id.touch_pad)
        {
            mTouchFlag = true;
            textX.setText("X : "+Float.toString(event.getX()));
            textY.setText("Y : "+Float.toString(event.getY()));

            Log.d("onTouch mTouchFlag = ",""+mTouchFlag);

            imageView.setX(event.getX()-(imageView.getMeasuredWidth()/2));
            imageView.setY(event.getY()-(imageView.getMeasuredHeight()/2));
            imageView.setVisibility(View.VISIBLE);


            if(event.getY() < 0 || event.getY() <340 ){

                imageView.setVisibility(View.INVISIBLE);

            }

            return true;
        }

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                Toast.makeText(getContext(), " 모션 ACTION_UP", Toast.LENGTH_SHORT).show();
                mTouchFlag = false;
                Log.d("unTouch mTouchFlag = ",""+mTouchFlag);
                break;

            default:
                return false;
        }
        return true;
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
