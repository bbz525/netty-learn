package com.poplar.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * Created By poplar on 2019/10/23
 * 传统的IO编程
 */
public class NIOServerTest {

    public static void main(String[] args) throws IOException {
        int[] ports = {5000, 5001, 5002, 5003};
        //创建selector
        Selector selector = Selector.open();

        //selector底层创建对象
        System.out.println("selector底层创建对象 " + SelectorProvider.provider().getClass());
        //使用循环绑定每个端口
        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //selector和channel使用时必须设置为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(address);

            System.out.println("监听端口：" + ports[i]);
            //将channel注册到selector上
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        }

        while (true) {
            /**
             * 在执行Selector的select()方法时，如果与SelectionKey相关的事件发生了，
             * 这个SelectionKey就被加入到selected-keys集合中
             */
            int numberKeys = selector.select();
            System.out.println("测试");
            System.out.println("返回key的数量 " + numberKeys);
            //获得所有key，因为同一时间可能连接多个channel，产生多个key
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                //如果当前key是之前的SelectionKey.OP_ACCEPT这种状态，说明这是个感兴趣的事件。
                if (selectionKey.isAcceptable()) {
                    //获取通道
                    ServerSocketChannel ServerChannel = (ServerSocketChannel) selectionKey.channel();
                    //获取链接的客户端
                    SocketChannel socketChannel = ServerChannel.accept();
                    socketChannel.configureBlocking(false);
                    //selector监听读事件
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    //这里非常重要，不然会导致程序执行出错
                    iterator.remove();
                    System.out.println("当前链接的客服端：" + socketChannel);
                } else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    int read = 0;
                    while (true) {
                        ByteBuffer buffer = ByteBuffer.allocate(512);
                        buffer.clear();

                        int readNum = socketChannel.read(buffer);
                        if (readNum <= 0) {
                            break;
                        }
                        read += readNum;
                        buffer.flip();
                        socketChannel.write(buffer);

                    }

                    System.out.println("读取: " + read + "来自: " + socketChannel);
                    iterator.remove();
                }
            }

        }
    }
}
