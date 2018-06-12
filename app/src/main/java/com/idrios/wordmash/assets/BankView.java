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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by idrios on 4/5/18.
 */

public class BankView extends LinearLayout{

    private static final String TAG = "BankView";

    //Game information
    private LinearLayout.LayoutParams mRowLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    private BoardArrangement mBoardArrangement;
    private GameConfiguration mGameConfiguration;

    //Dimensions
    private int mScreenWidth;
    private int mScreenHeight;
    private int mSizeTile;
    private int mSizeLetter;

    private HashMap<String, BankWord> mBankWordReference;

    //Strings
    public static final String TILEURLGUESS = "letter_box_guess_single_1100";

    public BankView(Context context){
        this(context, null);
    }
    public BankView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        setGravity(Gravity.CENTER);
        setOrientation(LinearLayout.VERTICAL);
        int margin = getResources().getDimensionPixelSize(R.dimen.margine_top);
        int padding = getResources().getDimensionPixelSize(R.dimen.board_padding);
        mScreenWidth = Utils.screenWidth() - padding*2 - Utils.px(20); // I have no clue on these numbers here
        mScreenHeight = Utils.screenHeight() - padding*2 - margin;
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
        int tileMarginLeft = getResources().getDimensionPixelSize(R.dimen.tile_left_margin);
        int tileMarginHorizontal = getResources().getDimensionPixelSize(R.dimen.tile_horizontal_margin)/4;
        int tileMarginRight = getResources().getDimensionPixelSize(R.dimen.tile_right_margin);
        int width = (mScreenWidth - tileMarginLeft - tileMarginRight)/4;
        mSizeTile = Math.round((width - ((mGameConfiguration.maxWordSize - 1)*tileMarginHorizontal)) / mGameConfiguration.maxWordSize);
        mSizeLetter = (int)Math.round(mSizeTile * 0.90);

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
        //TODO: Sort this string
        for (int row = 0; row < wordArray.length; row++) {
            addBankWord(row, (String)wordArray[row]);

        }
    }

    private void addBankWord(int row, String word){
        BankWord bankWord = new BankWord(getContext());
        bankWord.initWord(row, word);
        addView(bankWord, mRowLayoutParams);
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
        public int ROW;
        public String WORD;
        public boolean found = false;
        private LinearLayout.LayoutParams mLayoutParams;
        public Map<Integer, TileView> mTileViewReference;

        public BankWord(Context context){
            super(context);
        }

        public void initWord(int row, String word){
            this.ROW = row;
            this.WORD = word;
            this.mTileViewReference = new HashMap<Integer, TileView>();
            this.mLayoutParams = new LinearLayout.LayoutParams(mSizeTile, mSizeTile);
            for(int id = 0; id < WORD.length(); id++){
                addTile(id);
            }
        }

        public void findWord(){
            found = true;
            for(int id = 0; id < mTileViewReference.size(); id++){
                addLetter(id);
            }
            mBoardArrangement.wordList.put(WORD, true);
        }

        private void addTile(int id) {
            final TileView tileView = TileView.fromXml(getContext(), this);

            tileView.setId(id);
            tileView.setLayoutParams(mLayoutParams);
            this.addView(tileView);
            mTileViewReference.put(id, tileView);

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
            System.out.println("Done");
        }

        //This will be off-center. Fix that. ------ Actually, amazingly, it is not.
        private void addLetter(int id){
            final TileView tileView = mTileViewReference.get(id);
            final char c = WORD.charAt(id);
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
