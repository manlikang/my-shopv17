package com.fuhan.netty.utils;

import io.netty.channel.Channel;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/27
 */
public class ChannelUtil  {



    private static Map<String, Channel> map = new HashMap<>();

    public static void addChannel(String userId,Channel channel){
        map.put(userId,channel);
    }

    public static void removeByUserId(String userId){
        map.remove(userId);
    }

    public static Channel getChannel(String userId){
        return map.get(userId);
    }

    public static void removeByChannel(Channel channel){
        map.forEach((k,v)->{
            if(v==channel){
                map.remove(k);
                return;
            }
        });
    }

    public static Map<String, Channel> getMap() {
        return map;
    }

    public static void setMap(Map<String, Channel> map) {
        ChannelUtil.map = map;
    }
}
