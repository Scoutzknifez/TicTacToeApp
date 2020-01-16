package com.scoutzknifez.tictactoe.gamelogic.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GameState implements Serializable {
    private static final long serialVersionUID = 4701108655532099L;

    private boolean isXTurn;
    private TTTBoard board;
}
