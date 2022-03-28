package Chatogram.Client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.in;

public class ClientSocket {
    private Socket clientSocket;
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;
    private InputStream inputStream;
    private ObjectInputStream objectInputStream;

    public void startConnection(String pIp, int pPort) {
        try {
            this.clientSocket = new Socket(pIp, pPort);
            this.outputStream = clientSocket.getOutputStream();
            this.objectOutputStream = new ObjectOutputStream(outputStream);
            this.inputStream = clientSocket.getInputStream();
            this.objectInputStream = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> sendMessage(List<String> pMessages) {
        List<String> response = new ArrayList<String>();
        response.add(new String("Error"));
        try {
            objectOutputStream.writeObject(pMessages);
            response = (List<String>) this.objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void stopConnection() {
        try {
            inputStream.close();
            objectInputStream.close();
            outputStream.close();
            objectOutputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
