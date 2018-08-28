package com.idrios.wordfall.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.idrios.wordfall.R;
import com.idrios.wordfall.assets.BankView;
import com.idrios.wordfall.assets.BoardView;
import com.idrios.wordfall.assets.PanelNewGameView;
import com.idrios.wordfall.common.Shared;
import com.idrios.wordfall.events.engine.GameEndEvent;
import com.idrios.wordfall.events.engine.RandomizeEvent;
import com.idrios.wordfall.events.engine.LettersResetEvent;
import com.idrios.wordfall.events.engine.WordDiscoverEvent;
import com.idrios.wordfall.events.engine.WordRootDiscoverEvent;
import com.idrios.wordfall.model.Game;
import com.idrios.wordfall.assets.PanelOptionsView;

/** The fragment loads all the viewable objects, but is not responsible for the programming behind
 * them (i.e. the bank and board are loaded from XML but the content in the bank and board are
 * determined in the engine, and stored in the 'Game' rather than here or in bank or board).
 *
 */


public class GameFragment extends BaseFragment {

    private BoardView mBoardView;
    private BankView mBankView;
    private PanelOptionsView mPanelOptionsView;
    private PanelNewGameView mPanelNewgameView;
    private FrameLayout mBankContainer;
    private FrameLayout mBoardContainer;
    private FrameLayout mPanelContainer;

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
        mBankContainer = (FrameLayout) view.findViewById(R.id.bank_container);
        mBankContainer.addView(mBankView);

        //Make board
        mBoardView = BoardView.fromXml(getActivity().getApplicationContext(), view);
        mBoardContainer = (FrameLayout) view.findViewById(R.id.game_container);
        mBoardContainer.addView(mBoardView);

        //Make panel
        mPanelOptionsView = (PanelOptionsView)PanelOptionsView.fromXml(getActivity().getApplicationContext(), view);
        mPanelContainer = (FrameLayout) view.findViewById(R.id.panel_container);
        mPanelContainer.addView(mPanelOptionsView);

        //Prepare end-game panel
        mPanelNewgameView = PanelNewGameView.fromXml(getActivity().getApplicationContext(), view);


        makeGame();
        Shared.eventBus.listen(GameEndEvent.TYPE, this);
        Shared.eventBus.listen(WordDiscoverEvent.TYPE, this);
        Shared.eventBus.listen(WordRootDiscoverEvent.TYPE, this);
        Shared.eventBus.listen(RandomizeEvent.TYPE, this);
        Shared.eventBus.listen(LettersResetEvent.TYPE, this);


        //TODO get an image background
        return view;
    }

    @Override
    public void onDestroy(){
        Shared.eventBus.unlisten(GameEndEvent.TYPE, this);
        Shared.eventBus.unlisten(WordDiscoverEvent.TYPE, this);
        Shared.eventBus.unlisten(WordRootDiscoverEvent.TYPE, this);
        Shared.eventBus.unlisten(RandomizeEvent.TYPE, this);
        Shared.eventBus.unlisten(LettersResetEvent.TYPE, this);
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
    public void onEvent(WordDiscoverEvent e){
        mBankView.wordFound(e.word);
    }

    @Override
    public void onEvent(WordRootDiscoverEvent e){
        mBankView.wordFound(e.word);
    }

    @Override
    public void onEvent(RandomizeEvent e){
        mBoardView.randomizeLetters();
    }

    @Override
    public void onEvent(LettersResetEvent e){
        mBoardView.resetLetters();
    }

    @Override
    public void onEvent(GameEndEvent e){
        mBankView.showAll();
        mPanelContainer.removeAllViews();
        mPanelContainer.addView(mPanelNewgameView);
    }

}
