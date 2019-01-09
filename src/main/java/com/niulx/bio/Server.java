package com.niulx.bio;


import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class Server {

    private static final int PORT = 1234;

    private static ServerSocket serverSocket;

    private static BufferedReader bufferedReader;
    private static PrintWriter out;

    public static void start() throws IOException {
        start(PORT);
    }

    private synchronized static void start(int port) throws IOException {
        if (serverSocket != null) return;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务端已启动,端口号:" + port);
            while (true) {
                Socket socket = serverSocket.accept();
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                String line;
                while (true) {
                    if ((line = bufferedReader.readLine()) == null) break;
                    out.println("小金金");
                    System.out.println("服务端收到信息: " + line);
                    System.out.println("服务端发送信息:小金金");
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
                bufferedReader = null;
            }

            if (out != null) {
                out.close();
                out = null;
            }

            if (serverSocket != null) {
                serverSocket.close();
                serverSocket = null;
            }
        }
    }

}
