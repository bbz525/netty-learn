package com.poplar.zero;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created By poplar on 2019/11/1
 * nio零拷贝 客服端
 */
public class NioClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8180));
        socketChannel.configureBlocking(true);

        String fileName = "E:\\DownLoad\\Snipaste-1.16.2-x64.zip";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long startTime = System.currentTimeMillis();
        /*
         * his method is potentially much more efficient than a simple loop
         * that reads from this channel and writes to the target channel.  Many
         * operating systems can transfer bytes directly from the filesystem cache
         * to the target channel without actually copying them.
         * */
        long len;
        long count=0;
        len = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送总字节数：" + len + "  总耗时" + (System.currentTimeMillis() - startTime));
        fileChannel.close();
    }
}
