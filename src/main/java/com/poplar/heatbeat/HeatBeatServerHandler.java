package com.poplar.heatbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created By poplar on 2019/10/9
 */
public class HeatBeatServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String stateType = null;
            switch (event.state()) {
                case ALL_IDLE:
                    stateType = "读写空闲";
                    break;
                case READER_IDLE:
                    stateType = "读空闲";
                    break;
                case WRITER_IDLE:
                    stateType = "写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + " 超时事件: " + stateType);
        }
        ctx.channel().close();
    }
}
