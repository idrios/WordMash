package com.idrios.wordfall.events.engine;

import com.idrios.wordfall.events.AbstractEvent;
import com.idrios.wordfall.events.EventObserver;

/**
 * Created by idrios on 3/16/18.
 */

public class GameEndEvent extends AbstractEvent{

    public static final String TYPE = GameEndEvent.class.getName();

    public GameEndEvent(){

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
