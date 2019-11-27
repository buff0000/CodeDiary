package com.buff.nio.multithread;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Author: Buff
 * @Description:
 * @Date: Created in 2019-11-28 0:46
 */
public class SelectSockets {

    public static int PORT_NUMBER = 1234;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) throws Exception {
        new SelectSockets().go(args);
    }

    public void go(String[] args) throws Exception {
        int port = PORT_NUMBER;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        System.out.println("Listening on port " + port);
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverChannel.socket();
        Selector selector = Selector.open();
        serverSocket.bind(new InetSocketAddress(port));
        //设置非阻塞模式
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int n = selector.select(); //阻塞
            if (n == 0) {//一个事件都没有获取到，那只能继续
                continue;
            }
            Iterator it = selector.selectedKeys().iterator();
            //迭代获取到的SelectionKey
            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();
                //是否有连接接入进来，有就从ServerSocketChannel通道类中获取到连接进来的SocketChannel
                if (key.isAcceptable()) {//等价于 if ((key.readyOps( ) & SelectionKey.OP_ACCEPT) != 0)
                    ServerSocketChannel server =
                            (ServerSocketChannel) key.channel();
                    SocketChannel channel = server.accept();
                    registerChannel(selector, channel
                            , SelectionKey.OP_READ);
                    sayHello(channel);
                }
                //是否有数据已经准备好，有就请准备读吧
                if (key.isReadable()) {
                    readDataFromSocket(key);
                }
                //当对于一个SelectionKey消费完了，要记得移除掉
                it.remove();
            }
        }

    }

    protected void registerChannel(Selector selector,
                                   SelectableChannel channel, int ops) throws Exception {
        if (channel == null) {
            return;
        }
        channel.configureBlocking(false);
        channel.register(selector, ops);
    }


    protected void readDataFromSocket(SelectionKey key) throws Exception {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int count;
        buffer.clear(); //Empty buffer
        while ((count = socketChannel.read(buffer)) > 0) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                socketChannel.write(buffer);
            }
            buffer.clear();
        }
        //如果没接收到数据就关闭通道（连接）
        if (count < 0) {
            socketChannel.close();
        }
    }

    /**
     * 打招呼
     * <p>
     * Author: chenkangxian
     * <p>
     * Last Modification Time: 2011-7-11
     *
     * @param channel 客户端channel
     * @throws Exception
     */
    private void sayHello(SocketChannel channel) throws Exception {
        buffer.clear();
        buffer.put("Hello! \r\n".getBytes());
        buffer.flip();
        channel.write(buffer);
    }

}

