package cn.homjie.netty.tutorial.server.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author jiehong.jh
 * @date 2018/3/19
 */
public class AddServerHandler extends SimpleChannelInboundHandler<Integer> {
    int sum = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Integer msg) throws Exception {
        System.out.println("sum add [" + msg + "]");
        sum += msg;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server return: " + sum);
        ByteBuf byteBuf = ctx.alloc().buffer().writeInt(sum);
        ctx.writeAndFlush(byteBuf).addListener(ChannelFutureListener.CLOSE);
    }
}
