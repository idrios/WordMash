package com.idrios.wordmash;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.engine.Engine;
import com.idrios.wordmash.engine.ScreenController;
import com.idrios.wordmash.engine.ScreenController.Screen;
import com.idrios.wordmash.events.EventBus;
import com.idrios.wordmash.events.engine.GameStartedEvent;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Shared.context = getApplicationContext();
        Shared.engine = Engine.getInstance();
        Shared.eventBus = EventBus.getInstance();

        Shared.activity = this;
        Shared.engine.start();

        Shared.eventBus.notify(new GameStartedEvent());

    }

    @Override
    protected void onDestroy(){
        //TODO stop the engine from running.
        Shared.engine.stop();
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        //TODO onBackPressedEvent

    }

}
