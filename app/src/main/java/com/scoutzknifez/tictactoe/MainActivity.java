package com.scoutzknifez.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import com.scoutzknifez.tictactoe.fragments.TicTacToeBoard;
import com.scoutzknifez.tictactoe.gamelogic.ClientConnection;
import com.scoutzknifez.tictactoe.utility.Constants;
import com.scoutzknifez.tictactoe.utility.Globals;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transitionToFragment(new TicTacToeBoard(), Constants.TICTACTOE_BOARD_TAG);

        Globals.clientConnection = new ClientConnection("10.0.2.2");
        Globals.clientConnection.start();

        /*Utils.test(API.class).getLogin(Utils.createJSON("username", "Connorcon")).enqueue(new Callback<LobbyPacket>() {
            @Override
            public void onResponse(Call<LobbyPacket> call, Response<LobbyPacket> response) {
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<LobbyPacket> call, Throwable t) {
                System.out.println("------------------------------->Big fail");
                t.printStackTrace();
            }
        });*/

        setContentView(R.layout.activity_main);
    }

    public void transitionToFragment(Fragment fragment, String tag) {
        Globals.fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Globals.fragmentTransaction.add(R.id.main_container, fragment, tag);
        Globals.fragmentTransaction.commit();
    }
}
