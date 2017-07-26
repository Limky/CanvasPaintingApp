package com.example.sqisoft.moldcreateapp.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sqisoft.moldcreateapp.Activity.MainActivity;
import com.example.sqisoft.moldcreateapp.manager.DataManager;
import com.example.sqisoft.moldcreateapp.R;
import com.example.sqisoft.moldcreateapp.moldutil.FragmentUtil;
import com.example.sqisoft.moldcreateapp.view.ColorPickerGridViewAdapter;
import com.example.sqisoft.moldcreateapp.view.DrawingView;
import com.example.sqisoft.moldcreateapp.view.HeaderGridView;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentDrawing.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentDrawing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDrawing extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View mFragmentDrawingView;
    private Button mSendingButton;

    private ImageButton img;
    private Button mEraserButton,mBackButton;
    private boolean isSelected = false;

    private HeaderGridView gridView;
    private ColorPickerGridViewAdapter mColorPickerGridViewAdapter;

    private DrawingView mDrawingView;

    TextView touchPad, textX, textY;
   // private int mSeletedMold = 0;
    private ImageView mSeletedPaletteColorView, mMolde_image;
    private TextView mMoldNameTextView, mMoldDescTextView;

    public FragmentDrawing() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDrawing.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDrawing newInstance(String param1, String param2) {
        FragmentDrawing fragment = new FragmentDrawing();
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
       mFragmentDrawingView = inflater.inflate(R.layout.fragment_drawing, container, false);
        attachViewAndListener();
        // Inflate the layout for this fragment
        FragmentUtil.trace();

        DataManager.getInstance().setmSeletecPaletteColorView(mSeletedPaletteColorView);
        DataManager.getInstance().setEraser(mEraserButton);

        addBaseAdapter();

        DataManager.getInstance().setmDrawingView(mDrawingView);

       // gridView.setEnabled(false);


        mDrawingView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                   mColorPickerGridViewAdapter.unTouch(true);
                        break;

                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:
                    mColorPickerGridViewAdapter.unTouch(false);
                        break;

                    default:
                        return false;
                }
                return false;
            }
        });


        return mFragmentDrawingView;
    }




    private void addBaseAdapter(){
        gridView = (HeaderGridView) mFragmentDrawingView.findViewById(R.id.colorpicker_list);
        mColorPickerGridViewAdapter = new ColorPickerGridViewAdapter(this,getContext());
        gridView.setAdapter(mColorPickerGridViewAdapter);
    }


    private void attachViewAndListener(){
        mSendingButton = (Button) mFragmentDrawingView.findViewById(R.id.sending_mold_button);
        mSendingButton.setOnClickListener(mSendingButtonistener);
        mDrawingView = (DrawingView) mFragmentDrawingView.findViewById(R.id.drawing_view);
        mSeletedPaletteColorView = (ImageView) mFragmentDrawingView.findViewById(R.id.selected_palette_color);

        mMoldNameTextView = (TextView)  mFragmentDrawingView.findViewById(R.id.mold_name);
        mMoldDescTextView = (TextView)  mFragmentDrawingView.findViewById(R.id.mold_desc);

        mMolde_image = (ImageView) mFragmentDrawingView.findViewById(R.id.molde_image);

        mMoldNameTextView.setTypeface(DataManager.getInstance().getNotoOtf());
        mMoldDescTextView.setTypeface(DataManager.getInstance().getNotoOtf());

        mEraserButton = (Button) mFragmentDrawingView.findViewById(R.id.undo_button);
        mEraserButton.setOnClickListener(mUndoButtonListener);

        mBackButton = (Button) mFragmentDrawingView.findViewById(R.id.backButton);
        mBackButton.setOnClickListener(mBackButtonListener);

        switch (DataManager.getInstance().getSeletedMoldIndex()){

            case  1 :
             mDrawingView.setBackgroundResource(R.drawable.mold_01b);
                mMoldNameTextView.setText("좁쌀 곰팡이");
                mMoldDescTextView.setText(getResources().getString(R.string.selected_mold_01_desc));
                mMolde_image.setImageResource(R.drawable.mold_2100_img_01);

            break;

            case  2 :
             mDrawingView.setBackgroundResource(R.drawable.mold_02b);
                mMoldNameTextView.setText("누룩 곰팡이");
                mMoldDescTextView.setText(getResources().getString(R.string.selected_mold_02_desc));
                mMolde_image.setImageResource(R.drawable.mold_2100_img_02);
                break;

            case  3 :
              mDrawingView.setBackgroundResource(R.drawable.mold_03b);
                mMoldNameTextView.setText("솜사탕 곰팡이");
                mMoldDescTextView.setText(getResources().getString(R.string.selected_mold_03_desc));
                mMolde_image.setImageResource(R.drawable.mold_2100_img_03);
                break;

            case  4 :
              mDrawingView.setBackgroundResource(R.drawable.mold_04b);
                mMoldNameTextView.setText("털 곰팡이");
                mMoldDescTextView.setText(getResources().getString(R.string.selected_mold_04_desc));
                mMolde_image.setImageResource(R.drawable.mold_2100_img_04);
                break;


        }

    }
    private File filepath,externalFilePath;

    private Button.OnClickListener mSendingButtonistener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            mDrawingView.setDrawingCacheEnabled(true);
            Bitmap b = mDrawingView.getDrawingCache();
        //    DataManager.getInstance().setmCapturingBitmap(b.copy(b.getConfig(),true));
            FragmentUtil.addFragment(new FragmentSending(b.copy(b.getConfig(),true) ));
        }
    };
    private Boolean pressedFlag = false;
    private Button.OnClickListener mUndoButtonListener = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            mColorPickerGridViewAdapter.initImageButton();
            mEraserButton.setBackgroundResource(R.drawable.palette_20_p);
            mDrawingView.eraser();
            //mDrawingView.onClickUndo();

        }
    };


    private Button.OnClickListener mBackButtonListener = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {

            ((MainActivity)(DataManager.getInstance().getActivity())).backButtonFunction();

        }
    };



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
