package Chatogram.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;;

public class Program {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSockets = new ServerSocket(4999);
        while(true){
            Socket socket = null;
            try {
                socket = serverSockets.accept();
                System.out.println("A new client is connected: " + socket);
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                Server server = new Server(socket, objectInputStream, objectOutputStream);
                Thread thread = new Thread(server);
                thread.start();
            } catch (SocketException e){
                socket.close();
                e.printStackTrace();
            }
        }
    }
}
