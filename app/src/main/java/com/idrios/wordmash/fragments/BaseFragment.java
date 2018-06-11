package com.idrios.wordmash.fragments;

import android.support.v4.app.Fragment;

import com.idrios.wordmash.events.EventObserver;
import com.idrios.wordmash.events.engine.EndGameEvent;
import com.idrios.wordmash.events.engine.StartGameEvent;
import com.idrios.wordmash.events.engine.LetterTappedEvent;
import com.idrios.wordmash.events.engine.WordFoundEvent;

/**
 * Created by idrios on 2/16/18.
 */

public class BaseFragment extends Fragment implements EventObserver {

    @Override
    public void onEvent(LetterTappedEvent event){throw new UnsupportedOperationException();}

    @Override
    public void onEvent(StartGameEvent event){throw new UnsupportedOperationException();}

    @Override
    public void onEvent(EndGameEvent event){throw new UnsupportedOperationException();}

    @Override
    public void onEvent(WordFoundEvent event){throw new UnsupportedOperationException();}

}
