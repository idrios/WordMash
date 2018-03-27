package com.idrios.wordmash.assets;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.idrios.wordmash.R;
import com.idrios.wordmash.model.board.BoardArrangement;
import com.idrios.wordmash.model.BoardConfiguration;
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
    private BoardConfiguration mBoardConfiguration;
    private LinearLayout.LayoutParams mTileLayoutParams;

    //Dimensions
    private int mScreenWidth;
    private int mScreenHeight;
    private int mBoardCenterX;
    private int mBoardCenterY;
    private int mSize;
    private int[] mTileCoordsX;
    private int[] mTileCoordsY;
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
        mBoardConfiguration = game.boardConfiguration;
        mBoardArrangement = game.boardArrangement;

        // calculate tile width and height
        int tileMarginLeft = getResources().getDimensionPixelSize(R.dimen.letter_left_margin);
        int tileMarginHorizontal = getResources().getDimensionPixelSize(R.dimen.letter_horizontal_margin);
        int tileMarginRight = getResources().getDimensionPixelSize(R.dimen.letter_right_margin);
        int width = mScreenWidth - tileMarginLeft - tileMarginRight;
        mSize = Math.round((width - ((mBoardConfiguration.maxWordSize - 1)*tileMarginHorizontal)) / mBoardConfiguration.maxWordSize);

        // calculate positions of tiles
        mBoardCenterX = mScreenWidth / 2; // TODO make this a correct calculation
        mBoardCenterY = mScreenHeight / 2; // TODO make this a correct calculation
        mTileCoordsX =  new int[mBoardConfiguration.maxWordSize];
        for(int i = 0; i < mTileCoordsX.length; i++){
            mTileCoordsX[i] = (i-((mBoardConfiguration.maxWordSize-1)/2))*(mSize + tileMarginHorizontal);
        }
        mTileCoordsY = new int[2];
        for(int i = 0; i < mTileCoordsY.length; i++){
            mTileCoordsY[i] = (i-(1/2))*(mSize + (3*tileMarginHorizontal)); // Arbitrarily chosen the vertical tile margin to be 3x the horizontal tile margin
        }

        mTileLayoutParams = new LinearLayout.LayoutParams(mSize, mSize);
        // TODO update mTileLayoutParams to be correct

        buildBoard();

    }

    public void buildBoard(){
        // TODO upload WordMash background

        // TODO give correct layoutParams to tiles
        LinearLayout linearLayout = new LinearLayout(getContext());

        for(int tileNum = 1; tileNum < mBoardConfiguration.maxWordSize+1; tileNum++ ){
            addTile(tileNum, linearLayout);
        }

    }

    private void addTile(final int id, ViewGroup parent){
        final TileView tileView = TileView.fromXml(getContext(), parent);
        tileView.setLayoutParams(mTileLayoutParams);
        parent.addView(tileView);
        parent.setClipChildren(false);
        mViewReference.put(id, tileView);

        //TODO load images via bitmap
        new AsyncTask<Void, Void, Bitmap>(){

            @Override
            protected Bitmap doInBackground(Void... params){
                return mBoardArrangement.getTileBitmap(id, mSize);
            }

            @Override
            protected void onPostExecute(Bitmap result){
                tileView.setTileImage(result);
            }
        }.execute();


        //TODO set onclicklistener

        //TODO give give animations
    }
}
