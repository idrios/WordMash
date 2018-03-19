package com.idrios.wordmash.model;

/**
 * Created by idrios on 2/18/18.
 * The 'Game' class is the existing state of the game.
 */

public class Game {

    public BoardConfiguration boardConfiguration;
    public BoardArrangement boardArrangement;

    public Game(BoardConfiguration bConfig){
        this.boardConfiguration = bConfig;
    }

}
