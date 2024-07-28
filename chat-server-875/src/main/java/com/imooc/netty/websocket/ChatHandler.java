package com.imooc.netty.websocket;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 处理消息的handler
 * TextWebSocketFrame 是websocket在netty中的载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取客户端传输过来的内容
        String text = msg.text();
        //获取当前用户连接的客户端channel
        Channel channel = ctx.channel();
        String currentChannelId = channel.id().asLongText();
        //给客户端写入消息，将消息使用TextWebSocketFrame包裹
        TextWebSocketFrame replayMsg = new TextWebSocketFrame("当前客户端的id为：" + currentChannelId);
        channel.writeAndFlush(replayMsg);
    }
}
