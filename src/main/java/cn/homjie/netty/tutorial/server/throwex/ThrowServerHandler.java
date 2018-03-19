package cn.homjie.netty.tutorial.server.throwex;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author jiehong.jh
 * @date 2018/3/19
 */
public class ThrowServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        throw new RuntimeException("Read Failure");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println(getClass().getSimpleName() + "不处理异常");
        ctx.fireExceptionCaught(cause);
    }
}
