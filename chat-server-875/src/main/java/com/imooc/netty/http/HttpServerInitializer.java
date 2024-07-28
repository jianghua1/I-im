package com.imooc.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 初始化器，channel注册之后 会执行这里的初始化方法
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //pipeline中应该是定义一组channelHandler
        ChannelPipeline pipeline = socketChannel.pipeline();
        /**
         * 通过管道添加handler
         * 1.添加netty官方助手类（编解码工作）
         * 2.注册自定义的http处理类
         */
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());
        pipeline.addLast("httpHandler",new HttpHandler());
    }
}
