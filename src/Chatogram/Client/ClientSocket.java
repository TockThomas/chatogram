package Chatogram.Client;

import java.io.*;
import java.net.Socket;

public class ClientSocket {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientSocket (String pIp, int pPort) {
        try {
            this.clientSocket = new Socket(pIp, pPort);
            this.out = new ObjectOutputStream(this.clientSocket.getOutputStream());
            this.in = new ObjectInputStream(this.clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] sendMessage(String[] pMessages) {
        String[] response = {"error"};
        try {
            this.out.writeObject(pMessages);
            this.out.flush();
            response = (String[]) in.readObject();
            System.out.println("Message to server: " + response[0]); //TODO: später deaktivieren
            return response;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void stopConnection() {
        try {
            clientSocket.close();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
