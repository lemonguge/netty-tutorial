package cn.homjie.netty.tutorial.client.travel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author jiehong.jh
 * @date 2018/3/19
 */
@Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 当被通知 Channel是活跃的时候，发送一条消息
        ByteBuf byteBuf = Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8);

        // Step1、decode 的 AddServerMain 用
        // ByteBuf byteBuf = Unpooled.copyInt(52, 14, 23);

        System.out.println("before writeAndFlush: " + byteBuf.refCnt()); // 1
        // 消息将被释放，已经释放的对象不可再用
        ctx.writeAndFlush(byteBuf);
        System.out.println("after writeAndFlush: " + byteBuf.refCnt()); // 0
    }

    /**
     * 每当接收数据时，都会调用这个方法。需要注意的是由服务器发送的消息可能会被分块接收。
     * <p>
     * 也就是说，如果服务器发送了 5 字节，那么不能保证这 5 字节会被一次性接收。
     * <p>
     * 即使是对于这么少量的数据，channelRead0()方法也可能会被调用两次，第一次使用一个持有 3 字节的 ByteBuf，第二次使用一个持有 2 字节的 ByteBuf。
     * <p>
     * 作为一个面向流的协议，TCP保证了字节数组将会按照服务器发送它们的顺序被接收。
     *
     * @param ctx
     * @param in
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
        // 当从服务器接收到一条消息时被调用，将自动释放消息
        System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));

        // Step2、decode 的 AddServerMain 用
        // System.out.println("Client received: " + in.readInt());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //在发生异常时，记录错误并关闭Channel
        cause.printStackTrace();
        ctx.close();
    }
}
