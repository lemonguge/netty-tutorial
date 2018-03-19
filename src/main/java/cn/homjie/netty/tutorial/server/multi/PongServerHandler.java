package cn.homjie.netty.tutorial.server.multi;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author jiehong.jh
 * @date 2018/3/19
 */
public class PongServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        // msg 将在 LogServerHandler 被自动释放，而 PongServerHandler 会再次自动释放 msg，需要增加引用计数
        msg.retain();
        System.out.println("PongServerHandler msg hash code: " + msg.hashCode() + ", refCnt: " + msg.refCnt());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Server return pong");
        ByteBuf byteBuf = ctx.alloc().buffer().writeBytes("pong".getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(byteBuf).addListener(ChannelFutureListener.CLOSE);
    }
}
