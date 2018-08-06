package com.idrios.wordmash.fragments;

import android.support.v4.app.Fragment;

import com.idrios.wordmash.events.EventObserver;
import com.idrios.wordmash.events.engine.GameEndEvent;
import com.idrios.wordmash.events.engine.GameLoseEvent;
import com.idrios.wordmash.events.engine.GameWinEvent;
import com.idrios.wordmash.events.engine.RandomizeEvent;
import com.idrios.wordmash.events.engine.LettersResetEvent;
import com.idrios.wordmash.events.engine.GameStartEvent;
import com.idrios.wordmash.events.engine.LetterTapEvent;
import com.idrios.wordmash.events.engine.WordDiscoverEvent;
import com.idrios.wordmash.events.engine.WordRootDiscoverEvent;

/**
 * Created by idrios on 2/16/18.
 */

public class BaseFragment extends Fragment implements EventObserver {

    @Override
    public void onEvent(GameStartEvent event){throw new UnsupportedOperationException();}

    @Override
    public void onEvent(GameEndEvent event){throw new UnsupportedOperationException();}

    @Override
    public void onEvent(GameWinEvent event){throw new UnsupportedOperationException();}

    @Override
    public void onEvent(GameLoseEvent event){throw new UnsupportedOperationException();}

    @Override
    public void onEvent(WordDiscoverEvent event){throw new UnsupportedOperationException();}

    @Override
    public void onEvent(WordRootDiscoverEvent event){throw new UnsupportedOperationException();}

    @Override
    public void onEvent(LetterTapEvent event){throw new UnsupportedOperationException();}

    @Override
    public void onEvent(LettersResetEvent event){throw new UnsupportedOperationException();}

    @Override
    public void onEvent(RandomizeEvent event){throw new UnsupportedOperationException();}


}
