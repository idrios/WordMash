package com.idrios.wordmash.events;

import com.idrios.wordmash.events.engine.GameEndEvent;
import com.idrios.wordmash.events.engine.GameLoseEvent;
import com.idrios.wordmash.events.engine.GameWinEvent;
import com.idrios.wordmash.events.engine.RandomizeEvent;
import com.idrios.wordmash.events.engine.LettersResetEvent;
import com.idrios.wordmash.events.engine.GameStartEvent;
import com.idrios.wordmash.events.engine.LetterTapEvent;
import com.idrios.wordmash.events.engine.WordDiscoverEvent;
import com.idrios.wordmash.events.engine.WordRootDiscoverEvent;

/**
 * Created by idrios on 2/16/18.
 * Template for EventObservers in the app
 * Declares the different kinds of events that will be used
 */

public interface EventObserver {
    //TODO include other event types
    void onEvent(GameStartEvent event);

    void onEvent(GameEndEvent event);

    void onEvent(GameWinEvent event);

    void onEvent(GameLoseEvent event);

    void onEvent(WordDiscoverEvent event);

    void onEvent(WordRootDiscoverEvent event);

    void onEvent(LetterTapEvent event);

    void onEvent(LettersResetEvent event);

    void onEvent(RandomizeEvent event);




}
