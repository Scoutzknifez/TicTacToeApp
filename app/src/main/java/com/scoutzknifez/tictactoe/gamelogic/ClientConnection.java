package com.scoutzknifez.tictactoe.gamelogic;

import com.scoutzknifez.tictactoe.gamelogic.dtos.Sample;
import com.scoutzknifez.tictactoe.gamelogic.dtos.TTTBoard;
import com.scoutzknifez.tictactoe.gamelogic.dtos.Value;
import com.scoutzknifez.tictactoe.utility.Constants;
import com.scoutzknifez.tictactoe.utility.Globals;
import com.scoutzknifez.tictactoe.utility.Utils;
import com.scoutzknifez.tictactoe.utility.exceptions.ObjectConstructionException;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import lombok.Data;

@Data
public class ClientConnection extends Thread {
    private Socket socket;

    /**
     * These are used for all forms of input and output on this thread.
     * Can be accessed from getters
     */
    private InputStream input;
    private ObjectInputStream ois;
    private OutputStream output;
    private ObjectOutputStream oos;

    public ClientConnection(String ipAddress) {
        try {
            Thread thread = new Thread(() -> {
                try {
                    setSocket(new Socket(ipAddress, Constants.GAME_SERVER_PORT));
                    try {
                        setOutput(getSocket().getOutputStream());
                        setOos(new ObjectOutputStream(getOutput()));
                        setInput(getSocket().getInputStream());
                        setOis(new ObjectInputStream(getInput()));
                    } catch (Exception e) {
                        Utils.log("Could not get the input channels for the socket! %s", e);
                        throw new ObjectConstructionException(e.getMessage());
                    }
                } catch (Exception e) {
                    Utils.log("Could not connect to game server! %s", e);
                }
            });
            thread.start();
            thread.join();
        } catch (Exception e) {
            Utils.log("The thread could not await the connection to game server! %s", e);
        }
    }

    @Override
    public void run() {
        while (Globals.inGame) {
            try {
                sendOutput(new Sample("Connor"));
                Object object = getOis().readObject();

                if (object instanceof TTTBoard) {
                    TTTBoard value = (TTTBoard) object;
                    Utils.log(value);
                }

                Thread.sleep(5000);
            } catch (Exception e) {
                Utils.log("Client Connection failed! %s", e);
            }
        }
    }

    private void closeSocket() {
        try {
            getSocket().close();
        } catch (Exception e) {
            Utils.log("Could not close the socket! %s", e);
        }
    }

    public void sendOutput(Object object) {
        try {
            getOos().writeObject(object);
            getOos().flush();
        } catch (Exception e) {
            Utils.log("Could not send the object (%s) to the client! %s", object, e);
        }
    }
}