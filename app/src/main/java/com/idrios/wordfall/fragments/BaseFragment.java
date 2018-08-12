package com.idrios.wordfall.fragments;

import android.support.v4.app.Fragment;

import com.idrios.wordfall.events.EventObserver;
import com.idrios.wordfall.events.engine.GameEndEvent;
import com.idrios.wordfall.events.engine.GameLoseEvent;
import com.idrios.wordfall.events.engine.GameWinEvent;
import com.idrios.wordfall.events.engine.RandomizeEvent;
import com.idrios.wordfall.events.engine.LettersResetEvent;
import com.idrios.wordfall.events.engine.GameStartEvent;
import com.idrios.wordfall.events.engine.LetterTapEvent;
import com.idrios.wordfall.events.engine.WordDiscoverEvent;
import com.idrios.wordfall.events.engine.WordRootDiscoverEvent;

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
