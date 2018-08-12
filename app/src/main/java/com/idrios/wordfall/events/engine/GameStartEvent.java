package com.idrios.wordfall.events.engine;

import com.idrios.wordfall.events.AbstractEvent;
import com.idrios.wordfall.events.EventObserver;

/**
 * Created by idrios on 3/1/18.
 */

public class GameStartEvent extends AbstractEvent {

    public static final String TYPE = GameStartEvent.class.getName();

    public GameStartEvent(){

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
