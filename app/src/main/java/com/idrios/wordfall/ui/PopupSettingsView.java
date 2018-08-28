package com.idrios.wordfall.ui;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.idrios.wordfall.R;
import com.idrios.wordfall.common.Music;
import com.idrios.wordfall.common.Shared;

/**
 * Created by idrios on 8/23/18.
 */

public class PopupSettingsView extends LinearLayout {

    private ImageView btnMusic;
    private ImageView btnSound;

    public PopupSettingsView(Context context){
        this(context, null);
    }
    public PopupSettingsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_popup_settings, this, true);
        btnMusic = (ImageView) findViewById(R.id.btnMusic);
        btnSound = (ImageView) findViewById(R.id.btnSound);
        setMusicButton();
        setSoundButton();
        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music.toggleMusic();
                setMusicButton();
            }
        });
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music.toggleSound();
                setSoundButton();
            }
        });
    }

    private void setMusicButton(){
        Resources res = getResources();
        if(Music.MUSIC_OFF){
            btnMusic.setImageDrawable(res.getDrawable(R.drawable.submenu_toggle_off));
        }
        else{
            btnMusic.setImageDrawable(res.getDrawable(R.drawable.submenu_toggle_on));
        }
    }
    private void setSoundButton(){
        Resources res = getResources();
        if(Music.SOUND_OFF){
            btnSound.setImageDrawable(res.getDrawable(R.drawable.submenu_toggle_off));
        }
        else{
            btnSound.setImageDrawable(res.getDrawable(R.drawable.submenu_toggle_on));
        }
    }

}
