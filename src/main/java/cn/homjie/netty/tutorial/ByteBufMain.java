package cn.homjie.netty.tutorial;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import io.netty.util.IllegalReferenceCountException;

/**
 * @author jiehong.jh
 * @date 2018/3/19
 */
public class ByteBufMain {

    public static void main(String[] args) {
        // Netty 的字节容器
        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello Netty!", CharsetUtil.UTF_8);
        System.out.println("before release: " + byteBuf.refCnt());
        byteBuf.release();
        System.out.println("after release: " + byteBuf.refCnt());
        try {
            byteBuf.toString(CharsetUtil.UTF_8);
        } catch (IllegalReferenceCountException ex) {
            System.err.println("已经释放的对象应该不可再用");
        }

        byteBuf = Unpooled.buffer();
        byteBuf.writeBytes("New buffer".getBytes(CharsetUtil.UTF_8));
        System.out.println("readerIndex: " + byteBuf.readerIndex() + ", writerIndex: " + byteBuf.writerIndex());
        ByteBuf slice = byteBuf.slice(4, 3);
        System.out.println("slice: " + slice.toString(CharsetUtil.UTF_8));

    }
}
