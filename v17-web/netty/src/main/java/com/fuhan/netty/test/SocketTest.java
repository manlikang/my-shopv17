package com.fuhan.netty.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/26
 */
public class SocketTest {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors()*2,1, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(10));

        while (true){
            Socket socket = serverSocket.accept();

            poolExecutor.execute(new HandleTask(socket));
        }
    }
}
