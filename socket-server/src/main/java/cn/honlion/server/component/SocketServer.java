package cn.honlion.server.component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class SocketServer {

    @Resource
    private SocketInitializer socketInitializer;

    @Getter
    private ServerBootstrap serverBootstrap;

    /**
     * 服务端监听端口
     */
    @Value("${netty.port:8090}")
    private int port;

    /**
     * 主线程组数量
     */
    @Value("${netty.boss-thread:1}")
    private int bossThread;

    /**
     * 启动服务
     */
    public void start(){
        this.init();
        this.serverBootstrap.bind(this.port);
        log.info("netty started on port: {}(TCP) with boss thread {}",this.port,this.bossThread);
    }

    /**
     * 服务初始化配置
     */
    private void init(){
        // 创建两个线程组，bossgroup为接收请求的线程组 1-2个
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(this.bossThread);
        // 实际工作线程组
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        this.serverBootstrap = new ServerBootstrap();
        this.serverBootstrap.group(bossGroup,workGroup)
                // 配置nio类型
                .channel(NioServerSocketChannel.class)
                // 加入自己的初始化器
                .childHandler(this.socketInitializer);
    }
}
