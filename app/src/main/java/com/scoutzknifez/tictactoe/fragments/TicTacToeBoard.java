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
import com.scoutzknifez.tictactoe.structures.TTTBoard;

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
public class TicTacToeBoard extends Fragment {
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
    private TTTBoard board = new TTTBoard();
    private boolean isXTurn = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View created = inflater.inflate(R.layout.fragment_tictactoe_board, container, false);

        ButterKnife.bind(this, created);

        slotTextViews.addAll(Arrays.asList(slot0, slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8));

        // Add click listeners
        for (int i = 0; i < slotTextViews.size(); i++) {
            final int index = i;
            slotTextViews.get(i).setOnClickListener(slot -> {
                if (!board.slotIsEmpty(index)) {
                    Toast.makeText(this.getContext(), "This slot is not empty!", Toast.LENGTH_SHORT).show();
                    System.out.println(board);
                    return;
                }

                if (isXTurn())
                    board.setSlotToCross(index);
                else
                    board.setSlotToCircle(index);

                if (board.didWin(index))
                    Toast.makeText(getContext(), "GAME OVER!!! " + (isXTurn() ? "X" : "O") + " WON THE GAME", Toast.LENGTH_LONG).show();

                setXTurn(!isXTurn());
                refreshScreen();
            });
        }

        resetButton.setOnClickListener(view -> {
            board.resetBoard();
            refreshScreen();
        });

        return created;
    }

    private void refreshScreen() {
        for (int i = 0; i < slotTextViews.size(); i++) {
            slotTextViews.get(i).setText("" + board.getSlots()[i].getCharacter());
        }
    }
}