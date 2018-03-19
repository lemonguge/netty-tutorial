package cn.homjie.netty.tutorial.server.throwex;

import cn.homjie.netty.tutorial.server.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author jiehong.jh
 * @date 2018/3/19
 */
public class ThrowServerMain {

    public static void main(String[] args) throws Exception {
        ChannelInitializer<SocketChannel> initializer = new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new CatchServerHandler());
                // 多个SimpleChannelInboundHandler
                pipeline.addLast(new ThrowServerHandler());

                // 入站异常被出站Handler处理
                pipeline.addLast(new OutExceptionHandler());
                // 异常到达了 ChannelPipeline 的尾端，但没有被处理，netty 并尝试释放该异常。
            }
        };
        ServerBootstrap bootstrap = new ServerBootstrap(initializer);
        bootstrap.start(8890);
    }
}
