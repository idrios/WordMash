package com.idrios.wordmash;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.engine.ScreenController;
import com.idrios.wordmash.engine.ScreenController.Screen;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Shared.context = getApplicationContext();
        Shared.activity = this;

        //Load directly to game
        ScreenController.getInstance().openScreen(Screen.GAME);

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
