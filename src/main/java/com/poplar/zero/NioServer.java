package com.poplar.zero;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created By poplar on 2019/10/31
 * Nio的零拷贝 服务端
 */
public class NioServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        /*调用这个方法的作用主要是因为，当我们想绑定的某个端口时，虽然那个端口的服务已经释放了端口，但是端口还处于“TIME_WAIT”状态
         * 应用试图绑定时就会报错，意思是当那个端口处于TIME_WAIT时就可以重新使用
         * */
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(8180));
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(true);
            int len = 0;
            while (len != -1) {
                len = socketChannel.read(buffer);
                buffer.rewind();
            }
        }

    }
}
