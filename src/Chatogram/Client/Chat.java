package Chatogram.Client;

import java.util.ArrayList;

public class Chat {
    private String username;
    private String friend;

    public Chat(String pUsername, String pFriend){
        this.username = pUsername;
        this.friend = pFriend;
    }


    public void writeMessage(String pMessage) {
        //TODO: Server kontaktieren
    }

    public String getChat() {
        String chat = "<html><body>";
        for(int i = 0; i < 2; i++) {
            chat += "Textnachricht" + i;
            if(i < 1) {
                chat += "<br>";
            }
        }
        chat += "</body></html>";
        return chat;
    }
}
