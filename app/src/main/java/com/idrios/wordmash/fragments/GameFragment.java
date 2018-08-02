package com.idrios.wordmash.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.idrios.wordmash.R;
import com.idrios.wordmash.assets.BankView;
import com.idrios.wordmash.assets.BoardView;
import com.idrios.wordmash.common.Shared;
import com.idrios.wordmash.events.Event;
import com.idrios.wordmash.events.engine.EndGameEvent;
import com.idrios.wordmash.events.engine.RandomizeEvent;
import com.idrios.wordmash.events.engine.ResetTilesEvent;
import com.idrios.wordmash.events.engine.WordFoundEvent;
import com.idrios.wordmash.model.Game;
import com.idrios.wordmash.ui.PanelView;

import java.util.HashMap;

/** The fragment loads all the viewable objects, but is not responsible for the programming behind
 * them (i.e. the bank and board are loaded from XML but the content in the bank and board are
 * determined in the engine, and stored in the 'Game' rather than here or in bank or board).
 *
 */


public class GameFragment extends BaseFragment {

    private BoardView mBoardView;
    private BankView mBankView;
    private PanelView mPanelView;

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


        //Make bank
        mBankView = BankView.fromXml(getActivity().getApplicationContext(), view);
        FrameLayout bankContainer = (FrameLayout) view.findViewById(R.id.bank_container);
        bankContainer.addView(mBankView);

        //Make board
        mBoardView = BoardView.fromXml(getActivity().getApplicationContext(), view);
        FrameLayout boardContainer = (FrameLayout) view.findViewById(R.id.game_container);
        boardContainer.addView(mBoardView);

        //Make panel
        mPanelView = PanelView.fromXml(getActivity().getApplicationContext(), view);
        FrameLayout panelContainer = (FrameLayout) view.findViewById(R.id.panel_container);
        panelContainer.addView(mPanelView);

        makeGame();
        Shared.eventBus.listen(WordFoundEvent.TYPE, this);
        Shared.eventBus.listen(RandomizeEvent.TYPE, this);
        Shared.eventBus.listen(ResetTilesEvent.TYPE, this);


        //TODO get an image background
        return view;
    }

    @Override
    public void onDestroy(){
        Shared.eventBus.unlisten(WordFoundEvent.TYPE, this);
        Shared.eventBus.unlisten(RandomizeEvent.TYPE, this);
        Shared.eventBus.unlisten(ResetTilesEvent.TYPE, this);
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void makeGame(){
        Game game = Shared.engine.getActiveGame();
        mBoardView.setBoard(game);
        mBankView.setBank(game);
    }

    @Override
    public void onEvent(WordFoundEvent e){
        mBankView.wordFound(e.word);
    }

    @Override
    public void onEvent(RandomizeEvent e){
        mBoardView.randomizeLetters();
    }

    @Override
    public void onEvent(ResetTilesEvent e){
        mBoardView.resetLetters();
    }

    @Override
    public void onEvent(EndGameEvent e){
        mBankView.showAll();
    }

}
