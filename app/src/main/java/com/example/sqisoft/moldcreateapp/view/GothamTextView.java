package com.example.sqisoft.moldcreateapp.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by SQISOFT on 2017-05-11.
 */

public class GothamTextView extends TextView {
    public GothamTextView(Context context) {
        super(context);
    }

    public GothamTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GothamTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GothamTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private void setType(Context context){

      //  this.setTypeface(Typeface.createFromAsset(context.getAssets(),"Gotham-Bold.otf"));
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Gotham-Medium.otf"));

    }

}
