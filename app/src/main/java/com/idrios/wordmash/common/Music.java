package com.idrios.wordmash.common;

import android.media.MediaCodec;
import android.media.MediaPlayer;

import com.idrios.wordmash.R;
import com.idrios.wordmash.engine.ScreenController;

/**
 * Created by idrios on 8/6/18.
 */

public class Music {

    public static boolean MUSIC_OFF = false;
    public static boolean SOUND_OFF = false;
    public static MediaPlayer MUSIC;
    public static Theme theme;
    public static int themeResource;
    public static int musicTime = 0;

    public enum Theme{
        MAIN,
        GAME
    }

    public static void playTheme(ScreenController.Screen screen){
        if (!MUSIC_OFF) {
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
        MUSIC.pause();
    }
    public static void pauseLong(){
        MUSIC.pause();
        musicTime = MUSIC.getCurrentPosition();
        MUSIC.reset();
        MUSIC.release();
        MUSIC = null;
    }
    public static void stop(){
        MUSIC.stop();
        MUSIC.reset();
        MUSIC.release();
    }
    public static void resumeShort(){
        MUSIC.start();
    }
    public static void resumeLong(){
        if(MUSIC == null) {
            MUSIC = MediaPlayer.create(Shared.context, themeResource);
            MUSIC.seekTo(musicTime);
            MUSIC.start();
        }
    }

    //Sometimes doesn't work
    public static void playGameLose(){
        if (!SOUND_OFF) {
            MUSIC.pause();
            MediaPlayer mp = MediaPlayer.create(Shared.context, R.raw.lose);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                    mp = null;
                    MUSIC.start();
                }
            });
            mp.start();
        }
    }

    //Sometimes doesn't work
    public static void playGameWin(){
        if (!SOUND_OFF) {
            MUSIC.pause();
            MediaPlayer mp = MediaPlayer.create(Shared.context, R.raw.win);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                    mp = null;
                    MUSIC.start();
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
