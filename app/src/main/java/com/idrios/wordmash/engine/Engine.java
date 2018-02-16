package com.idrios.wordmash.engine;

import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.events.EventObserverAdapter;
import com.idrios.wordmash.events.ui.LetterTappedEvent;

/**
 * Created by idrios on 2/16/18.
 */

public class Engine extends EventObserverAdapter {

    private static Engine mInstance = null;

    private Engine(){

    }

    public void start(){
        Shared.eventBus.listen(LetterTappedEvent.TYPE, this);
    }

}
