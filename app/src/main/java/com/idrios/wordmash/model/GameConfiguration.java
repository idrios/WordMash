package com.idrios.wordmash.model;

/**
 * These are config parameters; word size, difficulty, language, anything that must be predetermined
 *
 * Created by idrios on 2/18/18.
 */

public class GameConfiguration {

    public int minWordSize;
    public int maxWordSize;
    public String WORD;

    public GameConfiguration(int lenMin, int lenMax){
        minWordSize = lenMin;
        maxWordSize = lenMax;
    }

    public void setWord(String word){
        this.WORD = word;
    }


}
