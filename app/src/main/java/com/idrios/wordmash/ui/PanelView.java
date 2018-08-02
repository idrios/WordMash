package com.idrios.wordmash.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.idrios.wordmash.R;
import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.events.engine.EndGameEvent;
import com.idrios.wordmash.events.engine.RandomizeEvent;
import com.idrios.wordmash.events.engine.ResetTilesEvent;

/**
 * Created by idrios on 6/24/18.
 */

public class PanelView extends LinearLayout{

    public Button endGame;
    public Button randomize;
    public Button reset;

    public PanelView(Context context){
        this(context, null);
    }
    public PanelView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setOrientation(LinearLayout.HORIZONTAL);

        endGame = new Button(context);
        randomize = new Button(context);
        reset = new Button(context);

        //TODO: give these images, and consider changing text font
        endGame.setText("End Game");
        randomize.setText("Randomize");
        reset.setText("Reset");

        endGame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared.eventBus.notify(new EndGameEvent());
            }
        });
        randomize.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared.eventBus.notify(new RandomizeEvent());
            }
        });
        reset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared.eventBus.notify(new ResetTilesEvent());
            }
        });

        addView(endGame);
        addView(randomize);
        addView(reset);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public static PanelView fromXml(Context context, ViewGroup parent){
        return (PanelView) LayoutInflater.from(context).inflate(R.layout.panel_view, parent, false);
    }




}
