package com.idrios.wordmash.model.board;

import android.graphics.Bitmap;

import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.utils.Utils;

import java.util.Map;

/**
 * This is the setting of the layout. Only settings are stored here and methods to obtain settings,
 * no algorithms or program execution are done here.
 *
 * Created by idrios on 2/18/18.
 */

public class BoardArrangement {

    // something for layout of obtained/unobtained words
    // something for mapping of tile to position on board
    public Map<Integer, String> letterUrls;
    public int boardPositions;

    public BoardArrangement(Map<Integer, String> letterUrls){
        this.letterUrls = letterUrls;
    }

    public Bitmap getLetterTileBitmap(int id, int size){
        String string = letterUrls.get(id);
        String drawableResourceName = string; //TODO this is hard-coded, fix this
        int drawableResourceId = Shared.context.getResources().getIdentifier(drawableResourceName, "drawable", Shared.context.getPackageName());

        Bitmap bitmap = Utils.scaleDown(drawableResourceId, size, size);

        return bitmap;


    }

}
