package com.imooc.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WSServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //pipeline中应该是定义一组channelHandler
        ChannelPipeline pipeline = socketChannel.pipeline();
        /**
         * 通过管道添加handler
         * 1.添加netty官方助手类（编解码工作）
         * 2.对大数据流的写支持
         * 3.几乎在netty编程中都会使用到handler
         * 4.websocket路由
         * 5.自定义处理器
         */
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
        //本handler会帮我们处理一些繁重的事物，比如我手动做
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new ChatHandler());
    }
}
