package com.idrios.wordfall.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.idrios.wordfall.R;

/**
 * Created by idrios on 8/23/18.
 */

public class PopupStatsView extends RelativeLayout {

    public PopupStatsView(Context context){
        this(context, null);
    }
    public PopupStatsView(Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_popup_won, this, true);
    }
}
