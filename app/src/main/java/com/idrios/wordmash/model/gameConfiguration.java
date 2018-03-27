package com.idrios.wordmash.model;

/**
 * These are config parameters; word size, difficulty, language, anything that must be predetermined
 *
 * Created by idrios on 2/18/18.
 */

public class gameConfiguration {

    public int minWordSize;
    public int maxWordSize;
    public String WORD;

    public gameConfiguration(int lenMin, int lenMax){
        minWordSize = lenMin;
        maxWordSize = lenMax;
    }
    public void setWord(){
        #1 PUT IN THE FUNCTION FROM EFWORDSCRAMBLE TO MAKE A WORD
    }
    public void setWord(String word){
        this.WORD = word;
    }


}
