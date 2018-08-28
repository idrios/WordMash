package com.idrios.wordfall.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idrios.wordfall.R;

/**
 * Created by idrios on 8/23/18.
 */

public class PopupWonView extends RelativeLayout {

    public PopupWonView(Context context){
        this(context, null);
    }
    public PopupWonView(Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_popup_won, this, true);

    }
}
