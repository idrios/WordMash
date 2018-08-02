package com.idrios.wordmash.events.engine;

import com.idrios.wordmash.events.AbstractEvent;
import com.idrios.wordmash.events.EventObserver;

/**
 * Created by idrios on 6/27/18.
 */

public class ResetTilesEvent extends AbstractEvent{

    public static final String TYPE = ResetTilesEvent.class.getName();

    public ResetTilesEvent(){

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


