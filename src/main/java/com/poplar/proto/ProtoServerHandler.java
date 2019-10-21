package com.poplar.proto;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created By poplar on 2019/10/21
 */
public class ProtoServerHandler extends SimpleChannelInboundHandler<CustomPerson.Person> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomPerson.Person msg) throws Exception {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CustomPerson.Person person = CustomPerson.Person.newBuilder().setName("猫花").setAge(18).setAddress("重庆").build();
        ctx.channel().writeAndFlush(person);
    }
}
