package com.buff.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: Buff
 * @Description:
 * @Date: Created in 2019-11-21 23:08
 */
public class BuffSocketServer {
    public static void main(String[] args) {
        //server1();
        //server2();
        server3();
    }

    private static void server1() {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String str = in.readLine();
                System.out.println(socket + "   收到的请求内容为：" + str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void server2() {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String str = in.readLine();
                System.out.println(socket + "   收到的请求内容为：" + str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void server3() {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        while(true) {
                            String str = in.readLine();
                            System.out.println(socket + "   收到的请求内容为：" + str);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
