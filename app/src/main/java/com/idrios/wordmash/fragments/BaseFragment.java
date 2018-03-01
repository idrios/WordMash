package com.idrios.wordmash.fragments;

import android.support.v4.app.Fragment;

import com.idrios.wordmash.events.EventObserver;
import com.idrios.wordmash.events.engine.GameStartedEvent;
import com.idrios.wordmash.events.ui.LetterTappedEvent;

/**
 * Created by idrios on 2/16/18.
 */

public class BaseFragment extends Fragment implements EventObserver {

    @Override
    public void onEvent(LetterTappedEvent event){throw new UnsupportedOperationException();}

    @Override
    public void onEvent(GameStartedEvent event){throw new UnsupportedOperationException();}

}
