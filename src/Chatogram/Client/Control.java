package Chatogram.Client;

public class Control {
    private int id;
    private String username;
    private String[] friendsList;
    private ClientSocket clientSocket;

    public Control(){
        this.id = 0; //TODO: ändern
        this.clientSocket = new ClientSocket("localhost", 4999);
    }

    public String[] login(String pUsername, String pPassword){
        String[] message = {"login", pUsername, pPassword};
        String[] response = this.clientSocket.sendMessage(message);
        if(response[1].equals("success")) {
            this.username = pUsername;
            this.fetchFriendsList();
        }
        return response;
    }

    public String register(String pUsername, String pPassword){
        String[] message = {"register", pUsername, pPassword};
        String[] response = this.clientSocket.sendMessage(message);
        return response[1];
    }

    public String getChat(String pFriend){
        String[] message = {"getChat", this.username, pFriend};
        String[] response = clientSocket.sendMessage(message);
        return response[1];
    }

    private void fetchFriendsList(){
        String[] message = {"getFriend", this.username};
        String[] response = clientSocket.sendMessage(message);
        if(response.length != 1) {
            this.friendsList = new String[response.length - 1];
            for(int i = 0; i < response.length - 1; i++) {
                this.friendsList[i] = response[i+1];
            }
        }
    }

    public String[] getFriendsList() {
        return this.friendsList;
    }

    public void writeMessage(String pFriend,String pMessage) {
        //TODO: Server kontaktieren
    }
}
