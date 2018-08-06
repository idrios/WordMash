package com.idrios.wordmash.engine;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.idrios.wordmash.R;
import com.idrios.wordmash.common.Music;
import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.fragments.GameFragment;
import com.idrios.wordmash.fragments.MenuFragment;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import static com.idrios.wordmash.engine.ScreenController.Screen.MENU;

/**        for(Screen sc : openedScreens){
            if(sc == screen){
                openedScreens.remove(sc);
            }
        }
        mFragmentManager = Shared.activity.getSupportFragmentManager();
        Fragment fragment = getFragment(screen);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
        openedScreens.add(screen);
 * Decides which screen user is on -- whether it's the starting screen, a menu screen, or a game screen.
 */

public class ScreenController {

    //TODO figure out why isn't mFragmentManager static?
    private static ScreenController mInstance = null;
    private static List<Screen> openedScreens = new ArrayList<Screen>();
    private FragmentManager mFragmentManager;

    private ScreenController(){
    }

    public static ScreenController getInstance(){
        if (mInstance == null) {
            mInstance = new ScreenController();
        }
        return mInstance;
    }

    public enum Screen {
        MENU,
        GAME,
    }

    public void openScreen(Screen screen){
        //TODO check for special cases (e.g. if opening a screen creates 2 screens of a game, remove one of the game screens)
        for(Screen sc : openedScreens){
            if(sc == screen){
                openedScreens.remove(sc);
            }
        }
        mFragmentManager = Shared.activity.getSupportFragmentManager();
        Fragment fragment = getFragment(screen);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
        Music.playTheme(screen);
        openedScreens.add(screen);
    }

    public boolean onBack(){
        //TODO return to the previous screen and remove the now returned-from screen.
        if(openedScreens.size() > 0){
            Screen screenToRemove = openedScreens.get(openedScreens.size() - 1);
            openedScreens.remove(openedScreens.size() - 1);
            if(openedScreens.size() == 0){
                return true;
            }
            Screen screen = openedScreens.get(openedScreens.size() - 1);
            openedScreens.remove(openedScreens.size() - 1);
            openScreen(screen);
            return false;
        }
        return true;
    }

    private Fragment getFragment(Screen screen) {
        switch (screen) {
            case MENU:
                return new MenuFragment();
            case GAME:
                return new GameFragment();
            default:
                break;
        }
        return null;
    }
}
