package Chatogram.Client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatogramClient extends JFrame {
    private CardLayout cardlayout = (CardLayout)this.pnlMain.getLayout();
    private JPanel pnlMain;
    private JPanel pnlLogin;
    private JPanel pnlRegister;
    private JTextField tfLoginUsername;
    private JTextField tfRegisterUsername;
    private JPasswordField tfLoginPassword;
    private JPasswordField tfRegisterPassword;
    private JPasswordField tfRegisterConfirmPassword;
    private JButton btLoginRegister;
    private JButton btLoginLogin;
    private JButton btRegisterReturn;
    private JButton btRegisterRegister;
    private JPanel pnlChat;
    private JButton btSendMessage;
    private JTextField tfMessage;
    private JScrollPane spChat;
    private JLabel lbFriendName;
    private JScrollPane spPeople;
    private JButton btAddFriend;
    private JLabel lbUsername;
    private JList listFriends;
    private JLabel lbChat;
    private ClientSocket clientSocket;
    private User user;


    public ChatogramClient(){
        ImageIcon icon = new ImageIcon("src/Chatogram/Client/img/ChatogramIcon.png");
        this.setIconImage(icon.getImage());
        this.setContentPane(this.pnlMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(320,440);
        this.setLocationRelativeTo(null);
        this.setTitle("Chatogram");
        this.setResizable(false);
        this.setVisible(true);
        this.clientSocket = new ClientSocket("localhost", 4999);

        this.btLoginLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] message = {"login", tfLoginUsername.getText(), tfLoginPassword.getText()};
                String[] response = clientSocket.sendMessage(message);
                if(response[1].equals("success")) {
                    startChatFrame(message[1]);
                } else {
                    JOptionPane.showMessageDialog(null, "Give valid login credentials!", "Login failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        this.btLoginRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardlayout.show(pnlMain, "Register");
            }
        });
        this.btRegisterReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardlayout.show(pnlMain, "Login");
            }
        });
        this.btRegisterRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!tfRegisterUsername.getText().equals("") && !tfRegisterPassword.getText().equals("") && tfRegisterPassword.getText().equals(tfRegisterConfirmPassword.getText())) {
                    String[] message = {"register", tfRegisterUsername.getText(), tfRegisterPassword.getText()};
                    String[] response = clientSocket.sendMessage(message);
                    if(response[1].equals("success")) {
                        JOptionPane.showMessageDialog(null, "Account has been created!", "Register success", JOptionPane.PLAIN_MESSAGE);
                        cardlayout.show(pnlMain, "Login");
                    } else {
                        JOptionPane.showMessageDialog(null, "Username is taken", "Register failed", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    if(tfRegisterUsername.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "Username is missing!", "Register failed", JOptionPane.ERROR_MESSAGE);
                    } else if(tfRegisterPassword.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "Password is missing!", "Register failed", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Passwords are not the same!", "Register failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        this.btSendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chat chat = new Chat(user.getUsername(), user.getFriend(listFriends.getSelectedIndex()));
                chat.writeMessage(tfMessage.getText());
                refreshChat();
            }
        });

        this.listFriends.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                refreshChat();
            }
        });
    }

    private void startChatFrame(String pUsername) {
        this.setVisible(false);
        this.setSize(1280, 720);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.cardlayout.show(pnlMain, "Chat");
        this.user = new User(pUsername);
        this.getFriends();
        this.lbUsername.setText(pUsername);
        this.listFriends.setListData(this.user.getFriends());
        this.setVisible(true);
    }

    private void refreshChat(){
        Chat chat = new Chat(user.getUsername(), (String) listFriends.getSelectedValue());
        String[] message = {"getChat", this.user.getUsername(), (String)this.listFriends.getSelectedValue()};
        String[] response = clientSocket.sendMessage(message);
        lbChat.setText(response[1]);
    }

    private void getFriends(){
        String[] message = {"getFriend", this.user.getUsername()};
        System.out.println("testfriend");
        String[] response = clientSocket.sendMessage(message);
        this.user.setFriend(response);
    }
}
