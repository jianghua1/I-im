package com.imooc.netty.websocket;


import com.imooc.enums.MsgTypeEnum;
import com.imooc.pojo.ChatMsg;
import com.imooc.pojo.DataContent;
import com.imooc.utils.GsonUtils;
import com.imooc.utils.LocalDateUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.util.List;


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
        /*
         * 1.获取客户端发来的消息并且解析
         */
        DataContent dataContent = GsonUtils.stringToBean(content, DataContent.class);
        ChatMsg chatMsg = dataContent.getChatMsg();
        String message = chatMsg.getMsg();
        String senderId = chatMsg.getSenderId();
        String receiverId = chatMsg.getReceiverId();
        //时间校准
        chatMsg.setChatTime(LocalDateTime.now());
        //获取消息类型用于判断
        Integer msgType = chatMsg.getMsgType();
        //消息的类型判断
        if (msgType == MsgTypeEnum.CONNECT_INIT.type) {
            //初始化channel会话，将用户和channel关联起来
            UserChannelSession.putUserChannelIdRelation(currentChannelId, senderId);
            UserChannelSession.putMultiSession(senderId, channel);
        } else if (msgType == MsgTypeEnum.WORDS.type) {
            //通过信息接收人的id找到会话
            List<Channel> channelList = UserChannelSession.getMultiSession(receiverId);
            if (channelList == null || channelList.isEmpty()) {
                //将消息推到容器中
                chatMsg.setReceiverOnline(false);
                return;
            } else {
                chatMsg.setReceiverOnline(true);
                String chatTimeFormat = LocalDateUtils.format(chatMsg.getChatTime(), LocalDateUtils.DATETIME_PATTERN_2);
                //对消息进行再次的封装
                dataContent.setChatTime(chatTimeFormat);
                for (Channel c : channelList) {
                    Channel findChannel = clients.find(c.id());
                    if (findChannel == null) {
                        continue;
                    }
                    findChannel.writeAndFlush(new TextWebSocketFrame(GsonUtils.object2String(dataContent)));
                }

            }

        }

        //给客户端写入消息，将消息使用TextWebSocketFrame包裹
        TextWebSocketFrame replayMsg = new TextWebSocketFrame("当前客户端的id为：" + currentChannelId);
        //对连接进来的客户回复消息
//        channel.writeAndFlush(replayMsg);
        //向所有客户端群发消息
//        clients.writeAndFlush(replayMsg);
//        for (Channel channel1 : clients) {
//            channel1.writeAndFlush(replayMsg);
//        }
        UserChannelSession.outputMulti();
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
        //短线重连删除过去的会话
        String userId = UserChannelSession.getUserChannelIdRelation(channelId);
        UserChannelSession.removeUselessChannels(channelId, userId);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //当发生异常时，将当前channel关闭
        ctx.channel().close();
        clients.remove(ctx.channel());
        String channelId = ctx.channel().id().asLongText();
        String userId = UserChannelSession.getUserChannelIdRelation(channelId);
        UserChannelSession.removeUselessChannels(channelId, userId);
    }
}
