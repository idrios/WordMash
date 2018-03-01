package com.idrios.wordmash.events.engine;

import com.idrios.wordmash.events.AbstractEvent;
import com.idrios.wordmash.events.EventObserver;

/**
 * Created by idrios on 3/1/18.
 */

public class GameStartedEvent extends AbstractEvent {

    public static final String TYPE = GameStartedEvent.class.getName();

    public GameStartedEvent(){

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
