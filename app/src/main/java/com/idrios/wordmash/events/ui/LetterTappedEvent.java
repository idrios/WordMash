package com.idrios.wordmash.events.ui;

import com.idrios.wordmash.events.AbstractEvent;
import com.idrios.wordmash.events.EventObserver;

/**
 * Created by idrios on 2/16/18.
 */

public class LetterTappedEvent extends AbstractEvent {

    public static final String TYPE = LetterTappedEvent.class.getName();

    public final int id;

    public LetterTappedEvent(int id){
        this.id = id;
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
