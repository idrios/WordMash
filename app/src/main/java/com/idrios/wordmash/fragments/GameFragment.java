package com.idrios.wordmash.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.idrios.wordmash.R;
import com.idrios.wordmash.assets.BoardView;
import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.model.Game;


public class GameFragment extends BaseFragment {

    private BoardView mBoardView;

    public GameFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_game, container, false);

        mBoardView = BoardView.fromXml(getActivity().getApplicationContext(), view);
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.game_container);
        frameLayout.addView(mBoardView);

        //make board
        makeBoard();

        //TODO get rid of toast
        Toast.makeText(Shared.context, "Game Loaded", Toast.LENGTH_SHORT).show();

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

    private void makeBoard(){
        Game game = Shared.engine.getActiveGame();
        mBoardView.setBoard(game);

    }

}
