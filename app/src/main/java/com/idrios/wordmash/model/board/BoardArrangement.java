package com.idrios.wordmash.model.board;

import android.graphics.Bitmap;

import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the setting of the layout.
 *
 * Created by idrios on 2/18/18.
 */

public class BoardArrangement {

    // something for layout of obtained/unobtained words
    // something for mapping of tile to position on board

    public Map<String, Boolean> wordList;
    public Map<Integer, Integer> letterToTileMap;
    public Map<Integer, Integer> tileToLetterMap;

    //TODO: Where do wordLists actually get made?
    public void setWordList(String word){
        wordList = new HashMap<>();
        wordList.put("bug", false);
        wordList.put("bugs", false);
        wordList.put("bed", false);
        wordList.put("sub", false);
        wordList.put("bus", false);
        wordList.put("beg", false);
        wordList.put("begs", false);
        wordList.put("bud", false);
        wordList.put("beds", false);
        wordList.put("budge", false);
        wordList.put("budges", false);
        wordList.put("debug", false);
        wordList.put("debugs", false);
    }
}
