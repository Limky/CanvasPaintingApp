package com.example.sqisoft.moldcreateapp.data;

public interface ResponseListener<U> {
    void response(boolean success, U data);
}
