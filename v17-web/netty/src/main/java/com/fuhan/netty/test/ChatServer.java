package com.fuhan.netty.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/26
 */
public class ChatServer {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    public ChatServer(){
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(6666));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务端准备就绪");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start(){
        try {
            while (true){//不断轮询
                //每隔1秒钟查看是否可处理的事件
                if(selector.select(1000) == 0){
                    //非阻塞的体现
                    //System.out.println("当前没有事件需要处理，服务器可以做点其他事");
                    continue;
                }
                //获取到一系列待处理事件
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while(keyIterator.hasNext()){
                    //获取每一个待处理事件
                    SelectionKey key = keyIterator.next();
                    //
                    if (key.isAcceptable()){
                        SocketChannel channel = serverSocketChannel.accept();
                        //设置为非阻塞模式
                        channel.configureBlocking(false);
                        //注册到选择器并监听可读事件
                        channel.register(selector,SelectionKey.OP_READ);
                        //
                        System.out.println(channel.getRemoteAddress().toString()+":上线了....");
                    }

                    //
                    if (key.isReadable()){
                        //读取客户端发送的数据
                        readMsg(key);
                    }
                    //关键一步
                    //需要将当前的key移除，避免重复处理
                    keyIterator.remove();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void readMsg(SelectionKey key){
        SocketChannel channel = null;
        try {
            //1.获取通道对象
            channel = (SocketChannel) key.channel();
            //2.创建缓冲区对象
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //3.从通道读取数据到缓冲区
            int count = channel.read(buffer);
            //
            if (count > 0){
                String msg = new String(buffer.array());
                System.out.println(msg);
                //将channel设置为read，继续准备接受数据
                //key.interestOps(SelectionKey.OP_READ);
                //广播信息给其他客户端
                braodcast(channel,msg);
            }
            //清除缓冲区
            buffer.clear();
        }catch (Exception e){
            try {
                //当客户端关闭Channel时，进行异常处理
                System.out.println(channel.getRemoteAddress().toString()+":下线了....");
                //取消注册
                key.channel();
                //关闭通道
                channel.close();
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }

    public void braodcast(SocketChannel channel,String msg) throws IOException {
        System.out.println("广播消息给其他客户端....");
        for (SelectionKey key : selector.keys()) {
            Channel clientChannel = key.channel();
            //排除自身
            if(clientChannel instanceof SocketChannel && clientChannel != channel){
                SocketChannel dest = (SocketChannel) clientChannel;
                //把数据放到缓冲区
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //往通道写数据
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args){
        ChatServer server = new ChatServer();
        server.start();
    }

}
