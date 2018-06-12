package com.idrios.wordmash.model;

import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.model.board.BoardArrangement;
import com.idrios.wordmash.model.wordlist.HashmapHelper;
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
        this.boardArrangement = initArrangement();
        this.words = initWordMap();
        this.gameConfiguration.setWord(words.MAINWORD);
        this.boardArrangement.setWordList(words);

        }


    private BoardArrangement initArrangement(){
        BoardArrangement boardArrangement = new BoardArrangement();
        Map<Integer, Integer> tileToLetterMap = new HashMap<Integer, Integer>();
        Map<Integer, Integer> letterToTileMap = new HashMap<Integer, Integer>();
        int[] order = randomOrder();
        for(int i = 0; i<2*gameConfiguration.maxWordSize; i++){
            tileToLetterMap.put(i, -1);
        }
        for(int i = 0; i<gameConfiguration.maxWordSize; i++){
            letterToTileMap.put(order[i], i+gameConfiguration.maxWordSize);
            tileToLetterMap.put(i+gameConfiguration.maxWordSize, order[i]);
        }
        boardArrangement.letterToTileMap = letterToTileMap;
        boardArrangement.tileToLetterMap = tileToLetterMap;
        return boardArrangement;
    }

    private int[] randomOrder(){
        int arrSize = this.gameConfiguration.maxWordSize;

        // Find a random order
        int[] arr = new int[arrSize];
        int[] newArr = new int[arrSize];

        for(int i = 0; i < arrSize; i++){
            arr[i] = i+1;
        }
        //arr[] = {1, 2, 3, 4, 5, 6}

        for(int i = 0; i < arrSize; i++){
            int randInd = (int)Math.floor(Math.random()*(arrSize-i)); // Get a random number from a size of how many are available
            for(int j = 0; j < arrSize; j++){
                while(newArr[j] > 0){
                    j+=1;
                }
                if(randInd <= 0){
                    newArr[j] = arr[i];
                    break;
                }
                randInd -= 1;
            }
        }
        //newArr[] = {5, 3, 1, 6, 2, 4} .. or some other random order

        for(int i = 0; i < arrSize; i++) {
            newArr[i] -= 1; // Decrement all values in new
        }
        //newArr[] = {4, 2, 0, 5, 1, 3} .. now corresponds correctly with letterIds.
        return newArr;
    }

    private WordMap initWordMap(){
        int wordSize = gameConfiguration.maxWordSize;
        String[] wordlist = null;
        switch (wordSize){
            case 3:
                wordlist = HashmapHelper.threeS;
                break;
            case 4:
                wordlist = HashmapHelper.fourS;
                break;
            case 5:
                wordlist = HashmapHelper.fiveS;
                break;
            case 6:
                wordlist = HashmapHelper.sixS;
                break;
            default:
            throw new Error("Maximum word size must be between 3 and 6");
        }
        int index = (int)(wordlist.length * Math.random());
        String word = wordlist[index];
        return new WordMap(word);
    }


}
