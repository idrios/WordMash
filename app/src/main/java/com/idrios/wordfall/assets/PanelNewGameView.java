package com.idrios.wordfall.assets;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.idrios.wordfall.R;
import com.idrios.wordfall.common.Shared;
import com.idrios.wordfall.events.engine.GameStartEvent;
import com.idrios.wordfall.ui.PopupManager;
import com.idrios.wordfall.utils.Utils;

/**
 * Created by idrios on 8/28/18.
 */

public class PanelNewGameView extends PanelView {

    private int mScreenWidth;
    private int mScreenHeight;

    public final ImageView btnNewgame;
    public final ImageView btnStats;

    public PanelNewGameView(Context context){
        this(context, null);
    }
    public PanelNewGameView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        int margin = getResources().getDimensionPixelSize(R.dimen.margine_top);
        int padding = getResources().getDimensionPixelSize(R.dimen.board_padding);
        mScreenWidth = Utils.screenWidth() - padding*2 - Utils.px(20);
        mScreenHeight = Utils.screenHeight() - padding*2 - margin;

        int btnNewgameWidth = getResources().getDimensionPixelSize(R.dimen.btn_newgame_width);
        int btnNewgameHeight = getResources().getDimensionPixelSize(R.dimen.btn_newgame_height);
        int btnStatsWidth = getResources().getDimensionPixelSize(R.dimen.btn_stats_width);
        int btnStatsHeight = getResources().getDimensionPixelSize(R.dimen.btn_stats_height);
        int btnStatsMarginRight = getResources().getDimensionPixelSize(R.dimen.btn_stats_margin_right);
        int btnStatsMarginBottom = getResources().getDimensionPixelSize(R.dimen.btn_stats_margin_bottom);

        //setGravity(Gravity.CENTER_HORIZONTAL);

        btnNewgame = new ImageView(context);
        btnStats = new ImageView(context);

        int id = 9000;

        btnNewgame.setId(id+4);
        btnStats.setId(id+5);

        RelativeLayout.LayoutParams newgameLayoutParams = new RelativeLayout.LayoutParams(btnNewgameWidth, btnNewgameHeight);
        RelativeLayout.LayoutParams statsLayoutParams = new RelativeLayout.LayoutParams(btnStatsWidth, btnStatsHeight);

        newgameLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        statsLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        statsLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        statsLayoutParams.rightMargin = btnStatsMarginRight;
        statsLayoutParams.bottomMargin = btnStatsMarginBottom;

        Resources res = getResources();
        btnNewgame.setImageDrawable(res.getDrawable(R.drawable.btn_newgame_sign));
        btnStats.setImageDrawable(res.getDrawable(R.drawable.btn_stats_sign));

        btnNewgame.setLayoutParams(newgameLayoutParams);
        btnStats.setLayoutParams(statsLayoutParams);

        btnNewgame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared.eventBus.notify(new GameStartEvent());
            }
        });
        btnStats.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupManager.showPopupStats();
            }
        });

        addView(btnNewgame);
        //addView(btnStats);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public static PanelNewGameView fromXml(Context context, ViewGroup parent){
        return (PanelNewGameView) LayoutInflater.from(context).inflate(R.layout.view_panel_newgame, parent, false);
    }

}
