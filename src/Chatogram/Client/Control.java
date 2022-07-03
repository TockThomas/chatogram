package Chatogram.Client;

public class Control {
    private String username;
    private String[] friendsList;
    private ClientSocket clientSocket;

    public Control(){
        this.clientSocket = new ClientSocket("localhost", 4999);
    }

    public String[] login(String pUsername, String pPassword){
        String[] message = {"login", pUsername, pPassword};
        String[] response = this.clientSocket.sendMessage(message);
        if(response[1].equals("success")) {
            this.username = pUsername;
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
        } else {
            this.friendsList = new String[0];
        }
    }

    public String[] getFriendsList() {
        this.fetchFriendsList();
        return this.friendsList;
    }

    public void writeMessage(String pFriend,String pMessage) {
        String[] message = {"writeMessage", this.username, pFriend, pMessage};
        clientSocket.sendMessage(message);  //Rückgabewert wird nicht gespeichert, ansonsten wartet er auf Antwort
    }

    public boolean addFriend(String pFriend){
        String[] message = {"addFriend", this.username, pFriend};
        String[] response = clientSocket.sendMessage(message);
        if(response[1].equals("success")) {
            this.fetchFriendsList();
            return true;
        } else {
            return false;
        }
    }

    public void deleteFriend(String pFriend){
        String[] message = {"deleteFriend", this.username, pFriend};
        String[] response = clientSocket.sendMessage(message);
    }

    public void changeUser(){
        this.username = null;
        this.friendsList = null;
    }
}
