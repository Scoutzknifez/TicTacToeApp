package com.scoutzknifez.tictactoe.gamelogic.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Value<T> implements Serializable {
    private T value;
}
