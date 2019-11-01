package com.poplar.zero;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created By poplar on 2019/10/31
 * 传统的IO从磁盘拷贝数据 服务端
 */
public class TraditionalIOServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8899);
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream stream = new DataInputStream(socket.getInputStream());
            try {
                byte[] buffer = new byte[8192];
                while (true) {
                    int read = stream.read(buffer, 0, buffer.length);
                    if (-1 == read) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

