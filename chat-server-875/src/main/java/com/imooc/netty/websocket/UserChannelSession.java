package com.imooc.netty.websocket;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话管理
 * 用户id和channel的管理关系处理
 */
public class UserChannelSession {
    //用于多端多设备同时接收消息，允许一个设备在多个设备同时在线，比如iPad
    private static Map<String, List<Channel>> multiSession = new ConcurrentHashMap<>();
    //用于记录用户id和客户端longId的关联关系
    private static Map<String, String> userChannelIdRelation = new ConcurrentHashMap<>();

    public static void putUserChannelIdRelation(String channelId, String userId) {
        userChannelIdRelation.put(channelId, userId);
    }

    public static String getUserChannelIdRelation(String channelId) {
        return userChannelIdRelation.get(channelId);
    }

    /**
     * 简历连接后初始化用户的会话
     * @param userId
     * @param channel
     */
    public static void putMultiSession(String userId, Channel channel) {
        List<Channel> channels = getMultiSession(userId);
        if (channels == null) {
            channels = new ArrayList<>();
        }
        channels.add(channel);
        multiSession.put(userId, channels);
    }

    public static List<Channel> getMultiSession(String userId) {
        return multiSession.get(userId);
    }
}
