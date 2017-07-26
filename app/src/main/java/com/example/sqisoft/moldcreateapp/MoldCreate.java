package com.example.sqisoft.moldcreateapp;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 * Created by SQISOFT on 2017-05-12.
 */
public class MoldCreate extends Application {

    private static Context mAppContext;

    @Override
    public void onCreate()
    {
        super.onCreate();
        setAppContext(getApplicationContext());


        setDefaultFont(this, "SERIF", "fonts/NotoSansCJKkr-Medium.otf");
       // setDefaultFont(this, "MONOSPACE", "fonts/Gotham-Medium.otf");

      //  setDefaultFont(this, "SANS_SERIF", "Gotham-Medium.otf");
     //   setDefaultFont(this, "DEFAULT", "fonts/NotoSansCJKkr-Medium.otf");
    }


    protected static void setDefaultFont(Context ctx, String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(ctx.getAssets(), fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    /**
     * 지정한 타입의 폰트를 변경한다.
     * @param staticTypefaceFieldName
     * @param newTypeface
     */
    private static void replaceFont(String staticTypefaceFieldName, final Typeface newTypeface) {
        try {
            final Field StaticField = Typeface.class.getDeclaredField(staticTypefaceFieldName);
            StaticField.setAccessible(true);
            StaticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public static void setAppContext(Context appContext) {
        mAppContext = appContext;
    }
}
