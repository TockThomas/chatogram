package Chatogram.Server;

import java.sql.*;

public class Datenbank {
    private String databaseURL;

    public Datenbank() {
        this. databaseURL = "jdbc:ucanaccess://C:/Users/thoma/Documents/Chatogram.accdb";
    }

    public void createUser(String pUsername, String pPassword) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection(databaseURL);
            String sql = "INSERT INTO User (Username, Password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pUsername);
            preparedStatement.setString(2, pPassword);
            preparedStatement.executeUpdate();

            sql = "SELECT * FROM User";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while(result.next()) {
                int id = result.getInt("ID");
                String username = result.getString("Username");
                String password = result.getString("Password");
                System.out.println("Account created: " + id + ", " + username);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
