package com.niulx.bio;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            try {
                Server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);

        new Thread(() -> {
            try {
                Client.send();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
