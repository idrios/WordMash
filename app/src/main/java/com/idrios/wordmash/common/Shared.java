package com.idrios.wordmash.common;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.idrios.wordmash.engine.Engine;
import com.idrios.wordmash.events.EventBus;
import com.idrios.wordmash.model.Game;
import com.idrios.wordmash.model.wordlist.HashmapHelper;

/**
 * Created by idrios on 2/16/18.
 */

public class Shared {

    //Game
    public static Context context;
    public static FragmentActivity activity;
    public static Engine engine;
    public static EventBus eventBus;

    // Word info
    public static HashmapHelper hashmapHelper;

}
