package com.idrios.wordmash.assets;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.idrios.wordmash.R;

/**
 * Created by idrios on 2/17/18.
 * The TileView is the frame that the letters move around from. This never moves.
 *
 */

public class TileView extends FrameLayout {

    public static final String TAG = "TileView";
    private ImageView mTileImage;

    public TileView(Context context){
        this(context, null);
    }

    public TileView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    public static TileView fromXml(Context context, ViewGroup parent){
        return (TileView) LayoutInflater.from(context).inflate(R.layout.tile_view, parent, false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTileImage = (ImageView)findViewById(R.id.image);
    }

    public void setTileImage(Bitmap bitmap){
        mTileImage.setImageBitmap(bitmap);
    }

    ////////// Code for flashy animations here ///////////
    //TODO if you want animations on the TileView, put them here.


}
