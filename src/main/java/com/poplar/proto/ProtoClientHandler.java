package com.poplar.proto;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created By poplar on 2019/10/21
 */
public class ProtoClientHandler extends SimpleChannelInboundHandler<CustomPerson.Person> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomPerson.Person msg) throws Exception {
        System.out.println(msg.getName());
        System.out.println(msg.getAge());
        System.out.println(msg.getAddress());
    }
}
