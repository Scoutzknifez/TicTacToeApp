package com.scoutzknifez.tictactoe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.scoutzknifez.tictactoe.R;
import com.scoutzknifez.tictactoe.fragments.interfaces.Refreshable;
import com.scoutzknifez.tictactoe.gamelogic.dtos.GameState;
import com.scoutzknifez.tictactoe.utility.Globals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TicTacToeBoard extends Fragment implements Refreshable {
    @BindView(R.id.turn_tv)
    TextView turnTextView;

    @BindView(R.id.reset_button)
    RelativeLayout resetButton;

    @BindView(R.id.slot_0)
    TextView slot0;

    @BindView(R.id.slot_1)
    TextView slot1;

    @BindView(R.id.slot_2)
    TextView slot2;

    @BindView(R.id.slot_3)
    TextView slot3;

    @BindView(R.id.slot_4)
    TextView slot4;

    @BindView(R.id.slot_5)
    TextView slot5;

    @BindView(R.id.slot_6)
    TextView slot6;

    @BindView(R.id.slot_7)
    TextView slot7;

    @BindView(R.id.slot_8)
    TextView slot8;


    private List<TextView> slotTextViews = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View created = inflater.inflate(R.layout.fragment_tictactoe_board, container, false);

        ButterKnife.bind(this, created);

        refreshScreen();

        slotTextViews.addAll(Arrays.asList(slot0, slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8));

        // Add click listeners
        for (int i = 0; i < slotTextViews.size(); i++) {
            final int index = i;
            slotTextViews.get(i).setOnClickListener(slot -> {
                if (!Globals.isMyTurn){
                    Toast.makeText(getContext(), "It is not your turn!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Globals.gameState.getBoard().slotIsEmpty(index)) {
                    Toast.makeText(this.getContext(), "This slot is not empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Globals.isX)
                    Globals.gameState.getBoard().setSlotToCross(index);
                else
                    Globals.gameState.getBoard().setSlotToCircle(index);

                if (Globals.gameState.getBoard().didWin())
                    Toast.makeText(getContext(), "GAME OVER!!! " + (Globals.isX ? "X" : "O") + " WON THE GAME", Toast.LENGTH_LONG).show();

                Globals.clientConnection.sendOutput(new GameState(Globals.isX == Globals.isMyTurn, Globals.gameState.getBoard()));

                Globals.isMyTurn = false;
                refreshScreen();
            });
        }

        resetButton.setOnClickListener(view -> {
            Globals.gameState.getBoard().resetBoard();
            refreshScreen();
        });

        return created;
    }

    private void refreshScreen() {
        String textPlaceholder = "It is " + (Globals.isMyTurn ? "your" : "their") + " turn.";
        turnTextView.setText(textPlaceholder);

        for (int i = 0; i < slotTextViews.size(); i++) {
            slotTextViews.get(i).setText("" + Globals.gameState.getBoard().getSlots()[i].getCharacter());
        }
    }

    @Override
    public void refresh() {
        getActivity().runOnUiThread(() -> {
            refreshScreen();
        });
    }
}