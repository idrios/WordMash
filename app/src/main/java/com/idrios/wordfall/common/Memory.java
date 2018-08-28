package com.idrios.wordfall.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by idrios on 8/14/18.
 */

public class Memory {
    private static final String SHARED_PREFERENCES_NAME = "com.idrios.wordfall";

    private static String keyDifficulty = "difficulty";
    private static String keyMusicTheme = "musicTheme";
    private static String keySoundTheme = "soundTheme";
    private static String keyMusicOff = "musicOff";
    private static String keySoundOff = "soundOff";

    // Settings Preferences
    private static int mDifficulty = 2;
    private static String mMusicTheme = "classical_calm";
    private static String mSoundTheme = "classic";
    private static boolean mMusicOff = false;
    private static boolean mSoundOff = false;

    public static void saveDifficulty(int difficulty){
        mDifficulty = difficulty;
        SharedPreferences sharedPreferences =
                Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(keyDifficulty, difficulty);
        edit.commit();
    }
    public static void saveMusicTheme(String musicTheme){
        mMusicTheme = musicTheme;
        SharedPreferences sharedPreferences =
                Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(keyMusicTheme, musicTheme);
        edit.commit();
    }
    public static void saveSoundTheme(String soundTheme){
        mSoundTheme = soundTheme;
        SharedPreferences sharedPreferences =
                Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(keySoundTheme, soundTheme);
        edit.commit();
    }
    public static void saveMusicOff(boolean musicOff){
        mMusicOff = musicOff;
        SharedPreferences sharedPreferences =
                Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(keyMusicOff, musicOff);
        edit.commit();
    }
    public static void saveSoundOff(boolean soundOff){
        mSoundOff = soundOff;
        SharedPreferences sharedPreferences =
                Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(keySoundOff, soundOff);
        edit.commit();
    }


    public static boolean loadPreferencesAudio(){
        SharedPreferences sharedPreferences =
                Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        mMusicTheme = sharedPreferences.getString(keyMusicTheme, "classical_calm");
        mSoundTheme = sharedPreferences.getString(keySoundTheme, "classic");
        mMusicOff = sharedPreferences.getBoolean(keyMusicOff, false);
        mSoundOff = sharedPreferences.getBoolean(keySoundOff, false);
        return true;
    }

    public static boolean loadMusicOff(){
        SharedPreferences sharedPreferences =
                Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        mMusicOff = sharedPreferences.getBoolean(keyMusicOff, false);
        return mMusicOff;
    }

    public static boolean loadSoundOff(){
        SharedPreferences sharedPreferences =
                Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        mSoundOff = sharedPreferences.getBoolean(keySoundOff, false);
        return mSoundOff;
    }

    public static String loadMusicTheme(){
        SharedPreferences sharedPreferences =
                Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        mMusicTheme = sharedPreferences.getString(keyMusicTheme, "classic");
        return mMusicTheme;
    }

    public static String loadSoundTheme(){
        SharedPreferences sharedPreferences =
                Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        mSoundTheme = sharedPreferences.getString(keySoundTheme, "classic");
        return mSoundTheme;
    }

    public static int getDifficulty(){
        return mDifficulty;
    }
    public static String getMusicTheme(){
        return mMusicTheme;
    }
    public static String getSoundTheme(){
        return mSoundTheme;
    }
    public static boolean getMusicOff(){
        return mMusicOff;
    }
    public static boolean getSoundOff(){
        return mSoundOff;
    }
}
