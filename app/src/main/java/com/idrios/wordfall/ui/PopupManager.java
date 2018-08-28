package com.idrios.wordfall.ui;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.idrios.wordfall.R;
import com.idrios.wordfall.common.Shared;

/**
 * Created by idrios on 8/23/18.
 */

public class PopupManager {

    public static void showPopupSettings(){
        RelativeLayout popupContainer = (RelativeLayout) Shared.activity.findViewById(R.id.popup_container);
        popupContainer.removeAllViews();

        // background
        ImageView background = new ImageView(Shared.context);
        background.setBackgroundColor(Color.parseColor("#33883366"));
        background.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        background.setClickable(true);
        popupContainer.addView(background);
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopup();
            }
        });

        // popup
        PopupSettingsView popupSettingsView = new PopupSettingsView(Shared.context);
        int width = Shared.context.getResources().getDimensionPixelSize(R.dimen.popup_settings_width);
        int height = Shared.context.getResources().getDimensionPixelSize(R.dimen.popup_settings_height);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        popupSettingsView.setBackgroundDrawable(Shared.context.getResources().getDrawable(R.drawable.submenu_background));
        popupSettingsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do nothing. ... adding this was the only way I could figure out to stop closing the popup
            }
        });
        popupContainer.addView(popupSettingsView, params);

        // animate
        // Don't actually do any animation, I don't want to look too smart.
    }

    public static void closePopup(){
        final RelativeLayout popupContainer = (RelativeLayout) Shared.activity.findViewById(R.id.popup_container);
        int childCount = popupContainer.getChildCount();
        if(childCount > 0){
            View background = null;
            View popup = null;
            if(childCount == 1){
                popup = popupContainer.getChildAt(0);
            }
            else{
                background = popupContainer.getChildAt(0);
                popup = popupContainer.getChildAt(1);
            }
            // animate
            // don't actually animate the closing, we're too cool for that.
            popupContainer.removeAllViews();
        }
    }

}
