package com.idrios.wordmash.assets;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.idrios.wordmash.R;

/**
 * Created by idrios on 4/24/18.
 * The LetterView is the pink orb that moves around
 */

public class LetterView extends FrameLayout {

    private static final String TAG = "LetterView";
    private ImageView mLetterImage;
    private Character mLetter;

    public LetterView(Context context){
        this(context, null);
    }

    public LetterView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    public void setLetter(Character letter){
        mLetter = letter;
    }
    public Character getLetter(){
        return mLetter;
    }

    public static LetterView fromXml(Context context, ViewGroup parent){
        return (LetterView) LayoutInflater.from(context).inflate(R.layout.letter_view, parent, false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLetterImage = (ImageView)findViewById(R.id.image);
    }

    public void setLetterImage(Bitmap bitmap){
        mLetterImage.setImageBitmap(bitmap);
    }

    ////////// Code for flashy animations here ///////////
    //TODO Add animations for the tiles when clicked



}
