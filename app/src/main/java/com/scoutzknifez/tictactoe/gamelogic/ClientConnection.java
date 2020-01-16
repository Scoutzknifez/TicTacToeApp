package com.scoutzknifez.tictactoe.gamelogic;

import com.scoutzknifez.tictactoe.gamelogic.dtos.GameState;
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
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
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

                        // Get the immediate response from the server which declares
                        //  if this player is X or O, and sets who turn it is (X goes first)
                        Object obj = getOis().readObject();
                        if (obj instanceof Value) {
                            Globals.isX = (boolean) ((Value) obj).getValue();
                            Globals.isMyTurn = Globals.isX;
                        }
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
                System.out.println("Waiting for read");
                Object object = getOis().readObject();
                System.out.println("Started read");

                if (object instanceof GameState) {
                    GameState gs = (GameState) object;
                    Globals.gameState = gs;
                    Globals.isMyTurn = gs.isXTurn() == Globals.isX;
                    Globals.refreshable.refresh();
                }
                else if (object instanceof Value) {
                    boolean response = (boolean) ((Value) object).getValue();
                    if (response)
                        Globals.isMyTurn = false;
                    // It was not a valid packet or it was not their turn and packet was sent
                    else {
                        // TODO Send a packet from the server to force the UI to update itself
                        //  Therefore syncing the client back with the server and game
                    }
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

    public synchronized void sendOutput(final Object object) {
        new Thread(() -> {
            try {
                getOos().writeObject(object);
                getOos().flush();
            } catch (Exception e) {
                Utils.log("Could not send the object (%s) to the server! %s", object, e);
            }
        }).start();

    }
}