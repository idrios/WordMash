package com.idrios.wordmash.assets;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.idrios.wordmash.R;
import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.model.Game;
import com.idrios.wordmash.model.GameConfiguration;
import com.idrios.wordmash.model.board.BoardArrangement;
import com.idrios.wordmash.utils.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by idrios on 4/5/18.
 */

public class BankView extends LinearLayout{

    private static final String TAG = "BankView";

    //Game information
    private LinearLayout.LayoutParams mColLayoutParams;
    private LinearLayout.LayoutParams mRowLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    private BoardArrangement mBoardArrangement;
    private GameConfiguration mGameConfiguration;

    //Dimensions
    private int mScreenWidth;
    private int mScreenHeight;
    private int mSizeTile;
    private int mNumColumn;
    private int mWidthColumn;
    private int mHeightColumn;
    private int mSizeLetter;

    private HashMap<Integer, LinearLayout> mBankColumnReference;
    private HashMap<String, BankWord> mBankWordReference;

    //Strings
    public static final String TILEURLGUESS = "letter_box_guess_single_1100";

    public BankView(Context context){
        this(context, null);
    }
    public BankView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        setGravity(Gravity.CENTER);
        setOrientation(LinearLayout.HORIZONTAL);
        int margin = getResources().getDimensionPixelSize(R.dimen.margine_top);
        int padding = getResources().getDimensionPixelSize(R.dimen.board_padding);
        mScreenWidth = Utils.screenWidth() - padding*2 - Utils.px(20);
        mScreenHeight = Utils.screenHeight() - padding*2 - margin;
        mBankColumnReference = new HashMap<Integer, LinearLayout>();
        mBankWordReference = new HashMap<String, BankWord>();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public static BankView fromXml(Context context, ViewGroup parent){
        return (BankView) LayoutInflater.from(context).inflate(R.layout.bank_view, parent, false);
    }

    public void setBank(Game game){
        // Load board configuration and arrangement
        mGameConfiguration = game.gameConfiguration;
        mBoardArrangement = game.boardArrangement;

        // calculate tile width and height
        int tileMarginLeft = getResources().getDimensionPixelSize(R.dimen.bank_tile_margin_left);
        int tileMarginHorizontal = getResources().getDimensionPixelSize(R.dimen.bank_tile_margin_horizontal);
        int tileMarginRight = getResources().getDimensionPixelSize(R.dimen.bank_tile_margin_right);
        int columnMarginHorizontal = getResources().getDimensionPixelSize(R.dimen.column_margin_horizontal);
        int columnMarginVertical = getResources().getDimensionPixelSize(R.dimen.column_margin_vertical);
        mNumColumn = 3;
        mWidthColumn = mScreenWidth/mNumColumn;
        mHeightColumn = mScreenHeight; //NOT CORRECT
        mSizeTile = Math.round((mWidthColumn - tileMarginLeft -
                ((mGameConfiguration.maxWordSize - 1)*tileMarginHorizontal) - tileMarginRight)
                / mGameConfiguration.maxWordSize);
        mSizeLetter = (int)Math.round(mSizeTile * 0.90);

        mColLayoutParams = new LinearLayout.LayoutParams(mWidthColumn, LayoutParams.MATCH_PARENT);
        mRowLayoutParams.setMargins(tileMarginLeft, 0, tileMarginRight, 0);

        buildBank();

        /* THIS IS DEBUG CODE*/
        //mBankWordReference.get(0).findWord();
        //mBankWordReference.get(1).findWord();
        //mBankWordReference.get(2).findWord();
        /* THIS IS DEBUG CODE */
    }

    public void buildBank(){
        //TODO: Make this no longer based on the keySet?
        Object[] wordArray = mBoardArrangement.wordList.keySet().toArray();
        String[] wordArraySort = new String[wordArray.length];
        int ind = 0;
        for(int len = mGameConfiguration.minWordSize; len <= mGameConfiguration.maxWordSize; len++){
            //NOTE: If you feel like sorting the strings by alphabet in the bank, do it here
            for(int i = 0; i < wordArray.length; i++){
                String word = (String)wordArray[i];
                if(word.length()==len){
                    wordArraySort[ind] = word;
                    ind++;
                }
            }
        }

        for(int i = 0; i < mNumColumn; i++){
            addBankColumn(i);
        }
        for (int id = 0; id < wordArray.length; id++) {
            addBankWord(id, wordArraySort[id]);
        }
    }

    private void addBankColumn(int id){
        LinearLayout colLayout = new LinearLayout(getContext());
        colLayout.setGravity(Gravity.CENTER);
        colLayout.setOrientation(LinearLayout.VERTICAL);

        addView(colLayout, mColLayoutParams);
        mBankColumnReference.put(id, colLayout);
    }

    private void addBankWord(int id, String word){
        int col = id % mNumColumn;
        BankWord bankWord = new BankWord(getContext());
        bankWord.initWord(id, word);
        mBankColumnReference.get(col).addView(bankWord, mRowLayoutParams);
        mBankWordReference.put(word, bankWord);
    }

    public static Bitmap getTileBitmap(int size){
        String drawableResourceName = TILEURLGUESS; //TODO this can be used to choose between themes
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

    public void wordFound(String word){
        mBankWordReference.get(word).findWord();
    }

    private class BankWord extends LinearLayout{
        //Board Info
        public int ID;
        public String WORD;
        public boolean found = false;
        private LinearLayout.LayoutParams mLayoutParams;
        public Map<Integer, TileView> mTileViewReference;

        public BankWord(Context context){
            super(context);
        }

        public void initWord(int id, String word){

            this.ID = id;
            this.WORD = word;
            this.mTileViewReference = new HashMap<Integer, TileView>();
            this.mLayoutParams = new LinearLayout.LayoutParams(mSizeTile, mSizeTile);
            for(int tileId = 0; tileId < WORD.length(); tileId++){
                addTile(tileId);
            }
        }

        public void findWord(){
            found = true;
            for(int tileId = 0; tileId < mTileViewReference.size(); tileId++){
                addLetter(tileId);
            }
            mBoardArrangement.wordList.put(WORD, true);
        }

        private void addTile(int tileId) {
            final TileView tileView = TileView.fromXml(getContext(), this);

            tileView.setId(tileId);
            tileView.setLayoutParams(mLayoutParams);
            this.addView(tileView);
            mTileViewReference.put(tileId, tileView);

            // Render the TileView
            new AsyncTask<Void, Void, Bitmap>(){

                @Override
                protected Bitmap doInBackground(Void... params){
                    return getTileBitmap(mSizeTile);
                }

                @Override
                protected void onPostExecute(Bitmap result){
                    tileView.setTileImage(result);
                }
            }.execute();
        }

        //This will be off-center. Fix that. ------ Actually, amazingly, it is not.
        private void addLetter(int tileId){
            final TileView tileView = mTileViewReference.get(tileId);
            final char c = WORD.charAt(tileId);
            // Render the Letter
            new AsyncTask<Void, Void, Bitmap>(){

                @Override
                protected Bitmap doInBackground(Void... params){
                    return getLetterBitmap(c, mSizeLetter);
                }

                @Override
                protected void onPostExecute(Bitmap result){
                    tileView.setTileImage(result);
                }
            }.execute();
        }


    }

}
