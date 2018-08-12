package com.idrios.wordfall.events;

/**
 * Created by idrios on 2/16/18.
 */

public abstract class AbstractEvent implements Event{

    protected abstract void fire(EventObserver eventObserver);

}
