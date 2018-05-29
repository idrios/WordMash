package com.idrios.wordmash.model;

import com.idrios.wordmash.model.board.BoardArrangement;
import com.idrios.wordmash.model.wordlist.WordMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by idrios on 2/18/18.
 * The 'Game' class is the existing state of the game.
 */

public class Game {

    public GameConfiguration gameConfiguration;
    public BoardArrangement boardArrangement;
    public WordMap words;
    public boolean ready;

    public Game(GameConfiguration gConfig){
        this.gameConfiguration = gConfig;
        this.boardArrangement = randomArrangement();
        loadWordMap();
    }

    private void loadWordMap(){

    }

    private BoardArrangement randomArrangement(){
        BoardArrangement boardArrangement = new BoardArrangement();
        Map<Integer, Integer> tileToLetterMap = new HashMap<Integer, Integer>();
        Map<Integer, Integer> letterToTileMap = new HashMap<Integer, Integer>();
        for(int i = 0; i<2*gameConfiguration.maxWordSize; i++){
            tileToLetterMap.put(i, -1);
        }
        for(int i = 0; i<gameConfiguration.maxWordSize; i++){
            letterToTileMap.put(i, i+gameConfiguration.maxWordSize);
            tileToLetterMap.put(i+gameConfiguration.maxWordSize, i);
        }
        boardArrangement.letterToTileMap = letterToTileMap;
        boardArrangement.tileToLetterMap = tileToLetterMap;
        return boardArrangement;
    }



}
