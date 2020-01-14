package com.scoutzknifez.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import com.scoutzknifez.tictactoe.fragments.TicTacToeBoard;
import com.scoutzknifez.tictactoe.gamelogic.InboundHandler;
import com.scoutzknifez.tictactoe.utility.Constants;
import com.scoutzknifez.tictactoe.utility.Globals;
import com.scoutzknifez.tictactoe.utility.Utils;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4J2LoggerFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transitionToFragment(new TicTacToeBoard(), Constants.TICTACTOE_BOARD_TAG);

        new Thread(() -> {
            try {
                InternalLoggerFactory.setDefaultFactory(Log4J2LoggerFactory.INSTANCE);

                boolean hasEpoll = Epoll.isAvailable();

                EventLoopGroup group = hasEpoll ? new EpollEventLoopGroup() : new NioEventLoopGroup();

                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(group)
                        .channel(hasEpoll ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                        .handler(new InboundHandler());

                Channel channel = bootstrap.connect("10.0.2.2", 5050).sync().channel();
                channel.closeFuture().addListener(future -> {
                    Utils.log("Connection closed with server!");
                });

            } catch (Exception e) {
                e.printStackTrace();
                Utils.log("Failed to connect to server!");
            }
        }).start();


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
