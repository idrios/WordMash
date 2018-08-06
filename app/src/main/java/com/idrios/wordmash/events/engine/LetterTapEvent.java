package com.idrios.wordmash.events.engine;

import com.idrios.wordmash.events.AbstractEvent;
import com.idrios.wordmash.events.EventObserver;

/**
 * Created by idrios on 2/16/18.
 */

public class LetterTapEvent extends AbstractEvent {

    public static final String TYPE = LetterTapEvent.class.getName();

    public final String word;

    public LetterTapEvent(String word){
        this.word = word;
    }

    @Override
    protected void fire(EventObserver eventObserver){
        eventObserver.onEvent(this);
    }

    @Override
    public String getType(){
        return TYPE;
    }
}
