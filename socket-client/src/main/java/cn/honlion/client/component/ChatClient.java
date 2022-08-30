package cn.honlion.client.component;

import cn.honlion.client.thread.ClientThread;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClient {

    public void start(String name) throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8090));
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 监听服务端发来的消息
        new Thread(new ClientThread(selector)).start();
        // 监听用户输入
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            if (StringUtils.hasText(message)) {
                socketChannel.write(StandardCharsets.UTF_8.encode(name + ":" + message));
            }
        }
    }
}
