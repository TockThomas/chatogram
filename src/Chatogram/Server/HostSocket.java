package Chatogram.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HostSocket {
    private ServerSocket hostSocket;
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public HostSocket(int pPort) {
        try {
            this.hostSocket = new ServerSocket(pPort);
            this.clientSocket = this.hostSocket.accept();
            this.out = new ObjectOutputStream(this.clientSocket.getOutputStream());
            this.in = new ObjectInputStream(this.clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] receiveMessage(){
        try {
            return (String[]) this.in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            String[] errorMessage = {"error"};
            return errorMessage;
        }
    }

    public void sendMessage(String[] pMessage) {
        try {
            this.out.writeObject(pMessage);
            this.out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            this.hostSocket.close();
            this.clientSocket.close();
            this.out.close();
            this.in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
