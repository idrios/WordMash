package com.idrios.wordmash.events;

import com.idrios.wordmash.events.engine.EndGameEvent;
import com.idrios.wordmash.events.engine.StartGameEvent;
import com.idrios.wordmash.events.engine.LetterTappedEvent;

/**
 * Created by idrios on 2/16/18.
 * Template for EventObservers in the app
 * Declares the different kinds of events that will be used
 */

public interface EventObserver {
    //TODO include other event types
    void onEvent(StartGameEvent event);

    void onEvent(LetterTappedEvent event);

    void onEvent(EndGameEvent event);

}
