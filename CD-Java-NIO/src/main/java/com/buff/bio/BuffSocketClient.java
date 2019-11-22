package com.buff.bio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Author: Buff
 * @Description:
 * @Date: Created in 2019-11-21 23:08
 */
public class BuffSocketClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8888);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                out.println(Math.random());
                Thread.sleep(3000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
