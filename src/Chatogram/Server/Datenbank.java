package Chatogram.Server;

import java.sql.*;

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
}
