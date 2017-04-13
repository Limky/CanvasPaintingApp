package com.example.sqisoft.moldcreateapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.sqisoft.moldcreateapp.data.MultipartRequest;
import com.example.sqisoft.moldcreateapp.data.UTF8Request;
import com.example.sqisoft.moldcreateapp.manager.DataManager;

import java.util.HashMap;
import java.util.Map;

public class VolleyUtil {
    private RequestQueue mQueue = null;
    public static final boolean USE_SESSION = true;
    
    /*https 참고url : http://ogrelab.ikratko.com/using-android-volley-with-self-signed-certificate/ */
    public static final boolean USE_HTTPS = false;
    public static final String KEYSTORE_PASSWORD = "test111";
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    public static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "JSESSIONID";
    private static String sessionId = null;
    private ImageLoader mImageLoader = null;

    private static VolleyUtil mInstance = null;

    public static VolleyUtil getInstance() {
        if(mInstance == null) {
            Log.d("test","VolleyUtil getInstance (4)");
            mInstance = new VolleyUtil();
        }
        return mInstance;
    }

    private Context getContext() {
        return DataManager.getInstance().getmContext();
    }

    private VolleyUtil() {
        Log.d("test","VolleyUtil VolleyUtil 생성자 (5)");
        mQueue = Volley.newRequestQueue(getContext());
        mImageLoader = new ImageLoader(mQueue, new LruBitmapCache());
//        mImageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
//            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(15);
//            public void putBitmap(String url, Bitmap bitmap) {
//                mCache.put(url, bitmap);
//            }
//            public Bitmap getBitmap(String url) {
//                return mCache.get(url);
//            }
//        });
        Log.d("test","VolleyUtil newRequestQueue 생성 (6)");
    }

    public void get(String url, Listener<String> successListener, Response.ErrorListener errorListener) {
        if(url.startsWith("file:///android_asset/")) {
            Log.d("test","json파일을 읽어서 스트링으로 변환한다. (7)");
            String ret = AssetUtil.readAssets(getContext(), url.substring("file:///android_asset/".length()));
            successListener.onResponse(ret);
            return;
        }
        if(mQueue == null) {
            mQueue = Volley.newRequestQueue(getContext());
        }
        mQueue.add(new UTF8Request(url, successListener, errorListener));
    }

    public void post(final String url, final HashMap<String, String> params, Listener<String> successListener,
                     Response.ErrorListener errorListener) {
        if(url.startsWith("file:///android_asset/")) {
            String ret = AssetUtil.readAssets(getContext(), url.substring("file:///android_asset/".length()));
            successListener.onResponse(ret);
            return;
        }
        if(mQueue == null) {
            mQueue = Volley.newRequestQueue(getContext());
        }
        mQueue.add(new UTF8Request(Request.Method.POST, url, successListener, errorListener) {
        	
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        });
    }

    public void multipartBitmap(final String url, final HashMap<String, String> params, final String bitmapFieldName, final String fileName, final Bitmap bitmap, final Listener<Integer> progressListener, Response.Listener<String> successListener,
                                Response.ErrorListener errorListener) {
        if(url.startsWith("file:///android_asset/")) {
            String ret = AssetUtil.readAssets(getContext(), url.substring("file:///android_asset/".length()));
            successListener.onResponse(ret);
            return;
        }
        if(mQueue == null) {
            mQueue = Volley.newRequestQueue(getContext());
        }
        mQueue.add(new MultipartRequest(url, params, bitmapFieldName, fileName, bitmap, successListener, errorListener, progressListener));
    }

    public static void checkSessionCookie(Map<String, String> headers) {
        if(headers.containsKey(SET_COOKIE_KEY) && headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
            String cookie = headers.get(SET_COOKIE_KEY);
            if(cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                sessionId = cookie;
            }
        }
    }

    public ImageLoader getImageLoader(){
        return mImageLoader;
    }
}
