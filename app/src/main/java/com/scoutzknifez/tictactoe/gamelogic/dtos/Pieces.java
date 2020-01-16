package com.scoutzknifez.tictactoe.gamelogic.dtos;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Pieces implements Serializable {
    BLANK(' '),
    CROSS('X'),
    CIRCLE('O');

    private char character;

    public boolean isEqualTo(Pieces piece) {
        return this == piece && piece != BLANK;
    }
}
