package com.example.sqisoft.moldcreateapp.domain;

import com.google.gson.Gson;

/**
 * Created by SQISOFT on 2017-04-17.
 */

public class SendData {
    private int nettype;
    private int sporetype;
    private String url;

    public int getNettype() {
        return nettype;
    }

    public void setNettype(int nettype) {
        this.nettype = nettype;
    }

    public int getSporetype() {
        return sporetype;
    }

    public void setSporetype(int sporetype) {
        this.sporetype = sporetype;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
