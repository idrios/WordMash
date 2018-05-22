package com.idrios.wordmash.assets;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.idrios.wordmash.R;
import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.events.ui.LetterTappedEvent;
import com.idrios.wordmash.model.board.BoardArrangement;
import com.idrios.wordmash.model.GameConfiguration;
import com.idrios.wordmash.model.Game;
import com.idrios.wordmash.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by idrios on 2/17/18.
 *
 *  <--------MW--------->         (mScreenWidth = ScreenWidth - margins)
 * <----------W---------->        (Screen Width)
 * -----------------------        ^ ^
 * |                     |        | |
 * |                     |        | |
 * |                     |       TM | (Top Margin)
 * |                     |        | |
 * |    PT               |        | | (Padding btw Tiles)
 * |  >|  |<>|  |< |  |  |  |   | | H (Screen Height)
 * |---------------------|  | - v v |
 * || [ ][O][ ][ ][ ][ ]||  v -PT   | (Padding Top)
 * || [O][ ][O][O][O][O]||-PB   ^   | (Padding Bottom)
 * |---------------------|- ^   |   |
 *>||<-LM                |  |       | (Left Margin)
 * -----------------------          v
 *
 * Better to constrain from the bottom?
 *
 *
 */

public class BoardView extends RelativeLayout {

    private static final String TAG = "BoardView";

    //Layout information
    private LinearLayout TileLayout;
    private BoardArrangement mBoardArrangement;
    private GameConfiguration mGameConfiguration;
    private RelativeLayout.LayoutParams mLetterLayoutParams;


    //Dimensions
    private int mScreenWidth;
    private int mScreenHeight;
    private int mSizeTile;
    private int mSizeLetter;

    //Board Info
    private Map<Integer, TileView> mTileViewReference;
    private Map<Integer, LetterView> mLetterViewReference;

    public BoardView(Context context){
        this(context, null);
    }

    public BoardView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        setGravity(Gravity.CENTER);
        int margin = getResources().getDimensionPixelSize(R.dimen.margine_top);
        int padding = getResources().getDimensionPixelSize(R.dimen.board_padding);
        mScreenWidth = Utils.screenWidth() - padding*2 - Utils.px(20); // I have no clue on these numbers here
        mScreenHeight = Utils.screenHeight() - padding*2 - margin;
        mTileViewReference = new HashMap<Integer, TileView>();
        mLetterViewReference = new HashMap<Integer, LetterView>();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public static BoardView fromXml(Context context, ViewGroup parent){
        return (BoardView) LayoutInflater.from(context).inflate(R.layout.board_view, parent, false);
    }

    public void setBoard(Game game){
        // Load board configuration and arrangement
        mGameConfiguration = game.gameConfiguration;
        mBoardArrangement = game.boardArrangement;

        // calculate tile width and height
        int tileMarginLeft = getResources().getDimensionPixelSize(R.dimen.tile_left_margin);
        int tileMarginHorizontal = getResources().getDimensionPixelSize(R.dimen.tile_horizontal_margin);
        int tileMarginRight = getResources().getDimensionPixelSize(R.dimen.tile_right_margin);
        int width = mScreenWidth - tileMarginLeft - tileMarginRight;
        mSizeTile = Math.round((width - ((mGameConfiguration.maxWordSize - 1)*tileMarginHorizontal)) / mGameConfiguration.maxWordSize);
        mSizeLetter = (int)Math.round(mSizeTile * 0.90);

        // calculate positions of tiles
        /* TODO Consider deleting all of this code...
        mTileCoordsX =  new int[mGameConfiguration.maxWordSize];
        for(int i = 0; i < mTileCoordsX.length; i++){
            mTileCoordsX[i] = (i-((mGameConfiguration.maxWordSize-1)/2))*(mSizeTile + tileMarginHorizontal);
        }
        mTileCoordsY = new int[2];
        for(int i = 0; i < mTileCoordsY.length; i++){
            mTileCoordsY[i] = (i-(1/2))*(mSizeTile + (3*tileMarginHorizontal)); // Arbitrarily chosen the vertical tile margin to be 3x the horizontal tile margin
        }

        mTileLayoutParams = new RelativeLayout.LayoutParams(mSizeTile, mSizeTile);
        */
        mLetterLayoutParams = new RelativeLayout.LayoutParams((int)(mSizeTile*.90), (int)(mSizeTile*.90));


        // TODO update mTileLayoutParams to be correct

        buildBoard();

    }

    public void buildBoard(){
        // TODO upload WordMash background

        //mLetters = new Character[]{'c', 'h', 'e', 'r', 'r', 'y'};

        // TODO give correct layoutParams to tiles
        for(int row = 0; row < 2; row++) {
            for(int tileNum = 0; tileNum < mGameConfiguration.maxWordSize; tileNum++ ){
                addTile(row, (row * mGameConfiguration.maxWordSize) + tileNum, this);
            }
        }
        for(int id = 0; id < mGameConfiguration.maxWordSize; id++){
            addLetter(id, this);
            setLetterPosition(id, id);
        }

    }

    private void addTile(int row, final int id, ViewGroup parent){
        // Get the TileView
        final TileView tileView = TileView.fromXml(getContext(), parent);

        // Give the tile a proper View ID (viewId = 1000 + id, where 1000 is from strings.xml)
        tileView.setId(Integer.parseInt(getResources().getString(R.string.TileViewIdStartVal)) + id);

        // Create LayoutParams
        RelativeLayout.LayoutParams tileLayoutParams = new RelativeLayout.LayoutParams(mSizeTile, mSizeTile);
        tileLayoutParams.addRule(RelativeLayout.RIGHT_OF,
                Integer.parseInt(getResources().getString(R.string.TileViewIdStartVal))
                        + id - 1 - (row * mGameConfiguration.maxWordSize));
        tileLayoutParams.addRule(RelativeLayout.BELOW,
                Integer.parseInt(getResources().getString(R.string.TileViewIdStartVal))
                        + id - mGameConfiguration.maxWordSize);
        if (row>0){
            tileLayoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.tile_top_margin);
        }

        // Apply Layout Params to tileView
        tileView.setLayoutParams(tileLayoutParams);

        // Put TileView in BoardView
        parent.addView(tileView);
        parent.setClipChildren(false);
        mTileViewReference.put(id, tileView);

        // Render the TileView
        new AsyncTask<Void, Void, Bitmap>(){

            @Override
            protected Bitmap doInBackground(Void... params){
                return mBoardArrangement.getTileBitmap(id, mSizeTile);
            }

            @Override
            protected void onPostExecute(Bitmap result){
                tileView.setTileImage(result);
            }
        }.execute();

        //TODO give animations
    }

    public void addLetter(final int id, ViewGroup parent){
        // Get the LetterView
        final LetterView letterView = LetterView.fromXml(getContext(), parent);
        letterView.setLayoutParams(mLetterLayoutParams);
        parent.addView(letterView);
        parent.setClipChildren(false);
        mLetterViewReference.put(id, letterView);

        new AsyncTask<Void, Void, Bitmap>(){

            @Override
            protected Bitmap doInBackground(Void... params){
                return mBoardArrangement.getTileBitmap(id, mSizeLetter);
            }

            @Override
            protected void onPostExecute(Bitmap result){
                letterView.setLetterImage(result);
            }
        }.execute();

        letterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Shared.eventBus.notify(new LetterTappedEvent(id));
            }
        });

    }

    public void setLetterPosition(int letterId, int tileId){
        LetterView letterView = mLetterViewReference.get(letterId);
        TileView tileView = mTileViewReference.get(tileId);

        RelativeLayout.LayoutParams letterLayoutParams = new RelativeLayout.LayoutParams(mSizeLetter, mSizeLetter);
        letterLayoutParams.leftMargin = 100*tileId;

        letterView.setLayoutParams(letterLayoutParams);

    }


}
