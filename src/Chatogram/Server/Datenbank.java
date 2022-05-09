package Chatogram.Server;

import java.sql.*;
import java.util.ArrayList;

public class Datenbank {
    private String databaseURL;
    private Connection connection;

    public Datenbank() {
        this.databaseURL = "jdbc:ucanaccess://C:/Users/thoma/Documents/Chatogram.accdb";
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            this.connection = DriverManager.getConnection(this.databaseURL);
        } catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public boolean createUser(String pUsername, String pPassword) {
        try {
            String sql = "SELECT * FROM User WHERE Username=\"" + pUsername + "\"";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            result.next();
            try {
                int id = result.getInt("ID");
                if(id >= 0) {
                    System.out.println("Account " + pUsername + " exists, ID: " + id);
                    return false;
                }
            } catch (SQLException e) {
                sql = "INSERT INTO User (Username, Password) VALUES (?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, pUsername);
                preparedStatement.setString(2, pPassword);
                preparedStatement.executeUpdate();
                System.out.println("Account created: " + "pUsername");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace(); //TODO: debugging später ausschalten
        }
        return false;
    }

    public boolean loginUser(String pUsername, String pPassword) {
        try {
            String sql = "SELECT * FROM User WHERE Username=\"" + pUsername + "\"";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            result.next();
            if(result.getString("Password").equals(pPassword)){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace(); //TODO: später ausschalten
            return false;
        }
    }

    public String getUsername(int pUserId) {
        try{
            String sql = "SELECT * FROM User WHERE Id=\"" + pUserId + "\"";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            result.next();
            return result.getString("Username");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int getUserId(String pUsername) {
        try{
            String sql = "SELECT * FROM User WHERE Username=\"" + pUsername + "\"";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            result.next();
            return result.getInt("Id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getChat(String pUsername1, String pUsername2) {
        try {
            String text = "<html><body>";
            String username1 = pUsername1;
            String username2 = pUsername2;
            int userId1 = this.getUserId(username1);
            int userId2 = this.getUserId(username2);
            String sql = "SELECT * FROM Chat WHERE (UserId1=\"" + userId1 + "\" AND UserId2=\"" + userId2 + "\") OR (UserId1=\"" + userId2 + "\" OR UserId2=\"" + userId1 + "\")";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                if(result.getInt("UserId1") == userId1) {
                    text += username1 + ": " + result.getString("Text");
                } else {
                    text += username2 + ": " + result.getString("Text");
                }
                text += "<br>";
            }
            text += "</body></html>";
            System.out.println(text);
            return text;
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String[] getFriend(String pUsername) {
        try {
            String username = pUsername;
            int userId = this.getUserId(username);
            String sql = "SELECT * FROM Friend WHERE UserId1=\"" + userId + "\" OR UserId2=\"" + userId + "\"";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            ArrayList<String> friendsArrayList = new ArrayList<String>();
            while(result.next()) {
                if(result.getInt("UserId1") != userId) {
                    friendsArrayList.add(this.getUsername(result.getInt("UserId1")));
                } else {
                    friendsArrayList.add(this.getUsername(result.getInt("UserId2")));
                }
            }
            String[] friends = new String[friendsArrayList.size() + 1];
            System.out.println(friends.length);
            friends[0] = "getFriend";
            for(int i = 0; i < friends.length - 1; i++) {
                friends[i + 1] = friendsArrayList.get(i);
                System.out.println(friends[i+1]);
            }
            System.out.println("tesofrendo");
            return friends;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[1];
    }
}
