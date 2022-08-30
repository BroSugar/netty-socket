package cn.honlion.server.listener;

import cn.honlion.server.component.SocketServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description application 启动完成监听
 * @Author XIEZHONGPING
 * @Date 2022/8/30 16:30
 */
@Component
public class NettyStartListener implements ApplicationRunner {

    @Resource
    private SocketServer socketServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.socketServer.start();
    }
}
