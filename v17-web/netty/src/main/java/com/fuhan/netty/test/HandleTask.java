package com.fuhan.netty.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/26
 */
public class HandleTask implements Runnable {
    private Socket socket;

    public HandleTask(Socket socket) {
        this.socket=socket;
    }

    @Override
    public void run() {

        try {
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            while ((len=inputStream.read(bytes))!=-1){
                System.out.println(new String(bytes,0,len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
