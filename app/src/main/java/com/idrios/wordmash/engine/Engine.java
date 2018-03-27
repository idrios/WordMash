package com.idrios.wordmash.engine;

import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.events.EventObserverAdapter;
import com.idrios.wordmash.events.engine.EndGameEvent;
import com.idrios.wordmash.events.engine.StartGameEvent;
import com.idrios.wordmash.events.ui.LetterTappedEvent;
import com.idrios.wordmash.model.board.BoardArrangement;
import com.idrios.wordmash.model.BoardConfiguration;
import com.idrios.wordmash.model.Game;
import com.idrios.wordmash.engine.ScreenController.Screen;

import java.util.HashMap;
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
        //configure a game
        mPlayingGame = new Game(new BoardConfiguration(3, 6));

        //arrange the board
        arrangeBoard();

        //load the screen
        mScreenController.openScreen(Screen.GAME);
    }

    @Override
    public void onEvent(EndGameEvent event){
        //TODO is this correct?
        mScreenController.openScreen(Screen.MENU);
        mPlayingGame = null;
    }


    public void arrangeBoard(){
        BoardConfiguration boardConfiguration = mPlayingGame.boardConfiguration;

        //TODO initialize the hashmap better
        #2 CHANGE THIS SO THE ARRAY IS FILLED WITH CHARACTERS BASED ON THE WORD IN CONFIG FILE
        Map<Integer, String> arr = new HashMap<>();
        arr.put(0, "m");
        arr.put(1, "y");
        arr.put(2, "w");
        arr.put(3, "o");
        arr.put(4, "r");
        arr.put(5, "d");

        BoardArrangement boardArrangement = new BoardArrangement(arr);
        //TODO arrange the board here

        mPlayingGame.boardArrangement = boardArrangement;
        return;
    }

    public Game getActiveGame() {
        return mPlayingGame;
    }

}
