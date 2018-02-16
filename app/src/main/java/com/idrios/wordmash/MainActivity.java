package com.idrios.wordmash;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.idrios.wordmash.common.Shared;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Shared.context = getApplicationContext();

        setContentView(R.layout.activity_main);

        Shared.activity = this;
    }

    @Override
    protected void onDestroy(){
        //TODO stop the engine from running.

        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        //TODO onBackPressedEvent

    }

}
