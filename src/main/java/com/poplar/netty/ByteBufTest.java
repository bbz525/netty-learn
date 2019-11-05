package com.poplar.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * Created By poplar on 2019/11/4
 * netty提供的缓冲区
 */
public class ByteBufTest {

    public static void main(String[] args) {
        ByteBuf buffer= Unpooled.buffer(11);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.setByte(i,i);
        }

        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.getByte(i));
        }

        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello World", Charset.forName("utf-8"));
        System.out.println(buffer);
    }
}
