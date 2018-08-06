package com.idrios.wordmash.events.engine;

import com.idrios.wordmash.events.AbstractEvent;
import com.idrios.wordmash.events.EventObserver;

/**
 * Created by idrios on 8/6/18.
 */

public class WordRootDiscoverEvent extends AbstractEvent{

    public static final String TYPE = WordRootDiscoverEvent.class.getName();

    public final String word;

    public WordRootDiscoverEvent(String word){
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
