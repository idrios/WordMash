package com.idrios.wordfall.assets;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.idrios.wordfall.R;
import com.idrios.wordfall.common.Shared;
import com.idrios.wordfall.model.Game;
import com.idrios.wordfall.model.GameConfiguration;
import com.idrios.wordfall.model.board.BoardArrangement;
import com.idrios.wordfall.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by idrios on 4/5/18.
 */

/**
 * <ScrollView>
 *   <LinearLayout BankViewLayout

 */
public class BankView extends ScrollView{

    private static final String TAG = "BankView";

    //Game information
    private final LinearLayout bankViewLayout = new LinearLayout(getContext());
    private final LinearLayout.LayoutParams mViewLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    private LinearLayout.LayoutParams mColLayoutParams;
    private final LinearLayout.LayoutParams mRowLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
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
    public static final String TILEURLGUESS = "letter_tile_new_blue_small";

    public BankView(Context context){
        this(context, null);
    }
    public BankView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(true);
        bankViewLayout.setGravity(Gravity.CENTER);
        bankViewLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(bankViewLayout, mViewLayoutParams);
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
        mColLayoutParams.setMargins(0, columnMarginVertical, 0, columnMarginVertical);
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

        bankViewLayout.addView(colLayout, mColLayoutParams);
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

    public static Bitmap getLetterBitmap(char c, boolean found, int size){
        String drawableResourceName;
        if(found) {
            drawableResourceName = "letter_" + c + "_new_small";
        } else{
            drawableResourceName = "letter_" + c + "_new_small_blue";
        }

        int drawableResourceId = Shared.context.getResources().getIdentifier(drawableResourceName, "drawable", Shared.context.getPackageName());
        Bitmap bitmap = Utils.scaleDown(drawableResourceId, size, size);
        return bitmap;
    }

    public void wordFound(String word){
        mBankWordReference.get(word).findWord();
    }

    public void showAll(){ //TODO make this show the words in a different color
        for(BankWord word : mBankWordReference.values()){
            if(!word.found){
                word.showWord();
            }
        }
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
            showWord();
        }

        public void showWord(){
            for(int tileId = 0; tileId < mTileViewReference.size(); tileId++){
                addLetter(tileId, found);
            }
            mBoardArrangement.wordList.put(WORD, true);
        }

        private void addTile(int tileId) {
            final TileView tileView = TileView.fromXml(getContext(), this);

            tileView.setId(tileId);
            tileView.setLayoutParams(mLayoutParams);
            addView(tileView);
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
        private void addLetter(int tileId, final boolean found){
            final TileView tileView = mTileViewReference.get(tileId);
            final char c = WORD.charAt(tileId);
            // Render the Letter
            new AsyncTask<Void, Void, Bitmap>(){

                @Override
                protected Bitmap doInBackground(Void... params){
                    return getLetterBitmap(c, found, mSizeLetter);
                }

                @Override
                protected void onPostExecute(Bitmap result){
                    tileView.setTileImage(result);
                }
            }.execute();
        }


    }

}
