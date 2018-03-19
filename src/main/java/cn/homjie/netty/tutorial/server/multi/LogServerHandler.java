package cn.homjie.netty.tutorial.server.multi;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author jiehong.jh
 * @date 2018/3/19
 */
public class LogServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("LogServerHandler msg hash code: " + msg.hashCode() + ", refCnt: " + msg.refCnt());
        System.out.println("Server received: " + msg.toString(CharsetUtil.UTF_8));
        // 需要触发 PongServerHandler
        ctx.fireChannelRead(msg);
    }
}
