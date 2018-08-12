package com.idrios.wordfall.events.engine;

import com.idrios.wordfall.events.AbstractEvent;
import com.idrios.wordfall.events.EventObserver;

/**
 * Created by idrios on 6/7/18.
 */

public class WordDiscoverEvent extends AbstractEvent{

    public static final String TYPE = WordDiscoverEvent.class.getName();

    public final String word;

    public WordDiscoverEvent(String word){
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
