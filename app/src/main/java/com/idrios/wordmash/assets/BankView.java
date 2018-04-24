package com.idrios.wordmash.assets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.idrios.wordmash.R;
import com.idrios.wordmash.model.Game;
import com.idrios.wordmash.utils.Utils;

import java.util.HashMap;

/**
 * Created by idrios on 4/5/18.
 */

public class BankView extends LinearLayout{

    //Dimensions
    private int mScreenWidth;
    private int mScreenHeight;
    private int mSize;

    public BankView(Context context){
        this(context, null);
    }
    public BankView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        int margin = getResources().getDimensionPixelSize(R.dimen.margine_top);
        int padding = getResources().getDimensionPixelSize(R.dimen.board_padding);
        mScreenWidth = Utils.screenWidth() - padding*2 - Utils.px(20); // I have no clue on these numbers here
        mScreenHeight = Utils.screenHeight() - padding*2 - margin;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public static BankView fromXml(Context context, ViewGroup parent){
        return (BankView) LayoutInflater.from(context).inflate(R.layout.bank_view, parent, false);
    }

    public void setBank(Game game){

    }


}
