package cn.homjie.netty.tutorial.server.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 对于编码器和解码器来说，其过程也是相当的简单：一旦消息被编码或者解码，它就会被 ReferenceCountUtil.release(message) 调用自动释放。
 */
public class ToIntegerDecoder extends ByteToMessageDecoder {
    /**
     * 对这个方法的调用将会重复进行，直到确定没有新的元素被添加到该 List，或者该 ByteBuf 中没有更多可读取的字节时为止。
     *
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in,
                       List<Object> out) throws Exception {
        // 检查是否至少有 4 字节可读（一个 int 的字节长度）
        int readableBytes = in.readableBytes();
        System.out.println("readableBytes: " + readableBytes);
        if (readableBytes >= 4) {
            //从入站 ByteBuf 中读取一个 int，并将其添加到解码消息的 List 中
            int i = in.readInt();
            System.out.println("list add [" + i + "]");
            // 每解码一个消息，它的内容将会被发送给下一个 ChannelInboundHandler
            out.add(i);
        }
    }
    /**
     * readableBytes: 12
     * list add [52]
     * sum add [52]
     * readableBytes: 8
     * list add [14]
     * sum add [14]
     * readableBytes: 4
     * list add [23]
     * sum add [23]
     * server return: 89
     */
}

