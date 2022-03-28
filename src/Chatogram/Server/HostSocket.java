package Chatogram.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HostSocket {
    private ServerSocket hostSocket;
    private Socket clientSocket;
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;
    private InputStream inputStream;
    private ObjectInputStream objectInputStream;

    public void start(int pPort) {
        try {
            hostSocket = new ServerSocket(pPort);
            clientSocket = hostSocket.accept();
            inputStream = clientSocket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            outputStream = clientSocket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);

        } catch (IOException e) {
            System.out.println("Server failed");
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            inputStream.close();
            objectInputStream.close();
            outputStream.close();
            objectOutputStream.close();
            clientSocket.close();
            hostSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
