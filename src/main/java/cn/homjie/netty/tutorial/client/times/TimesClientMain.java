package cn.homjie.netty.tutorial.client.times;

import cn.homjie.netty.tutorial.client.ClientBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author jiehong.jh
 * @date 2018/3/19
 */
public class TimesClientMain {

    public static void main(String[] args) throws Exception {
        ChannelInitializer<SocketChannel> initializer = new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new TimesClientHandler());
            }
        };
        ClientBootstrap bootstrap = new ClientBootstrap(initializer);
        bootstrap.start("127.0.0.1", 8890);
    }
}
