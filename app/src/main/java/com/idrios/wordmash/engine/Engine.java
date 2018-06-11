package com.idrios.wordmash.engine;

import android.widget.Toast;

import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.events.EventObserverAdapter;
import com.idrios.wordmash.events.engine.EndGameEvent;
import com.idrios.wordmash.events.engine.StartGameEvent;
import com.idrios.wordmash.events.engine.LetterTappedEvent;
import com.idrios.wordmash.events.engine.WordFoundEvent;
import com.idrios.wordmash.model.GameConfiguration;
import com.idrios.wordmash.model.Game;
import com.idrios.wordmash.engine.ScreenController.Screen;

import java.util.Map;

/**
 * Created by idrios on 2/16/18.
 */

public class Engine extends EventObserverAdapter {

    private static Engine mInstance = null;
    private ScreenController mScreenController;
    private Game mPlayingGame = null;

    private Engine(){
        mScreenController = ScreenController.getInstance();
    }

    public static Engine getInstance(){
        if(mInstance == null){
            mInstance = new Engine();
        }
        return mInstance;
    }

    public void start(){
        Shared.eventBus.listen(StartGameEvent.TYPE, this);
        Shared.eventBus.listen(LetterTappedEvent.TYPE, this);
        Shared.eventBus.listen(EndGameEvent.TYPE, this);
    }

    public void stop(){
        mPlayingGame = null;
        Shared.eventBus.unlisten(StartGameEvent.TYPE, this);
        Shared.eventBus.unlisten(LetterTappedEvent.TYPE, this);
        Shared.eventBus.unlisten(EndGameEvent.TYPE, this);
    }

    @Override
    public void onEvent(StartGameEvent event){

        //TODO set the wordmap in the game configuration
        //configure a game
        mPlayingGame = new Game(new GameConfiguration(3, 6));

        //TODO programmatically set the word
        //set up the game
        mPlayingGame.gameConfiguration.setWord("debugs");

        //Make WordMap
        mPlayingGame.boardArrangement.setWordList("debugs");

        //load the screen
        mScreenController.openScreen(Screen.GAME);
    }

    @Override
    public void onEvent(EndGameEvent event){
        //TODO is this correct?
        mScreenController.openScreen(Screen.MENU);
        mPlayingGame = null;
    }

    @Override
    public void onEvent(LetterTappedEvent event){
        Map WordList = mPlayingGame.boardArrangement.wordList;
        if(WordList.containsKey(event.word)) {
            Shared.eventBus.notify(new WordFoundEvent(event.word));
        }

    }

    @Override
    public void onEvent(WordFoundEvent event){

    }

    public Game getActiveGame() {
        return mPlayingGame;
    }

}
