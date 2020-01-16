package com.scoutzknifez.tictactoe.gamelogic.dtos;

import java.io.Serializable;

import lombok.Data;

@Data
public class GameState implements Serializable{
    private boolean isXTurn = true;
    private TTTBoard board = new TTTBoard();
}
