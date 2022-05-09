package Chatogram.Server;


public class ChatogramServer {
    private HostSocket socket;
    private Datenbank db;

    public ChatogramServer() {
        System.out.println("Chatogram Server");
        db = new Datenbank();
        socket = new HostSocket(4999);
        while(true){
            String[] message = socket.receiveMessage();
            if(message[0].equals("error")) {
                System.out.println("Error, nothing received");
            } else if(message[0].equals("login")) {
                this.login(message);
            } else if(message[0].equals("register")) {
                this.register(message);
            } else if(message[0].equals("getChat")) {
                this.getChat(message);
            } else if(message[0].equals("getFriend")) {
                this.getFriend(message);
            }
        }
    }

    private void login(String[] pMessage) {
        String username = pMessage[1];
        String password = pMessage[2];
        String[] message = new String[2];
        message[0] = "login";
        if(db.loginUser(username, password)) {
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
        if(db.createUser(username, password)){
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
        message[1] = db.getChat(username1, username2);
        socket.sendMessage(message);
    }

    private void getFriend(String[] pMessage) {
        String username = pMessage[1];
        //message[0] = "getFriend" ist bereits in db.getFriends() vorhanden
        String[] message = db.getFriend(username);
        socket.sendMessage(message);
    }
}
