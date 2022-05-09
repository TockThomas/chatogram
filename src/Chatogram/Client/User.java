package Chatogram.Client;

public class User {
    private int id;
    private String username;
    private String[] friends;
    private int[] friendsId;
    //private Chat[] chat;

    public User(String pUsername){
        this.username = pUsername;
        this.id = 0; //TODO: ändern
    }

    private void refreshFriends(String pUsername) {
        /*this.friends = new String[2];
        this.friendsId = new int[2];
        this.friends[0] = "Test2";
        this.friendsId[0] = 1;
        this.friends[1] = "TestUser2";
        this.friendsId[1] = 2;
        this.chat = new Chat[this.friends.length];
        for(int i = 0; i < friends.length; i++) {
            this.chat[i] = new Chat(pUsername, friends[i]);
        }*/
    }

    public String[] getFriends() {
        return this.friends;
    }

    public String getFriend(int pFriendId) {
        return this.friends[pFriendId];
    }

    public void setFriend(String[] pFriend) {
        if(pFriend.length != 1) {
            this.friends = new String[pFriend.length - 1];
            for(int i = 0; i < pFriend.length - 1; i++) {
                this.friends[i] = pFriend[i+1];
            }
        }
    }

    public String getUsername() {
        return this.username;
    }
}
