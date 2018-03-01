package com.idrios.wordmash.engine;

import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.events.EventObserverAdapter;
import com.idrios.wordmash.events.engine.GameStartedEvent;
import com.idrios.wordmash.events.ui.LetterTappedEvent;
import com.idrios.wordmash.model.BoardArrangement;
import com.idrios.wordmash.model.BoardConfiguration;
import com.idrios.wordmash.model.Game;
import com.idrios.wordmash.engine.ScreenController.Screen;

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
        Shared.eventBus.listen(LetterTappedEvent.TYPE, this);
    }

    public void stop(){
        mPlayingGame = null;
        Shared.eventBus.unlisten(LetterTappedEvent.TYPE, this);
    }

    @Override
    public void onEvent(GameStartedEvent event){
        mPlayingGame = new Game();
        mPlayingGame.boardConfiguration = new BoardConfiguration();

        //arrange the board
        arrangeBoard();

        //start the screen
        mScreenController.openScreen(Screen.GAME);

    }


    public void arrangeBoard(){
        BoardConfiguration boardConfiguration = mPlayingGame.boardConfiguration;
        BoardArrangement boardArrangement = new BoardArrangement();
        //TODO arrange the board here

        mPlayingGame.boardArrangement = boardArrangement;
        return;
    }

    public Game getActiveGame() {
        return mPlayingGame;
    }

}
