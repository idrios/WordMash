package com.idrios.wordmash.events.engine;

import com.idrios.wordmash.events.AbstractEvent;
import com.idrios.wordmash.events.EventObserver;

/**
 * Created by idrios on 6/7/18.
 */

public class WordFoundEvent extends AbstractEvent{

    public static final String TYPE = EndGameEvent.class.getName();

    public final String word;

    public WordFoundEvent(String word){
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
