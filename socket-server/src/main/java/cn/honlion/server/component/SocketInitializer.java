package cn.honlion.server.component;

import cn.honlion.server.handler.SocketHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import org.springframework.stereotype.Component;

/**
 * @Description netty初始化
 * @Author XIEZHONGPING
 * @Date 2022/8/30 16:16
 */
@Component
public class SocketInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 添加对字节码数组的解码，netty提供了很多编码器
        pipeline.addLast(new ByteArrayDecoder());
        pipeline.addLast(new ByteArrayEncoder());
        // 添加自己的处理器
        pipeline.addLast(new SocketHandler());
    }
}
