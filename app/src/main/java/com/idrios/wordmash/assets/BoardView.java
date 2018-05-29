package com.idrios.wordmash.assets;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.idrios.wordmash.R;
import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.events.engine.LetterTappedEvent;
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
    //TODO: randomize the positions of the letters before the get rendered (so people can't see the answers)

    private static final String TAG = "BoardView";

    //Layout information
    private LinearLayout TileLayout;
    private BoardArrangement mBoardArrangement;
    private GameConfiguration mGameConfiguration;

    //Dimensions
    private int mScreenWidth;
    private int mScreenHeight;
    private int mSizeTile;
    private int mSizeLetter;

    //Board Info
    private Map<Integer, TileView> mTileViewReference; // {1,2,3,4,5,6   7,8,9,10,11,12}
    private Map<Integer, LetterView> mLetterViewReference; // {1,2,3,4,5,6}

    //Strings
    public static final String TILEURL = "letter_box_single_1100";
    public static final String TILEURLGUESS = "letter_box_guess_single_1100";


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

        buildBoard();
        setLetterChars(mGameConfiguration.WORD);
    }

    public void buildBoard(){
        // TODO upload WordMash background

        for(int row = 0; row < 2; row++) {
            for(int tileNum = 0; tileNum < mGameConfiguration.maxWordSize; tileNum++ ){
                addTile(row, (row * mGameConfiguration.maxWordSize) + tileNum, this);
            }
        }
        for(int id = 0; id < mGameConfiguration.maxWordSize; id++){
            addLetter(id, this);
            setLetterPosition(id, id + mGameConfiguration.maxWordSize);
        }
    }

    private void addTile(final int row, final int id, ViewGroup parent){
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
                return getTileBitmap(row, mSizeTile);
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

        // Create Layout Params
        RelativeLayout.LayoutParams letterLayoutParams = new RelativeLayout.LayoutParams(mSizeLetter, mSizeLetter);

        // Apply Layout Params to LetterView
        letterView.setLayoutParams(letterLayoutParams);

        // Put LetterView on the board
        parent.addView(letterView);
        parent.setClipChildren(false);
        mLetterViewReference.put(id, letterView);

        // Draw the LetterView
        new AsyncTask<Void, Void, Bitmap>(){

            @Override
            protected Bitmap doInBackground(Void... params){
                return getLetterBitmap('\u0000', mSizeLetter);
            }

            @Override
            protected void onPostExecute(Bitmap result){
                letterView.setLetterImage(result);
            }
        }.execute();

        letterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //Move up or down, based on position
                if(mBoardArrangement.letterToTileMap.get(id) < mGameConfiguration.maxWordSize) {
                    moveLetterDown(id);
                } else{
                    moveLetterUp(id);
                }
                String curWord = getWord(mBoardArrangement.tileToLetterMap);
                Shared.eventBus.notify(new LetterTappedEvent(curWord));
            }
        });
    }

    public boolean moveLetterUp(int letterId){

        // This while-loop finds which tile is not occupied by a letterView
        int tileId = -1;
        int lastId = 0; // lock the previous letter
        for(int id = 0; id<mGameConfiguration.maxWordSize; id++){
            if(mBoardArrangement.tileToLetterMap.get(id) < 0){
                tileId = id;
                if(id>0) {
                    mLetterViewReference.get(mBoardArrangement.tileToLetterMap.get(lastId)).isLocked = true;
                }
                break;
            }
            lastId = id;
        }
        setLetterPosition(letterId, tileId);
        return true;
    }

    public boolean moveLetterDown(int letterId){
        if(mLetterViewReference.get(letterId).isLocked){
            return true;
        }

        // Unlock the previous letter
        int oldTileId = mBoardArrangement.letterToTileMap.get(letterId);
        if(oldTileId > 0) {
            mLetterViewReference.get(mBoardArrangement.tileToLetterMap.get(oldTileId - 1)).isLocked = false;
        }

        // This while-loop finds which tile is not occupied by a letterView
        int tileId = -1;
        for(int id = mGameConfiguration.maxWordSize; id<2*mGameConfiguration.maxWordSize; id++){
            if(mBoardArrangement.tileToLetterMap.get(id) < 0){
                tileId = id;
                break;
            }
        }

        setLetterPosition(letterId, tileId);
        return true;
    }

    //TODO: move letters to chosen tileId
    public boolean setLetterPosition(int letterId, int tileId){
        LetterView letterView = mLetterViewReference.get(letterId);

        TileView tileView = mTileViewReference.get(tileId);

        RelativeLayout.LayoutParams letterLayoutParams = new RelativeLayout.LayoutParams(mSizeLetter, mSizeLetter);
        RelativeLayout.LayoutParams tileLayoutParams = (RelativeLayout.LayoutParams)tileView.getLayoutParams();
        int[] xy = new int[2];

        // Add new, correct rules
        letterLayoutParams.addRule(RelativeLayout.ALIGN_LEFT,
                Integer.parseInt(getResources().getString(R.string.TileViewIdStartVal)) + tileId);
        letterLayoutParams.leftMargin = ((mSizeTile - mSizeLetter)/2);
        letterLayoutParams.addRule(RelativeLayout.ALIGN_TOP,
                Integer.parseInt(getResources().getString(R.string.TileViewIdStartVal)) + tileId);
        letterLayoutParams.topMargin = ((mSizeTile - mSizeLetter)/2);

        letterView.setLayoutParams(letterLayoutParams);

        mBoardArrangement.tileToLetterMap.put(mBoardArrangement.letterToTileMap.get(letterId), -1); //make sure the old tile is now at -1
        mBoardArrangement.tileToLetterMap.put(tileId, letterId);
        mBoardArrangement.letterToTileMap.put(letterId, tileId);
        return true;
    }

    public void setLetterChars(String word){
        setLetterChars(word.toLowerCase().toCharArray());
    }

    public void setLetterChars(char[] chars){
        for(int i = 0; i<chars.length; i++){
            setLetterChar(chars[i], i);
        }
    }

    public void setLetterChar(final char c, int id){
        final LetterView view = mLetterViewReference.get(id);
        view.setLetter(c);

        // Draw the LetterView
        new AsyncTask<Void, Void, Bitmap>(){

            @Override
            protected Bitmap doInBackground(Void... params){
                return getLetterBitmap(c, mSizeLetter);
            }

            @Override
            protected void onPostExecute(Bitmap result){
                view.setLetterImage(result);
            }
        }.execute();


    }

    public String getWord(Map<Integer, Integer> tileToLetterMap){
        String s = "";
        for(int i = 0; i<mGameConfiguration.maxWordSize; i++){
            if(tileToLetterMap.get(i) < 0){
                return s;
            }
            s+=mLetterViewReference.get(tileToLetterMap.get(i)).getLetter();
        }
        return s;
    }

    public static Bitmap getTileBitmap(int row, int size){
        String string;
        if(row == 0) string = TILEURLGUESS;
        else string = TILEURL;
        String drawableResourceName = string; //TODO this can be used to choose between themes
        int drawableResourceId = Shared.context.getResources().getIdentifier(drawableResourceName, "drawable", Shared.context.getPackageName());

        Bitmap bitmap = Utils.scaleDown(drawableResourceId, size, size);

        return bitmap;
    }

    public static Bitmap getLetterBitmap(char c, int size){
        String drawableResourceName = "letter_" + c;
        int drawableResourceId = Shared.context.getResources().getIdentifier(drawableResourceName, "drawable", Shared.context.getPackageName());
        Bitmap bitmap = Utils.scaleDown(drawableResourceId, size, size);
        return bitmap;
    }

}
