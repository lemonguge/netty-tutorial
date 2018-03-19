package cn.homjie.netty.tutorial.server.throwex;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author jiehong.jh
 * @date 2018/3/19
 */
public class CatchServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println(getClass().getSimpleName() + "处理异常[" + cause.getMessage() + "]");
        ctx.close();
    }
}
