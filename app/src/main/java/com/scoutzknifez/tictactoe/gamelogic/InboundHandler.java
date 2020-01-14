package com.scoutzknifez.tictactoe.gamelogic;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class InboundHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext context) {
        ByteBuf buf = context.alloc().buffer();

        String string = "cody@4alexanders.com";

        byte[] data = string.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(data.length);
        buf.writeBytes(data);

        context.writeAndFlush(buf, context.voidPromise());
    }
}
