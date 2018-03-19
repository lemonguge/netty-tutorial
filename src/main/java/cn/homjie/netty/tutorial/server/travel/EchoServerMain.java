package cn.homjie.netty.tutorial.server.travel;

import cn.homjie.netty.tutorial.server.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author jiehong.jh
 * @date 2018/3/19
 */
public class EchoServerMain {

    public static void main(String[] args) throws Exception {
        EchoServerHandler echoServerHandler = new EchoServerHandler();
        ChannelInitializer<SocketChannel> initializer = new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                //EchoServerHandler 被标注为@Shareable，所以我们可以总是使用同样的实例
                //这里对于所有的客户端连接来说，都会使用同一个 EchoServerHandler，因为其被标注为@Sharable，
                ch.pipeline().addLast(echoServerHandler);
            }
        };
        ServerBootstrap bootstrap = new ServerBootstrap(initializer);
        bootstrap.start(8890);
    }
}
