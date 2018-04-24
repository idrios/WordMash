package com.idrios.wordmash.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.idrios.wordmash.R;
import com.idrios.wordmash.assets.BankView;
import com.idrios.wordmash.assets.BoardView;
import com.idrios.wordmash.assets.TileView;
import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.model.Game;

/** The fragment loads all the viewable objects, but is not responsible for the programming behind
 * them (i.e. the bank and board are loaded from XML but the content in the bank and board are
 * determined in the engine, and stored in the 'Game' rather than here or in bank or board).
 *
 */


public class GameFragment extends BaseFragment {

    private BoardView mBoardView;
    private BankView mBankView;

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

        //Make board
        mBoardView = BoardView.fromXml(getActivity().getApplicationContext(), view);
        FrameLayout boardContainer = (FrameLayout) view.findViewById(R.id.game_container);
        boardContainer.addView(mBoardView);
        makeBoard();

        //Make bank
        mBankView = BankView.fromXml(getActivity().getApplicationContext(), view);
        FrameLayout bankContainer = (FrameLayout) view.findViewById(R.id.bank_container);
        bankContainer.addView(mBankView);

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
