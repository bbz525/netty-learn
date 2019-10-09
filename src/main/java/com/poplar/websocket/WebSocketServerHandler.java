package com.poplar.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * Created By poplar on 2019/10/9
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("收到来自客服端发来的消息： " + msg.text());
        /**
         * 其实这里writeAndFlush（）里面传入的是object类型，说明无论传什么参数都不会报错
         * 但是我们这里是要传一个TextWebSocketFrame对象，所以不能传单独的字符串，传了也传不出去
         * 因为我们只用了TextWebSocketFrame
         */
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器的时间：" + LocalDateTime.now()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        /* id表示唯一，有长有短，长的asLongText，唯一。短的asShortText()不唯一*/
        System.out.println("handlerAdded " + ctx.channel().id().asLongText());
    }

    /*此处当我们刷新客服端时，该方法会被调用2次，说明重新简历了一个链接*/
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved " + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
