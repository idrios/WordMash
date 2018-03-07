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

        // Get or create saved states
        Shared.context = getApplicationContext();
        Shared.engine = Engine.getInstance();
        Shared.eventBus = EventBus.getInstance();

        // Load main activity layout
        setContentView(R.layout.activity_main);

        // Start engine
        Shared.activity = this;
        Shared.engine.start();

        // Launch menu screen
        ScreenController.getInstance().openScreen(Screen.MENU);

    }

    @Override
    protected void onDestroy(){
        Shared.engine.stop();
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        // TODO set this to cycle through previous screens or do something else
        super.onBackPressed();
    }
}
