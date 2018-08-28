package com.idrios.wordfall.assets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by idrios on 8/29/18.
 */

public abstract class PanelView extends RelativeLayout {
    public PanelView(Context context){
        super(context);
    }
    public PanelView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }
}
