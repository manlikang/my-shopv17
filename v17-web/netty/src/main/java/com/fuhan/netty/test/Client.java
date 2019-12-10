package com.fuhan.netty.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/26
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8888);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello".getBytes());
        outputStream.close();
        socket.close();
    }
}
