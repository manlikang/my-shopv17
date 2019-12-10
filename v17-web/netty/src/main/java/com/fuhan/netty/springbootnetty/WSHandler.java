package com.fuhan.netty.springbootnetty;

import com.fuhan.netty.pojo.MessageBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/27
 */
@Component
@ChannelHandler.Sharable
public class WSHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext context, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        System.out.println("接收到的消息为:"+text);
        Gson gson = new Gson();
        MessageBean<String> messageBean = gson.fromJson(text,new TypeToken<MessageBean<String>>(){}.getType());
        context.fireChannelRead(messageBean);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"断开连接");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
        System.out.println(ctx.channel().remoteAddress()+"已连接");
    }
}
