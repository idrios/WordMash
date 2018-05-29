package com.idrios.wordmash.model.board;

import android.graphics.Bitmap;

import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.utils.Utils;

import java.util.Map;

/**
 * This is the setting of the layout.
 *
 * Created by idrios on 2/18/18.
 */

public class BoardArrangement {

    // something for layout of obtained/unobtained words
    // something for mapping of tile to position on board

    public Map<String, Boolean> wordFound;
    public Map<Integer, Integer> letterToTileMap;
    public Map<Integer, Integer> tileToLetterMap;


}
