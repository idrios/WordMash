package com.idrios.wordmash.engine;

import android.widget.Toast;

import com.idrios.wordmash.assets.TileView;
import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.events.EventObserverAdapter;
import com.idrios.wordmash.events.engine.EndGameEvent;
import com.idrios.wordmash.events.engine.StartGameEvent;
import com.idrios.wordmash.events.ui.LetterTappedEvent;
import com.idrios.wordmash.model.GameConfiguration;
import com.idrios.wordmash.model.board.BoardArrangement;
import com.idrios.wordmash.model.Game;
import com.idrios.wordmash.engine.ScreenController.Screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        mPlayingGame = new Game(new GameConfiguration(3, 6));

        //Make WordMap


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

    @Override
    public void onEvent(LetterTappedEvent event){
        //TODO make an actual event
        Toast.makeText(Shared.context, "WOOOOOO", Toast.LENGTH_SHORT).show();
        int tileId = event.id;
        BoardArrangement boardArrangement = getActiveGame().boardArrangement;


    }

    public void arrangeBoard(){
        GameConfiguration gameConfiguration = mPlayingGame.gameConfiguration;
        //TODO initialize the hashmap better
        Map<Integer, String> arr = new HashMap<>();
        arr.put(0, "f");
        arr.put(1, "r");
        arr.put(2, "i");
        arr.put(3, "e");
        arr.put(4, "n");
        arr.put(5, "d");
        BoardArrangement boardArrangement = new BoardArrangement(arr);

        //each tile is labeled with its id 0-5
        List<Integer> ids = new ArrayList<>();
        for(int i = 0; i < gameConfiguration.maxWordSize; i++){
            ids.add(i);
        }

        //TODO Collections.shuffle(ids)

        boardArrangement.letterUrls = new HashMap<Integer, String>();
        int j = 0;
        for(int i = 0; i < arr.size(); i++){
            boardArrangement.letterUrls.put(ids.get(i), "letter_" + arr.get(i).toString().toLowerCase());
        }

        //TODO arrange the board here

        mPlayingGame.boardArrangement = boardArrangement;
        return;
    }

    public Game getActiveGame() {
        return mPlayingGame;
    }

}
