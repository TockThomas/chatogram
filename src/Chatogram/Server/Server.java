package Chatogram.Server;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Server extends Thread{
    private HostSocket socket;
    private Database db;

    public Server(Socket pS, ObjectInputStream pOis, ObjectOutputStream pOos) {
        System.out.println("Chatogram Server");
        this.db = new Database();
        this.socket = new HostSocket(pS, pOis, pOos);
        while (true) {
            String[] message = this.socket.receiveMessage();
            if (message[0].equals("error")) {
                System.out.println("Client disconnected: " + this.socket);
                this.socket.stop();
                break;
            } else if (message[0].equals("login")) {
                this.login(message);
            } else if (message[0].equals("register")) {
                this.register(message);
            } else if (message[0].equals("getChat")) {
                this.getChat(message);
            } else if (message[0].equals("getFriend")) {
                this.getFriend(message);
            } else if (message[0].equals("writeMessage")) {
                this.writeMessage(message);
            } else if (message[0].equals("addFriend")) {
                this.addFriend(message);
            } else if (message[0].equals("deleteFriend")){
                this.deleteFriend(message);
            }
        }
    }

    private void login(String[] pMessage) {
        String username = pMessage[1];
        String password = pMessage[2];
        String[] message = new String[2];
        message[0] = "login";
        if(this.db.loginUser(username, password)) {
            message[1] = "success";
        } else {
            message[1] = "failed";
        }
        socket.sendMessage(message);
    }

    private void register(String[] pMessage) {
        String username = pMessage[1];
        String password = pMessage[2];
        String[] message = new String[2];
        message[0] = "register";
        if(this.db.createUser(username, password)){
            message[1] = "success";
        } else {
            message[1] = "failed";
        }
        socket.sendMessage(message);
    }

    private void getChat(String[] pMessage) {
        String username1 = pMessage[1];
        String username2 = pMessage[2];
        String[] message = new String[2];
        message[0] = "getChat";
        message[1] = this.db.getChat(username1, username2);
        socket.sendMessage(message);
    }

    private void getFriend(String[] pMessage) {
        String username = pMessage[1];
        //message[0] = "getFriend" ist bereits in db.getFriends() vorhanden
        String[] message = this.db.getFriend(username);
        socket.sendMessage(message);
    }

    private void writeMessage(String[] pMessage) {
        String username = pMessage[1];
        String friend = pMessage[2];
        String text = pMessage[3];
        this.db.writeMessage(username, friend, text);
        String[] message = {"writeMessage", "success"};
        socket.sendMessage(message);
    }

    private void addFriend(String[] pMessage) {
        String username = pMessage[1];
        String friend = pMessage[2];
        String[] message = new String[2];
        message[0] = "addFriend";
        message[1] = this.db.addFriend(username, friend);
        socket.sendMessage(message);
    }

    private void deleteFriend(String[] pMessage){
        String username = pMessage[1];
        String friend = pMessage[2];
        String[] message = new String[1];
        message[0] = "deleteFriend";
        this.db.deleteFriend(username, friend);
        socket.sendMessage(message);
    }
}
