package cn.honlion.client.thread;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @Description 客户端线程
 * @Author XIEZHONGPING
 * @Date 2022/8/30 16:35
 */
public class ClientThread implements Runnable{

    private final Selector selector;

    public ClientThread(Selector selector){
        this.selector = selector;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            while (true){
                int channels = selector.select();
                if (0 == channels){
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
                while (selectionKeyIterator.hasNext()){
                    SelectionKey selectionKey = selectionKeyIterator.next();
                    // 移出当前集合中的迭代器避免重复处理
                    selectionKeyIterator.remove();
                    if (selectionKey.isReadable()){
                        this.handleRead(selector,selectionKey);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleRead(Selector selector,SelectionKey selectionKey) throws IOException{
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        StringBuilder message = new StringBuilder();
        if (channel.read(byteBuffer)>0){
            byteBuffer.flip();
            message.append(StandardCharsets.UTF_8.decode(byteBuffer));
        }
        // 再次注册到选择器上，继续监听可读状态
        channel.register(selector,SelectionKey.OP_READ);
        System.out.println(message);
    }
}
