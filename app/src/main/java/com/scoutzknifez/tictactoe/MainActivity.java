package com.scoutzknifez.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.scoutzknifez.tictactoe.fragments.TicTacToeBoard;
import com.scoutzknifez.tictactoe.utility.Constants;
import com.scoutzknifez.tictactoe.utility.Globals;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transitionToFragment(new TicTacToeBoard(), Constants.TICTACTOE_BOARD_TAG);

        setContentView(R.layout.activity_main);
    }

    public void transitionToFragment(Fragment fragment, String tag) {
        Globals.fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Globals.fragmentTransaction.add(R.id.main_container, fragment, tag);
        Globals.fragmentTransaction.commit();
    }
}
