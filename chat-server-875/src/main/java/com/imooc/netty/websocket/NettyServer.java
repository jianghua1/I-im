package com.imooc.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty 服务器
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        //因为程序在实际运行的时候，当关闭直接就是kill命令
        //定义主线程组，接收主线程组的连接，但是不做任何处理
        EventLoopGroup masterGroup = new NioEventLoopGroup();
        //定义从线程组，任务执行者，职能就是处理主线程丢过来的任务
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //服务启动器
            ServerBootstrap bootstrap = new ServerBootstrap();
            /**
             * 绑定关系配置开始
             */
            bootstrap.group(masterGroup, workerGroup);
            //客户端和服务端通信，服务端和客户端通信所以是双向通道
            bootstrap.channel(NioServerSocketChannel.class);
            //worker丢过来的信息需要处理，所以需要设置childHandler，至于如何处理需要我们编写
            bootstrap.childHandler(new HttpServerInitializer());
            /**
             * 绑定关系配置结束
             */
            ChannelFuture channelFuture = bootstrap.bind(875).sync();
            //监听关闭的channel
            channelFuture.channel().closeFuture().sync();
        } finally {
            masterGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
