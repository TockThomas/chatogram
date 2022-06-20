package Chatogram.Server;

import java.io.*;
import java.net.Socket;

public class HostSocket {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public HostSocket(Socket pS, ObjectInputStream pOis, ObjectOutputStream pOos) {
        this.clientSocket = pS;
        this.in = pOis;
        this.out = pOos;
    }

    public String[] receiveMessage(){
        try {
            return (String[]) this.in.readObject();
        } catch (IOException | ClassNotFoundException e) {
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
            this.out.close();
            this.in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
