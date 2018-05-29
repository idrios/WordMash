package com.idrios.wordmash.events.engine;

import com.idrios.wordmash.events.AbstractEvent;
import com.idrios.wordmash.events.EventObserver;
import com.idrios.wordmash.model.board.BoardArrangement;

/**
 * Created by idrios on 2/16/18.
 */

public class LetterTappedEvent extends AbstractEvent {

    public static final String TYPE = LetterTappedEvent.class.getName();

    public final String word;

    public LetterTappedEvent(String word){
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
