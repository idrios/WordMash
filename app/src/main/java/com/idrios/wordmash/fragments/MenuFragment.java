package com.idrios.wordmash.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.idrios.wordmash.R;
import com.idrios.wordmash.common.Music;
import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.events.engine.GameStartEvent;


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
                Music.toggleMusic();
                String msg = "on";
                if(Music.MUSIC_OFF){
                    msg = "off";
                }
                Toast.makeText(Shared.context, "Music is now " + msg, Toast.LENGTH_SHORT).show();
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
