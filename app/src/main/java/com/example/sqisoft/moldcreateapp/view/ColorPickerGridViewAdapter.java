package com.example.sqisoft.moldcreateapp.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.example.sqisoft.moldcreateapp.R;
import com.example.sqisoft.moldcreateapp.manager.ColorManager;
import com.example.sqisoft.moldcreateapp.manager.DataManager;

/**
 * Created by SQISOFT on 2017-04-06.
 */

public class ColorPickerGridViewAdapter  extends BaseAdapter {
    private Fragment mFragment;
    private Context mContext;
    LayoutInflater mInflater;
    private ColorPickerHolder mViewHolder;
  //  private boolean isSelected = false;
    private static int COLORPICKER_NUMBER = 20;
    private boolean[] isSelected = new boolean[COLORPICKER_NUMBER];
    private ImageButton[] mImageButtonList = new ImageButton[COLORPICKER_NUMBER];
    private View mView;

    public ColorPickerGridViewAdapter(Fragment fragment , Context context){
        for(int i = 0 ; i < isSelected.length ; i++)
            isSelected[i] = false;

        this.mFragment = fragment;
        this.mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return COLORPICKER_NUMBER;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public boolean test = true;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        mViewHolder = new ColorPickerHolder();


        if(convertView == null){

            convertView = mInflater.inflate(R.layout.color_radio_buttons,parent,false);

        //    Log.d("ddd","111");

            mViewHolder.mImageButton = (ImageButton) convertView.findViewById(R.id.color_radio_button);

             Log.w("mImageButtonList ================= ",""+pos);

                mViewHolder.mImageButton.setBackground(ColorManager.getInstance().getUnselectedColor(pos));
                //mViewHolder.mImageButton.setClickable(true);
            if(!bugFlag)
                mImageButtonList[pos] = mViewHolder.mImageButton;


            convertView.setTag(mViewHolder);

        }else{
            mViewHolder = (ColorPickerHolder) convertView.getTag();

        }



        mViewHolder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(DataManager.getInstance().getActivity().getBaseContext(), android.R.anim.fade_in));

                if (isSelected[pos]) {
                //선택안함

                    isSelected[pos] = false;
                    v.setBackground(ColorManager.getInstance().getUnselectedColor(pos));

                } else {
                //선택함
                //선택당한 순간 자기 이외의 놈들은 선택안하게..
                    initImageButton();
                    System.out.println("선택됬다. pos = "+pos);
                    isSelected[pos] = false;
                    v.setBackground(ColorManager.getInstance().getSelectedColor(pos));
                }
            }

        });


        return convertView;
    }
boolean bugFlag = false;
    private void initImageButton(){
        bugFlag = true;
        for(int i = 0; i < mImageButtonList.length ; i++){
            isSelected[i] = false;
            mImageButtonList[i].setBackground(ColorManager.getInstance().getUnselectedColor(i));

        }



    }


    public class ColorPickerHolder
    {
        public ImageButton mImageButton;

    }
}
