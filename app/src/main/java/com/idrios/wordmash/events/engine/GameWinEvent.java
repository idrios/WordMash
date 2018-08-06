package com.idrios.wordmash.events.engine;

import com.idrios.wordmash.events.AbstractEvent;
import com.idrios.wordmash.events.EventObserver;

/**
 * Created by idrios on 8/6/18.
 */

public class GameWinEvent extends AbstractEvent {

    public static final String TYPE = GameWinEvent.class.getName();

    public GameWinEvent(){

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
