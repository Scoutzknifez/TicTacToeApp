package com.scoutzknifez.tictactoe.utility;

import androidx.fragment.app.FragmentTransaction;

import com.scoutzknifez.tictactoe.fragments.interfaces.Refreshable;
import com.scoutzknifez.tictactoe.gamelogic.ClientConnection;
import com.scoutzknifez.tictactoe.gamelogic.dtos.GameState;

public class Globals {
    // Game variables
    public static ClientConnection clientConnection = null;
    public static boolean inGame = true; // TODO TEMP Flip to false
    public static boolean isX;
    public static boolean isMyTurn;
    public static GameState gameState;

    // Android variables
    public static FragmentTransaction fragmentTransaction;
    public static Refreshable refreshable;
}
