package com.poplar.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created By poplar on 2019/10/22
 * 使用nio完成文件的读写操作
 */
public class Test2 {

    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("input.txt");
        FileOutputStream outputStream = new FileOutputStream("out.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {
            //恢复缓冲区初始值
            buffer.clear();
            int read = inputChannel.read(buffer);
            System.out.println("read: " + read);
            if (-1 == read) {
                break;
            }
            buffer.flip();//capacity,position,limit三个值得变化
            outputChannel.write(buffer);
        }
        inputChannel.close();
        outputChannel.close();
    }
}
