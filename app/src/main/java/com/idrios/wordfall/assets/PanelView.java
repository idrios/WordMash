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
import com.idrios.wordfall.events.engine.GameLoseEvent;
import com.idrios.wordfall.events.engine.RandomizeEvent;
import com.idrios.wordfall.events.engine.LettersResetEvent;
import com.idrios.wordfall.utils.Utils;

/**
 * Created by idrios on 6/24/18.
 */

public class PanelView extends RelativeLayout{

    private int mScreenWidth;
    private int mScreenHeight;

    public final ImageView randomize;
    public final ImageView drop;
    public final ImageView endGame;

    public PanelView(Context context){
        this(context, null);
    }
    public PanelView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        int margin = getResources().getDimensionPixelSize(R.dimen.margine_top);
        int padding = getResources().getDimensionPixelSize(R.dimen.board_padding);
        mScreenWidth = Utils.screenWidth() - padding*2 - Utils.px(20);
        mScreenHeight = Utils.screenHeight() - padding*2 - margin;

        int signMainTopMargin = getResources().getDimensionPixelSize(R.dimen.sign_main_top_margin);
        int signMainLeftMargin = getResources().getDimensionPixelSize(R.dimen.sign_main_left_margin);
        int signMainHorizontalMargin = getResources().getDimensionPixelSize(R.dimen.sign_main_horizontal_margin);
        int signMainRightMargin = getResources().getDimensionPixelSize(R.dimen.sign_main_right_margin);
        int signSettingsRightMargin = getResources().getDimensionPixelSize(R.dimen.sign_settings_right_margin);
        int signSettingsBottomMargin = getResources().getDimensionPixelSize(R.dimen.sign_settings_bottom_margin);
        int nSignMain = 2;
        int signMainWidth = (mScreenWidth - (signMainLeftMargin + ((nSignMain-1)*signMainHorizontalMargin)
                + signMainRightMargin))/nSignMain;
        int signMainHeight = (signMainWidth * 194)/558;
        int signSettingsWidth = getResources().getDimensionPixelSize(R.dimen.sign_settings_width);
        int signSettingsHeight = getResources().getDimensionPixelSize(R.dimen.sign_settings_height);

        setGravity(Gravity.CENTER_HORIZONTAL);

        randomize = new ImageView(context);
        drop = new ImageView(context);
        endGame = new ImageView(context);

        int id = 9000;

        randomize.setId(id+1);
        drop.setId(id+2);
        endGame.setId(id+3);

        RelativeLayout.LayoutParams randomizeLayoutParams = new RelativeLayout.LayoutParams(signMainWidth, signMainHeight);
        RelativeLayout.LayoutParams dropLayoutParams = new RelativeLayout.LayoutParams(signMainWidth, signMainHeight);
        RelativeLayout.LayoutParams endGameLayoutParams = new RelativeLayout.LayoutParams(signSettingsWidth, signSettingsHeight);

        randomizeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        randomizeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        randomizeLayoutParams.leftMargin = signMainLeftMargin;
        randomizeLayoutParams.topMargin = signMainTopMargin;
        dropLayoutParams.addRule(RelativeLayout.RIGHT_OF, randomize.getId());
        dropLayoutParams.addRule(RelativeLayout.ALIGN_TOP, randomize.getId());
        dropLayoutParams.leftMargin = signMainHorizontalMargin;
        endGameLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        endGameLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        endGameLayoutParams.rightMargin = signSettingsRightMargin;
        endGameLayoutParams.bottomMargin = signSettingsBottomMargin;


        //TODO: give these images, and consider changing text font
        Resources res = getResources();
        randomize.setImageDrawable(res.getDrawable(R.drawable.btn_randomize_sign));
        drop.setImageDrawable(res.getDrawable(R.drawable.btn_drop_sign));
        endGame.setImageDrawable(res.getDrawable(R.drawable.btn_give_up_sign));

        randomize.setLayoutParams(randomizeLayoutParams);
        drop.setLayoutParams(dropLayoutParams);
        endGame.setLayoutParams(endGameLayoutParams);

        randomize.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared.eventBus.notify(new RandomizeEvent());
            }
        });
        drop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared.eventBus.notify(new LettersResetEvent());
            }
        });
        endGame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared.eventBus.notify(new GameLoseEvent());
            }
        });

        addView(randomize);
        addView(drop);
        addView(endGame);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public static PanelView fromXml(Context context, ViewGroup parent){
        return (PanelView) LayoutInflater.from(context).inflate(R.layout.panel_view, parent, false);
    }




}
