package org.example;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Main2 {
    public static void main(String[]args)throws Exception{

            Socket socket = new Socket("localhost",1234);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
            printWriter.println("RECEBA");
            socket.close();
    }
}
