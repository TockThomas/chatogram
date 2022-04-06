package Chatogram.Server;


public class ChatogramServer {
    private HostSocket socket;
    private Datenbank db;

    public ChatogramServer() {
        db = new Datenbank();
        db.createUser("TockThomas", "123");
        /*socket = new HostSocket(4999);
        while(true){
            String[] message = socket.receiveMessage();
            if(message[0].equals("error")) {
                System.out.println("Error, nothing received");
            } else if(message[0].equals("login")) {
                this.login(message);
            }
        }*/
    }

    private void login(String[] pMessage) {
        String username = pMessage[1];
        String password = pMessage[2];
        String[] message = new String[2];
        message[0] = "login";
        if(username.equals("Test") && password.equals("test")) {
            message[1] = "success";
        } else {
            message[1] = "failed";
        }
        socket.sendMessage(message);
    }
}
