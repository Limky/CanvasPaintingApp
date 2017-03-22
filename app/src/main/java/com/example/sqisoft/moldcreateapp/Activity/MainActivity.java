package com.example.sqisoft.moldcreateapp.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.sqisoft.moldcreateapp.Fragment.FragmentDrawing;
import com.example.sqisoft.moldcreateapp.Fragment.FragmentMain;
import com.example.sqisoft.moldcreateapp.Fragment.FragmentSelecting;
import com.example.sqisoft.moldcreateapp.R;

public class MainActivity extends AppCompatActivity implements FragmentMain.OnFragmentInteractionListener, FragmentSelecting.OnFragmentInteractionListener, FragmentDrawing.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.replaced_layout, new FragmentMain()).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
