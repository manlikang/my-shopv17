package com.fuhan.netty.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/26
 */
public class ChatClient {
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public ChatClient() throws IOException {
        //1.创建选择器对象
        selector = Selector.open();
        //2.连接服务器
        socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 6666));
        //3.设置为非阻塞模式
        socketChannel.configureBlocking(false);
        //4.注册并设置监听可读事件
        socketChannel.register(selector, SelectionKey.OP_READ);
        //5.把当前的客户端ip及端口作为用户名
        username = socketChannel.getLocalAddress().toString();
        System.out.println(username + " is ready!");
    }

    public void sendMsg(String msg) throws Exception{
        if("88".equals(msg)){
            //关闭通道
            socketChannel.close();
            socketChannel = null;
            return;
        }
        //拼接发送的消息
        msg = username+" 说："+msg;
        //将信息转换为缓冲区对象
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        //将缓冲区的数据写入到通道中
        socketChannel.write(buffer);
    }
    public void receiveMsg()throws Exception{
        while (true){
            //每隔1秒钟查看是否可处理的事件
            if(selector.select(2000) == 0){
                //非阻塞的体现
                //System.out.println("当前没有收到群友的其他消息");
                continue;
            }

            //获取需要处理的事件，因为可能存在多个客户端的事件需要处理，所以返回的是集合
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            //开始循环处理事件
            while (keyIterator.hasNext()){
                //获取到事件
                SelectionKey key = keyIterator.next();
                if(key.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    //创建一个缓存区对象
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    //将通道的数据读取到缓冲区
                    socketChannel.read(buffer);
                    //输出结果
                    System.out.println(new String(buffer.array()));
                }

                //关键一步
                //需要将当前的key移除，避免重复处理
                keyIterator.remove();
            }
        }
    }
    public static void main(String[] args) throws Exception {
        ChatClient chatClient = new ChatClient();
        //开启一个线程不断接收消息
        new Thread(() -> {
            try {
                chatClient.receiveMsg();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        //不断接收键盘录入发送消息
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()){
            String msg = scanner.nextLine();
            chatClient.sendMsg(msg);
        }

    }

}
