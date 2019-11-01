package com.poplar.zero;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created By poplar on 2019/10/31
 * 客服端
 */
public class TraditionalIOClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8899);
        String fileName = "E:\\DownLoad\\WebStorm-2019.2.2.exe";
        InputStream inputStream = new FileInputStream(fileName);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[8192];
        long count;
        long len = 0;

        long startTime = System.currentTimeMillis();
        while ((count = inputStream.read(buffer)) >= 0) {
            dataOutputStream.write(buffer);
            len += count;
        }
        System.out.println("发送总字节数：" + len + "  总耗时" + (System.currentTimeMillis() - startTime));
        dataOutputStream.close();
        inputStream.close();
        socket.close();
    }
}
