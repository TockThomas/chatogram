package Chatogram.Server;


public class ChatogramServer {
    HostSocket serverSocket;

    public ChatogramServer() {
        serverSocket = new HostSocket();
        serverSocket.start(4999);
    }

}
