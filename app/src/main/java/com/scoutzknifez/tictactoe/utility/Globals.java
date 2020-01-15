package com.scoutzknifez.tictactoe.utility;

import androidx.fragment.app.FragmentTransaction;

import com.scoutzknifez.tictactoe.gamelogic.ClientConnection;

public class Globals {
    // Game variables
    public static ClientConnection clientConnection = null;
    public static boolean inGame = true; // TODO TEMP Flip to false

    // Android variables
    public static FragmentTransaction fragmentTransaction;
}
