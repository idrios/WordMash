package com.idrios.wordfall.engine;

import com.idrios.wordfall.common.Music;
import com.idrios.wordfall.common.Settings;
import com.idrios.wordfall.common.Shared;
import com.idrios.wordfall.events.EventObserverAdapter;
import com.idrios.wordfall.events.engine.GameEndEvent;
import com.idrios.wordfall.events.engine.GameLoseEvent;
import com.idrios.wordfall.events.engine.GameStartEvent;
import com.idrios.wordfall.events.engine.GameWinEvent;
import com.idrios.wordfall.events.engine.LetterTapEvent;
import com.idrios.wordfall.events.engine.RandomizeEvent;
import com.idrios.wordfall.events.engine.LettersResetEvent;
import com.idrios.wordfall.events.engine.WordDiscoverEvent;
import com.idrios.wordfall.events.engine.WordRootDiscoverEvent;
import com.idrios.wordfall.model.GameConfiguration;
import com.idrios.wordfall.model.Game;
import com.idrios.wordfall.engine.ScreenController.Screen;

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
        Shared.eventBus.listen(GameStartEvent.TYPE, this);
        Shared.eventBus.listen(GameEndEvent.TYPE, this);
        Shared.eventBus.listen(GameWinEvent.TYPE, this);
        Shared.eventBus.listen(GameLoseEvent.TYPE, this);
        Shared.eventBus.listen(WordDiscoverEvent.TYPE, this);
        Shared.eventBus.listen(WordRootDiscoverEvent.TYPE, this);
        Shared.eventBus.listen(LetterTapEvent.TYPE, this);
        Shared.eventBus.listen(RandomizeEvent.TYPE, this);
        Shared.eventBus.listen(LettersResetEvent.TYPE, this);

    }

    public void stop(){
        mPlayingGame = null;
        Shared.eventBus.unlisten(GameStartEvent.TYPE, this);
        Shared.eventBus.unlisten(GameEndEvent.TYPE, this);
        Shared.eventBus.unlisten(GameWinEvent.TYPE, this);
        Shared.eventBus.unlisten(GameLoseEvent.TYPE, this);
        Shared.eventBus.unlisten(WordDiscoverEvent.TYPE, this);
        Shared.eventBus.unlisten(WordRootDiscoverEvent.TYPE, this);
        Shared.eventBus.unlisten(LetterTapEvent.TYPE, this);
        Shared.eventBus.unlisten(RandomizeEvent.TYPE, this);
        Shared.eventBus.unlisten(LettersResetEvent.TYPE, this);
    }

    @Override
    public void onEvent(GameStartEvent event){
        Settings.maxWordSize = 6;

        //configure a game
        mPlayingGame = new Game(new GameConfiguration(3, Settings.maxWordSize));

        //load the screen
        mScreenController.openScreen(Screen.GAME);
    }

    @Override
    public void onEvent(GameEndEvent event){
        //TODO correct this
        //mScreenController.openScreen(Screen.MENU);
        //mPlayingGame = null;
    }

    @Override
    public void onEvent(GameWinEvent event){
        Music.playGameWin();
        Shared.eventBus.notify(new GameEndEvent());
    }
    @Override
    public void onEvent(GameLoseEvent event){
        Music.playGameLose();
        Shared.eventBus.notify(new GameEndEvent());
    }

    @Override
    public void onEvent(WordDiscoverEvent event){
        Music.playWordDiscover();
        mPlayingGame.boardArrangement.wordList.put(event.word, true);
        // See if all words are found
        boolean allFound = true;
        Map wordList = mPlayingGame.boardArrangement.wordList;
        for(Object found: wordList.values()){
            if(!(Boolean)found){
                allFound = false;
            }
        }
        if(allFound){
            Shared.eventBus.notify(new GameWinEvent());
        }
    }

    @Override
    public void onEvent(WordRootDiscoverEvent event){
        Music.playWordRootDiscover();
        mPlayingGame.boardArrangement.wordList.put(event.word, true);
    }

    @Override
    public void onEvent(LetterTapEvent event){
        Music.playLetterTap();
        Map wordList = mPlayingGame.boardArrangement.wordList;
        if(wordList.containsKey(event.word)) {
            if(!(boolean)wordList.get(event.word)) {
                if (event.word.length() == Settings.maxWordSize) {
                    Shared.eventBus.notify(new WordRootDiscoverEvent(event.word));
                } else {
                    Shared.eventBus.notify(new WordDiscoverEvent(event.word));
                }
            }
        }
    }

    @Override
    public void onEvent(RandomizeEvent event){
        Music.playLettersRandomize();
    }

    @Override
    public void onEvent(LettersResetEvent event){
        Music.playLettersReset();
    }

    public Game getActiveGame() {
        return mPlayingGame;
    }

}
