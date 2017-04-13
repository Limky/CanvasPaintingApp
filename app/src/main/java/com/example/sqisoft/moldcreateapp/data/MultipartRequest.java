package com.example.sqisoft.moldcreateapp.data;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyLog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import khandroid.ext.apache.http.entity.mime.MultipartEntity;
import khandroid.ext.apache.http.entity.mime.content.ByteArrayBody;
import khandroid.ext.apache.http.entity.mime.content.StringBody;

public class MultipartRequest extends UTF8Request {

    private MultipartEntity entity = new MultipartEntity();

    private final String fieldName;
    private final String fileName;

    private final Bitmap bitmap;
    private final HashMap<String, String> params;

    public MultipartRequest(String url, HashMap<String, String> params, String fieldName, String fileName, Bitmap bitmap,
                            Response.Listener<String> listener, Response.ErrorListener errorListener,
                            Response.Listener<Integer> progLitener) {
        super(Method.POST, url, listener, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(10000, 0, 1f));
        this.bitmap = bitmap;
        this.params = params;
        this.fieldName = fieldName;
        this.fileName = fileName;
        buildMultipartEntity();
    }

    private void buildMultipartEntity() {
        entity.addPart(fieldName, new ByteArrayBody(bitmapToByteArray(bitmap), fileName));
        try {
            for (String key : params.keySet()) {
                entity.addPart(key, new StringBody(params.get(key)));
            }
        } catch (UnsupportedEncodingException e) {
            VolleyLog.e("UnsupportedEncodingException");
        }
    }

    public byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 80, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public String getBodyContentType() {
        return entity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }
}