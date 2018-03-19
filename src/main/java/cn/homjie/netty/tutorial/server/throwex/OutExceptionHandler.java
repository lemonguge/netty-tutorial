package cn.homjie.netty.tutorial.server.throwex;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;

/**
 * @author jiehong.jh
 * @date 2018/3/19
 */
public class OutExceptionHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("OutboundExceptionHandler处理异常[" + cause.getMessage() + "]");
        ctx.close();
    }
}