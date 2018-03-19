package cn.homjie.netty.tutorial.server.decode;

import cn.homjie.netty.tutorial.server.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author jiehong.jh
 * @date 2018/3/19
 */
public class AddServerMain {

    public static void main(String[] args) throws Exception {
        ChannelInitializer<SocketChannel> initializer = new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                // 多个SimpleChannelInboundHandler
                pipeline.addLast(new ToIntegerDecoder());
                pipeline.addLast(new AddServerHandler());
            }
        };
        ServerBootstrap bootstrap = new ServerBootstrap(initializer);
        bootstrap.start(8890);
    }
}
