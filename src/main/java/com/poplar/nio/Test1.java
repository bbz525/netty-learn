package com.poplar.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * Created By poplar on 2019/10/22
 */
public class Test1 {

    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            int random = new SecureRandom().nextInt(20);
            buffer.put(random);
        }
        //改变buffer的状态
        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
