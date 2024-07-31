package com.imooc.netty.websocket;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话管理
 * 用户id和channel的管理关系处理
 */
public class UserChannelSession {

    //用于多端多设备同时接收消息，允许一个设备在多个设备同时在线
    private static Map<String, List<Channel>> multiSession = new ConcurrentHashMap<>();
    //用于客户端longId和用户id的关联关系
    /**
     * 用户id是可以伪造的，但是客户端id是不能伪造的，所以映射是客户端和用户的关系
     * 当客户端找到对应的用户id时，使用用户id进行校验，校验通过后从multiSession中获取channel的对象
     */
    private static Map<String, String> userChannelIdRelation = new ConcurrentHashMap<>();

    /**
     * 设置channelId和用户id的映射关系
     *
     * @param channelId
     * @param userId
     */
    public static void putUserChannelIdRelation(String channelId, String userId) {
        userChannelIdRelation.put(channelId, userId);
    }

    /**
     * 通过channelId查询用户Id
     *
     * @param channelId
     * @return
     */
    public static String getUserChannelIdRelation(String channelId) {
        return userChannelIdRelation.get(channelId);
    }

    /**
     * 建立连接后初始化用户会话
     *
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

    /**
     * 通过用户id获取channel的列表
     *
     * @param userId
     * @return
     */
    public static List<Channel> getMultiSession(String userId) {
        return multiSession.get(userId);
    }

    public static void outputMulti() {
        System.out.println("---------------------");
        multiSession.keySet().forEach(key -> {
            System.out.println("+++++++++++++");
            System.out.println("UserId:" + key);
            List<Channel> channels = multiSession.get(key);
            for (Channel c : channels) {
                System.out.println("\t\tChannelId:" + c.id().asLongText());
            }
            System.out.println("+++++++++++++");
        });
        System.out.println("---------------------");
    }
}
