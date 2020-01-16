package com.scoutzknifez.tictactoe.gamelogic.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Pieces {
    BLANK(' '),
    CROSS('X'),
    CIRCLE('O');

    private char character;

    public boolean isEqualTo(Pieces piece) {
        return this == piece && piece != BLANK;
    }
}
