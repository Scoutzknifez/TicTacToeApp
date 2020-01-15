package com.scoutzknifez.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import com.scoutzknifez.tictactoe.fragments.TicTacToeBoard;
import com.scoutzknifez.tictactoe.gamelogic.dtos.Sample;
import com.scoutzknifez.tictactoe.utility.Constants;
import com.scoutzknifez.tictactoe.utility.Globals;
import com.scoutzknifez.tictactoe.utility.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transitionToFragment(new TicTacToeBoard(), Constants.TICTACTOE_BOARD_TAG);

        new Thread(this::connectToServer).start();

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

    private void connectToServer() {
        String ip = "10.0.2.2";
        int port = 5050;
        try {
            Utils.log("Connecting to server " + ip + " on port " + port);
            Socket client = new Socket(ip, port);

            OutputStream output = client.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(output);
            oos.writeObject(new Sample("Connor"));
            //DataOutputStream dos = new DataOutputStream(output);
            //dos.writeUTF("Hey its the client");

            InputStream input = client.getInputStream();
            DataInputStream dis = new DataInputStream(input);
            Utils.log("The server said: " + dis.readUTF());

            client.close();
        } catch (Exception e) {
            Utils.log("Could not connect! %s", e);
        }
    }
}
