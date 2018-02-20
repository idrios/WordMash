package com.idrios.wordmash.utils;

import com.idrios.wordmash.common.Shared;

/**
 * Created by idrios on 2/17/18.
 */

public class Utils {

    public static int px(int dp){
        return (int) (Shared.context.getResources().getDisplayMetrics().density * dp);
    }

    public static int screenWidth() {
        return Shared.context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int screenHeight(){
        return Shared.context.getResources().getDisplayMetrics().heightPixels;
    }

}
