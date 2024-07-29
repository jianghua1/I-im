package com.imooc.netty.websocket;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 处理消息的handler
 * TextWebSocketFrame 是websocket在netty中的载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //channel管理器
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取客户端传输过来的内容
        String content = msg.text();
        System.out.println("接收到的数据：" + content);
        //获取当前用户连接的客户端channel
        Channel channel = ctx.channel();
        String currentChannelId = channel.id().asLongText();

//        GsonUtils
        //给客户端写入消息，将消息使用TextWebSocketFrame包裹
        TextWebSocketFrame replayMsg = new TextWebSocketFrame("当前客户端的id为：" + currentChannelId);
        //对连接进来的客户回复消息
//        channel.writeAndFlush(replayMsg);
        //向所有客户端群发消息
        clients.writeAndFlush(replayMsg);
//        for (Channel channel1 : clients) {
//            channel1.writeAndFlush(replayMsg);
//        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
        String channelId = ctx.channel().id().asLongText();
        System.out.println("客户端连接，channel对应的长id为：" + channelId);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String channelId = ctx.channel().id().asLongText();
        System.out.println("客户端断开，channel对应的长id为：" + channelId);
        clients.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //当发生异常时，将当前channel关闭
        ctx.channel().close();
        clients.remove(ctx.channel());
    }
}
