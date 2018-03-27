package com.idrios.wordmash.model;

import com.idrios.wordmash.model.board.BoardArrangement;
import com.idrios.wordmash.model.wordlist.WordMap;

/**
 * Created by idrios on 2/18/18.
 * The 'Game' class is the existing state of the game.
 */

public class Game {

    public gameConfiguration gameConfiguration;
    public BoardArrangement boardArrangement;
    public WordMap words;


    public Game(gameConfiguration gConfig){
        this.gameConfiguration = gConfig;
    }

}
