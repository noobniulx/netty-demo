package com.niulx.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private static int PORT = 1234;

    private static String DEFAULT_HOST = "127.0.0.1";





    public static void send() throws IOException {
        Socket socket = null;
        BufferedReader in = null;

        PrintWriter out = null;

        try {
            socket = new Socket(DEFAULT_HOST, PORT);
            while (true) {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println("hello world");
                System.out.println("客户端发送信息：hello world");
                System.out.println("客户端接受消息: " + in.readLine());
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (in != null) {
                in.close();
                in = null;
            }
            if (out != null) {
                out.close();
                out = null;
            }
            if (socket != null) {
                socket.close();
                socket = null;
            }
        }
    }
}
