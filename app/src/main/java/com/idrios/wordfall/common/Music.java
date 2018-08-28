package com.idrios.wordfall.common;

import android.media.MediaPlayer;

import com.idrios.wordfall.R;
import com.idrios.wordfall.engine.ScreenController;

/**
 * Created by idrios on 8/6/18.
 */

public class Music {
    
    public static boolean MUSIC_OFF = Memory.loadMusicOff();
    public static boolean SOUND_OFF = Memory.loadSoundOff();
    public static MediaPlayer MUSIC;
    public static Theme theme;
    public static int themeResource;
    public static int musicTime = 0;

    public enum Theme{
        MAIN,
        GAME
    }

    public static void toggleMusic(){
        if(Memory.getMusicOff()){
            setMusicOn();
        }
        else{
            setMusicOff();
        }
    }
    public static void setMusicOn(){
        MUSIC_OFF = false;
        Memory.saveMusicOff(false);
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
        Memory.saveMusicOff(true);
    }

    public static void toggleSound(){
        if(Memory.getSoundOff()){
            setSoundOn();
        }
        else{
            setSoundOff();
        }
    }
    public static void setSoundOn(){
        SOUND_OFF = false;
        Memory.saveSoundOff(false);
    }
    public static void setSoundOff(){
        SOUND_OFF = true;
        Memory.saveSoundOff(true);
    }

    public static void playTheme(ScreenController.Screen screen){

        Theme newTheme;
        String rawResourceName;
        switch (screen){
            case MENU:
                newTheme = Theme.MAIN;
                rawResourceName = String.format("theme_main_%s", Memory.getMusicTheme());
                themeResource = Shared.context.getResources().getIdentifier(rawResourceName, "raw", Shared.context.getPackageName());
                break;
            case GAME:
                newTheme = Theme.GAME;
                rawResourceName = String.format("theme_game_%s", Memory.getMusicTheme());
                themeResource = Shared.context.getResources().getIdentifier(rawResourceName, "raw", Shared.context.getPackageName());
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
            String rawResourceName = String.format("sound_lose_%s", Memory.getSoundTheme());
            int rId = Shared.context.getResources().getIdentifier(rawResourceName, "raw", Shared.context.getPackageName());
            MediaPlayer mp = MediaPlayer.create(Shared.context, rId);
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
            String rawResourceName = String.format("sound_win_%s", Memory.getSoundTheme());
            int rId = Shared.context.getResources().getIdentifier(rawResourceName, "raw", Shared.context.getPackageName());
            MediaPlayer mp = MediaPlayer.create(Shared.context, rId);
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
            String rawResourceName = String.format("sound_worddiscover_%s", Memory.getSoundTheme());
            int rId = Shared.context.getResources().getIdentifier(rawResourceName, "raw", Shared.context.getPackageName());
            MediaPlayer mp = MediaPlayer.create(Shared.context, rId);
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
            String rawResourceName = String.format("sound_wordrootdiscover_%s", Memory.getSoundTheme());
            int rId = Shared.context.getResources().getIdentifier(rawResourceName, "raw", Shared.context.getPackageName());
            MediaPlayer mp = MediaPlayer.create(Shared.context, rId);
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
            String rawResourceName = String.format("sound_drop_%s", Memory.getSoundTheme());
            int rId = Shared.context.getResources().getIdentifier(rawResourceName, "raw", Shared.context.getPackageName());
            MediaPlayer mp = MediaPlayer.create(Shared.context, rId);
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
            String rawResourceName = String.format("sound_lettertap_%s", Memory.getSoundTheme());
            int rId = Shared.context.getResources().getIdentifier(rawResourceName, "raw", Shared.context.getPackageName());
            MediaPlayer mp = MediaPlayer.create(Shared.context, rId);
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
            String rawResourceName = String.format("sound_shuffle_%s", Memory.getSoundTheme());
            int rId = Shared.context.getResources().getIdentifier(rawResourceName, "raw", Shared.context.getPackageName());
            MediaPlayer mp = MediaPlayer.create(Shared.context, rId);
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
