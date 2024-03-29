package com.idrios.wordfall.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.idrios.wordfall.R;
import com.idrios.wordfall.common.Music;
import com.idrios.wordfall.common.Shared;
import com.idrios.wordfall.events.engine.GameStartEvent;
import com.idrios.wordfall.ui.PopupManager;


public class MenuFragment extends Fragment {

    private ImageView mStart;
    private ImageView mSettings;

    public MenuFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        mStart = (ImageView)view.findViewById(R.id.start);
        mSettings = (ImageView)view.findViewById(R.id.btn_settings);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared.eventBus.notify(new GameStartEvent());
            }
        });
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupManager.showPopupSettings();
//                Music.toggleMusic();
//                Music.toggleSound();
//                String msg = "on";
//                if(Music.MUSIC_OFF){
//                    msg = "off";
//                }
//                Toast.makeText(Shared.context, "Music is now " + msg, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
