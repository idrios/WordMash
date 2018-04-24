package com.idrios.wordmash.assets;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

public class BoardView extends LinearLayout {

    //Layout information
    private BoardArrangement mBoardArrangement;
    private GameConfiguration mGameConfiguration;
    private LinearLayout.LayoutParams mRowLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    private LinearLayout.LayoutParams mTileLayoutParams;

    //Dimensions
    private int mScreenWidth;
    private int mScreenHeight;
    private int mSizeTile;
    private int mSizeLetter;
    private int[] mTileCoordsX;
    private int[] mTileCoordsY;

    //Board Info
    private Character[] mLetters;
    private Map<Integer, TileView> mViewReference;

    public BoardView(Context context){
        this(context, null);
    }

    public BoardView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        int margin = getResources().getDimensionPixelSize(R.dimen.margine_top);
        int padding = getResources().getDimensionPixelSize(R.dimen.board_padding);
        mScreenWidth = Utils.screenWidth() - padding*2 - Utils.px(20); // I have no clue on these numbers here
        mScreenHeight = Utils.screenHeight() - padding*2 - margin;
        mViewReference = new HashMap<Integer, TileView>();

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
        int tileMarginLeft = getResources().getDimensionPixelSize(R.dimen.letter_left_margin);
        int tileMarginHorizontal = getResources().getDimensionPixelSize(R.dimen.letter_horizontal_margin);
        int tileMarginRight = getResources().getDimensionPixelSize(R.dimen.letter_right_margin);
        int width = mScreenWidth - tileMarginLeft - tileMarginRight;
        mSizeTile = Math.round((width - ((mGameConfiguration.maxWordSize - 1)*tileMarginHorizontal)) / mGameConfiguration.maxWordSize);
        mSizeLetter = (int)Math.round(mSizeTile * 0.95);

        // calculate positions of tiles
        mTileCoordsX =  new int[mGameConfiguration.maxWordSize];
        for(int i = 0; i < mTileCoordsX.length; i++){
            mTileCoordsX[i] = (i-((mGameConfiguration.maxWordSize-1)/2))*(mSizeTile + tileMarginHorizontal);
        }
        mTileCoordsY = new int[2];
        for(int i = 0; i < mTileCoordsY.length; i++){
            mTileCoordsY[i] = (i-(1/2))*(mSizeTile + (3*tileMarginHorizontal)); // Arbitrarily chosen the vertical tile margin to be 3x the horizontal tile margin
        }

        mTileLayoutParams = new LinearLayout.LayoutParams(mSizeTile, mSizeTile);
        // TODO update mTileLayoutParams to be correct

        buildBoard();

    }

    public void buildBoard(){
        // TODO upload WordMash background

        //mLetters = new Character[]{'c', 'h', 'e', 'r', 'r', 'y'};

        // TODO give correct layoutParams to tiles
        LinearLayout linearLayout = new LinearLayout(getContext());

        for(int tileNum = 0; tileNum < mGameConfiguration.maxWordSize; tileNum++ ){
            addTile(tileNum, linearLayout);
        }

        // add board to fragment view
        addView(linearLayout, mRowLayoutParams);
        linearLayout.setClipChildren(false);
    }

    private void addTile(final int id, ViewGroup parent){
        final TileView tileView = TileView.fromXml(getContext(), parent);
        tileView.setLayoutParams(mTileLayoutParams);
        parent.addView(tileView);
        parent.setClipChildren(false);
        mViewReference.put(id, tileView);

        new AsyncTask<Void, Void, Bitmap>(){

            @Override
            protected Bitmap doInBackground(Void... params){
                return mBoardArrangement.getLetterTileBitmap(id, mSizeTile);
            }

            @Override
            protected void onPostExecute(Bitmap result){
                tileView.setTileImage(result);
            }
        }.execute();

        tileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Shared.eventBus.notify(new LetterTappedEvent(id));
            }
        });



        //TODO give give animations
    }
}
