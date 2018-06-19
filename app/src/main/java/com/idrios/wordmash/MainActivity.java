package com.idrios.wordmash;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.engine.Engine;
import com.idrios.wordmash.engine.ScreenController;
import com.idrios.wordmash.engine.ScreenController.Screen;
import com.idrios.wordmash.events.EventBus;
import com.idrios.wordmash.model.wordlist.HashmapHelper;
import com.idrios.wordmash.utils.Utils;

public class MainActivity extends FragmentActivity {

    private ImageView mBackgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get or create saved states
        Shared.context = getApplicationContext();
        Shared.engine = Engine.getInstance();
        Shared.eventBus = EventBus.getInstance();
        HashmapHelper.init();

        // Load main activity layout
        setContentView(R.layout.activity_main);
        mBackgroundImage = (ImageView)findViewById(R.id.background_image);

        // Start engine
        Shared.activity = this;
        Shared.engine.start();

        // Set background
        setBackgroundImage();

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
        if(ScreenController.getInstance().onBack()) {
            super.onBackPressed();
        }
    }

    private void setBackgroundImage() {
        Bitmap bitmap = Utils.scaleDown(R.drawable.background, Utils.screenWidth(), Utils.screenHeight());
        bitmap = Utils.crop(bitmap, Utils.screenHeight(), Utils.screenWidth());
        bitmap = Utils.downscaleBitmap(bitmap, 2);
        mBackgroundImage.setImageBitmap(bitmap);
    }
}
