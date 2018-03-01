package com.idrios.wordmash.events;

import com.idrios.wordmash.events.engine.GameStartedEvent;
import com.idrios.wordmash.events.ui.LetterTappedEvent;

/**
 * Created by idrios on 2/16/18.
 */

public class EventObserverAdapter implements EventObserver {
    @Override
    public void onEvent(LetterTappedEvent event){throw new UnsupportedOperationException();}

    public void onEvent(GameStartedEvent event){throw new UnsupportedOperationException();}

}
