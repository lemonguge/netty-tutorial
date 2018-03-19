package cn.homjie.netty.tutorial.client.times;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

/**
 * @author jiehong.jh
 * @date 2018/3/19
 */
public class TimesClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf buffer = ctx.alloc().buffer();
        System.out.println("Initialization, ri: " + buffer.readerIndex() + ", wi: " + buffer.writerIndex());

        buffer.writeBytes("Hello".getBytes(StandardCharsets.UTF_8));
        System.out.println(
            "before ctx write, ri: " + buffer.readerIndex() + ", wi: " + buffer.writerIndex()
                + ", refCnt:" + buffer.refCnt());
        // write()操作是异步的
        ctx.write(buffer);
        System.out.println("after ctx write, ri: " + buffer.readerIndex() + ", wi: " + buffer.writerIndex()
            + ", refCnt:" + buffer.refCnt());

        // 多次写入应该用同一个ByteBuf，否则会看到 "HelloWorld-" 而不是 "Hello-World"
        ctx.write(Unpooled.copiedBuffer("-", CharsetUtil.UTF_8));

        buffer.writeBytes("World".getBytes(StandardCharsets.UTF_8));
        // 1、因为 buffer 被挂起，在等待 flush()，此时不需要调用 write()
        // ctx.write(buffer);

        // 2.1、如果调用了 write()，会将 buffer 写入两次
        // 2.2、此时需要增加引用计数 buffer.retain()，否则无法正常释放
        // ctx.write(buffer);
        // buffer.retain();

        System.out.println("before ctx flush, ri: " + buffer.readerIndex() + ", wi: " + buffer.writerIndex()
            + ", refCnt:" + buffer.refCnt());
        // flush()将冲刷 Channel 所有挂起的写入
        ctx.flush();
        System.out.println("after ctx flush, ri: " + buffer.readerIndex() + ", wi: " + buffer.writerIndex()
            + ", refCnt:" + buffer.refCnt());

        // 在 EchoServerHandler 读完消息会关闭 Channel，所以 flush() 之后不要在写数据
        // 有可能会在 Channel 关闭后发送消息，将导致：java.io.IOException: Connection reset by peer
        // ctx.writeAndFlush(Unpooled.copiedBuffer("After flush()", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
        // 自动释放消息
        System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //在发生异常时，记录错误并关闭Channel
        System.err.println("记录异常");
        cause.printStackTrace();
        ctx.close();
    }
}
