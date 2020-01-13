package com.scoutzknifez.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.scoutzknifez.tictactoe.api.API;
import com.scoutzknifez.tictactoe.fragments.TicTacToeBoard;
import com.scoutzknifez.tictactoe.structures.api.IPAddress;
import com.scoutzknifez.tictactoe.structures.api.LobbyPacket;
import com.scoutzknifez.tictactoe.structures.api.Player;
import com.scoutzknifez.tictactoe.utility.Constants;
import com.scoutzknifez.tictactoe.utility.Globals;
import com.scoutzknifez.tictactoe.utility.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transitionToFragment(new TicTacToeBoard(), Constants.TICTACTOE_BOARD_TAG);
        Utils.test(API.class).getLogin("ConnorCon").enqueue(new Callback<LobbyPacket>() {
            @Override
            public void onResponse(Call<LobbyPacket> call, Response<LobbyPacket> response) {
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<LobbyPacket> call, Throwable t) {
                System.out.println("------------------------------->Big fail");
                t.printStackTrace();
            }
        });

        setContentView(R.layout.activity_main);
    }

    public void transitionToFragment(Fragment fragment, String tag) {
        Globals.fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Globals.fragmentTransaction.add(R.id.main_container, fragment, tag);
        Globals.fragmentTransaction.commit();
    }
}
