package com.imooc.netty.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 创建自定义httpHandler处理类
 */
public class HttpHandler extends SimpleChannelInboundHandler<HttpObject> {
    //SimpleChannelInboundHandler这个类的核心方法是channelRead，作用就是做消息的路由，当消息的条件满足进入channelRead0时，
    //交给channelRead0的方法处理 channelRead0被我们写成什么样，SimpleChannelInboundHandler的入站逻辑就是什么样
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //获取channel
        Channel channel = ctx.channel();
        if(msg instanceof HttpRequest){
            //显示用户的远程地址
            System.out.println(channel.remoteAddress());
            //向客户端返回数据（处理用户的请求）
            //通过缓冲区定义发送的数据消息
            ByteBuf content = Unpooled.copiedBuffer("Hello netty", CharsetUtil.UTF_8);
            //构建HTTP的响应数据
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            //响应数据写入缓冲区，再刷新到客户端
            ctx.writeAndFlush(response);
        }
    }
}
