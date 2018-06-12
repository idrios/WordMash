package com.idrios.wordmash.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

    public static Bitmap scaleDown(int resource, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(Shared.context.getResources(), resource);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(Shared.context.getResources(), resource, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        // raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth){
            // Calculate ratios of height and width to requested height/width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize to guarantee a final image
            // with both dimensions larger than or equal to the requested height/width
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static String mkStringFromCharArray(char[] array){
        if (array == null){
            return null;
        }
        int len = array.length;
        String s = "";
        for(int i = 0; i<len; i++){
            s = s + array[i];
        }
        return s;
    }


}
