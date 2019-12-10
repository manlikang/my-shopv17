package com.fuhan.netty.springbootnetty;

import com.fuhan.netty.pojo.MessageBean;
import com.fuhan.netty.utils.ChannelUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;


/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/27
 */

@Component
@ChannelHandler.Sharable
public class LinkHandler extends SimpleChannelInboundHandler<MessageBean<String>> {
    @Override
    protected void channelRead0(ChannelHandlerContext context, MessageBean<String> msg) throws Exception {
        if("1".equals(msg.getMsgType())){
            ChannelUtil.addChannel(msg.getData(),context.channel());
        }
    }
}
