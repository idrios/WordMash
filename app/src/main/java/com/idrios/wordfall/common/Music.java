package com.idrios.wordfall.common;

import android.media.MediaPlayer;

import com.idrios.wordfall.R;
import com.idrios.wordfall.engine.ScreenController;

/**
 * Created by idrios on 8/6/18.
 */

public class Music {

    public static boolean MUSIC_OFF = true;
    public static boolean SOUND_OFF = false;
    public static MediaPlayer MUSIC;
    public static Theme theme;
    public static int themeResource;
    public static int musicTime = 0;

    public enum Theme{
        MAIN,
        GAME
    }

    public static void toggleMusic(){
        if(MUSIC_OFF){
            setMusicOn();
        }
        else{
            setMusicOff();
        }
    }
    public static void setMusicOn(){
        MUSIC_OFF = false;
        if(MUSIC!=null) {
            stop();
        }
        if(themeResource!=0){
            MUSIC = MediaPlayer.create(Shared.context, themeResource);
            MUSIC.setLooping(true);
            MUSIC.start();
        }
    }
    public static void setMusicOff(){
        stop();
        MUSIC_OFF = true;
    }

    public static void toggleSound(){
        if(SOUND_OFF){
            setSoundOn();
        }
        else{
            setSoundOff();
        }
    }
    public static void setSoundOn(){
        SOUND_OFF = false;
    }
    public static void setSoundOff(){
        SOUND_OFF = true;
    }

    public static void playTheme(ScreenController.Screen screen){

        Theme newTheme;
        switch (screen){
            case MENU:
                newTheme = Theme.MAIN;
                themeResource = R.raw.main_theme;
                break;
            case GAME:
                newTheme = Theme.GAME;
                themeResource = R.raw.game_theme;
                break;
            default:
                return;
        }
        if (!MUSIC_OFF) {
            if((MUSIC!=null) && theme != newTheme){
                stop();
            }
            theme = newTheme;
            MUSIC = MediaPlayer.create(Shared.context, themeResource);
            MUSIC.setLooping(true);
            MUSIC.start();
        }
    }

    public static void pauseShort(){
        if(!MUSIC_OFF) {
            MUSIC.pause();
        }
    }
    public static void pauseLong(){
        if(!MUSIC_OFF) {
            MUSIC.pause();
            musicTime = MUSIC.getCurrentPosition();
            MUSIC.reset();
            MUSIC.release();
            MUSIC = null;
        }
    }
    public static void stop(){
        if(MUSIC == null){
            return;
        }
        MUSIC.stop();
        MUSIC.reset();
        MUSIC.release();
        MUSIC = null;
    }
    public static void resumeShort(){
        if(!MUSIC_OFF) {
            MUSIC.start();
        }
    }
    public static void resumeLong(){
        if (!MUSIC_OFF) {
            if (MUSIC == null) {
                MUSIC = MediaPlayer.create(Shared.context, themeResource);
                MUSIC.seekTo(musicTime);
                MUSIC.start();
            }
        }
    }

    //Sometimes doesn't work
    public static void playGameLose(){
        if (!SOUND_OFF) {
            if(!MUSIC_OFF) {
                MUSIC.pause();
            }
            MediaPlayer mp = MediaPlayer.create(Shared.context, R.raw.lose);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                    mp = null;
                    if(!MUSIC_OFF) {
                        MUSIC.start();
                    }
                }
            });
            mp.start();
        }
    }

    //Sometimes doesn't work
    public static void playGameWin(){
        if (!SOUND_OFF) {
            if(!MUSIC_OFF) {
                MUSIC.pause();
            }
            MediaPlayer mp = MediaPlayer.create(Shared.context, R.raw.win);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                    mp = null;
                    if(!MUSIC_OFF) {
                        MUSIC.start();
                    }
                }
            });
            mp.start();
        }
    }
    public static void playWordDiscover(){
        if (!SOUND_OFF) {
            MediaPlayer mp = MediaPlayer.create(Shared.context, R.raw.beep);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                    mp = null;
                }
            });
            mp.start();
        }

    }
    public static void playWordRootDiscover(){
        if (!SOUND_OFF) {
            MediaPlayer mp = MediaPlayer.create(Shared.context, R.raw.bing);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                    mp = null;
                }
            });
            mp.start();
        }

    }
    public static void playLettersReset(){
        if (!SOUND_OFF) {
            MediaPlayer mp = MediaPlayer.create(Shared.context, R.raw.drop);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                    mp = null;
                }
            });
            mp.start();
        }

    }
    public static void playLetterTap(){
        if (!SOUND_OFF) {
            MediaPlayer mp = MediaPlayer.create(Shared.context, R.raw.click);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                    mp = null;
                }
            });
            mp.start();
        }
    }
    public static void playLettersRandomize(){
        if (!SOUND_OFF) {
            MediaPlayer mp = MediaPlayer.create(Shared.context, R.raw.randomize);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                    mp = null;
                }
            });
            mp.start();
        }

    }
}