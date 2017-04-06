package com.example.sqisoft.moldcreateapp.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sqisoft.moldcreateapp.R;
import com.example.sqisoft.moldcreateapp.Util.FragmentUtil;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSelecting.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSelecting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSelecting extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View mFragmentSelectingView;
    private Button mMoldButton01,mMoldButton02,mMoldButton03,mMoldButton04,mDrawButton ;
    private ImageView mSelectedImageView;
    private TextView mSelectedMoldNameTextView, mSelectedMoldDescTextView;


    public FragmentSelecting() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSelecting newInstance(String param1, String param2) {
        FragmentSelecting fragment = new FragmentSelecting();
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
        mFragmentSelectingView = inflater.inflate(R.layout.fragment_selecting, container, false);

        attachViewAndListener();

        initDefault();

        FragmentUtil.trace();


        return mFragmentSelectingView;
    }

    private void attachViewAndListener(){
        mMoldButton01 = (Button) mFragmentSelectingView.findViewById(R.id.selected_mold_01_button);
        mMoldButton02 = (Button) mFragmentSelectingView.findViewById(R.id.selected_mold_02_button);
        mMoldButton03 = (Button) mFragmentSelectingView.findViewById(R.id.selected_mold_03_button);
        mMoldButton04 = (Button) mFragmentSelectingView.findViewById(R.id.selected_mold_04_button);

        mSelectedImageView = (ImageView) mFragmentSelectingView.findViewById(R.id.selected_mold_imageView);
        mSelectedMoldNameTextView = (TextView) mFragmentSelectingView.findViewById(R.id.selected_mold_name_textview);
        mSelectedMoldDescTextView = (TextView) mFragmentSelectingView.findViewById(R.id.selected_mold_desc_textview);

        mDrawButton = (Button) mFragmentSelectingView.findViewById(R.id.drawing_mold_button);

        mMoldButton01.setOnClickListener(moldButton01Listener);
        mMoldButton02.setOnClickListener(moldButton02Listener);
        mMoldButton03.setOnClickListener(moldButton03Listener);
        mMoldButton04.setOnClickListener(moldButton04Listener);

        mDrawButton.setOnClickListener(drawMoldListener);


    }

    private void initDefault(){
        mSelectedImageView.setImageResource(R.drawable.mold_01);
        mSelectedMoldNameTextView.setText(getResources().getString(R.string.selected_mold_01_title));
        mSelectedMoldDescTextView.setText(getResources().getString(R.string.selected_mold_01_desc));
    }

    private Button.OnClickListener moldButton01Listener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
           mSelectedImageView.setImageResource(R.drawable.mold_01);
           mSelectedMoldNameTextView.setText(getResources().getString(R.string.selected_mold_01_title));
           mSelectedMoldDescTextView.setText(getResources().getString(R.string.selected_mold_01_desc));
        }
    };
    private Button.OnClickListener moldButton02Listener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSelectedImageView.setImageResource(R.drawable.mold_02);
            mSelectedMoldNameTextView.setText(getResources().getString(R.string.selected_mold_02_title));
            mSelectedMoldDescTextView.setText(getResources().getString(R.string.selected_mold_02_desc));
        }
    };

    private Button.OnClickListener moldButton03Listener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSelectedImageView.setImageResource(R.drawable.mold_03);
            mSelectedMoldNameTextView.setText(getResources().getString(R.string.selected_mold_03_title));
            mSelectedMoldDescTextView.setText(getResources().getString(R.string.selected_mold_03_desc));

        }
    };

    private Button.OnClickListener moldButton04Listener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSelectedImageView.setImageResource(R.drawable.mold_04);
            mSelectedMoldNameTextView.setText(getResources().getString(R.string.selected_mold_04_title));
            mSelectedMoldDescTextView.setText(getResources().getString(R.string.selected_mold_04_desc));

        }
    };


    private Button.OnClickListener drawMoldListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
   /*         FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.addToBackStack("FragmentSelecting");
            transaction.replace(R.id.replaced_layout, new FragmentDrawing()).commit();*/
            FragmentUtil.addFragment(new FragmentDrawing());
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
