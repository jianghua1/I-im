package com.imooc.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

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
         * 4.状态检查处理器
         * 5.自定义空闲状态处理器
         * 6.websocket路由
         * 7.自定义处理器
         */
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
        pipeline.addLast(new IdleStateHandler(8, 10, 60));
        pipeline.addLast(new HeartBeatHandler());
        //本handler会帮我们处理一些繁重的事物，比如我手动做
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new ChatHandler());
        /**
         * 以上是websocket的编程模型，如果是高性能场景，pipeline上一定会添加编解码器，并且
         * 上方的例子直接就可以接收和发送字符串内容，在高性能通信中做不到，高性能通信传递的数据一定
         * 是byte数组（甚至还要压缩）。
         */
//        ChannelPipeline highPipeline = socketChannel.pipeline();
//        highPipeline.addLast("decoder",new StringDecoder());
//        highPipeline.addLast("encoder",new StringEncoder());
//        highPipeline.addLast("new XXXXHandler()");
    }
}
