package Chatogram.Client;

import javax.accessibility.AccessibleIcon;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class Interface extends JFrame {
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
    private JMenuBar menuBar;
    private JMenu menuOption;
    private JMenuItem menuItemOptionDeleteFriend;
    private JMenuItem menuItemOptionLogOff;
    private Control control;


    public Interface(){
        ImageIcon icon = new ImageIcon("src/Chatogram/Client/img/ChatogramIcon.png");
        this.control = new Control();
        this.setIconImage(icon.getImage());
        this.setContentPane(this.pnlMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.menuBar = new JMenuBar();
        this.menuOption = new JMenu("Option");
        this.menuBar.add(this.menuOption);
        this.menuItemOptionDeleteFriend = new JMenuItem("Delete Friend");
        this.menuItemOptionLogOff = new JMenuItem("Log off");
        this.menuOption.add(this.menuItemOptionDeleteFriend);
        this.menuOption.addSeparator();
        this.menuOption.add(this.menuItemOptionLogOff);
        this.setSize(320,440);
        this.setLocationRelativeTo(null);
        this.setTitle("Chatogram");
        this.setResizable(false);
        this.setVisible(true);


        this.btLoginLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] arr = control.login(tfLoginUsername.getText(), tfLoginPassword.getText());
                if(arr[1].equals("success")) {
                    startChatFrame(tfLoginUsername.getText());
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
                    String confirmation = control.register(tfRegisterUsername.getText(), tfRegisterPassword.getText());
                    if(confirmation.equals("success")) {
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

        this.btAddFriend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String friend = (String)JOptionPane.showInputDialog(null, "Enter username from your Friend", "Add Friend", JOptionPane.PLAIN_MESSAGE);
                if(control.addFriend(friend)){
                    listFriends.setListData(control.getFriendsList());
                    JOptionPane.showMessageDialog(null, "Friend added", "Add Friend", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Friend not found", "Add Friend", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.btSendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!tfMessage.getText().equals("")) {
                    control.writeMessage((String) listFriends.getSelectedValue(), tfMessage.getText());
                    tfMessage.setText("");
                    refreshChat();
                }
            }
        });

        this.tfMessage.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_ENTER && !tfMessage.getText().equals("")) {
                    control.writeMessage((String) listFriends.getSelectedValue(), tfMessage.getText());
                    tfMessage.setText("");
                    refreshChat();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        this.listFriends.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()) {
                    lbFriendName.setText((String)listFriends.getSelectedValue());
                    refreshChat();
                    btSendMessage.setEnabled(true);
                    tfMessage.setEnabled(true);
                    menuItemOptionDeleteFriend.setEnabled(true);
                }
            }
        });

        this.menuItemOptionDeleteFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.deleteFriend((String)listFriends.getSelectedValue());
                refreshFriends();
                lbChat.setText("");
                lbFriendName.setText("");
                tfMessage.setText("");
                btSendMessage.setEnabled(false);
                tfMessage.setEnabled(false);
                menuItemOptionDeleteFriend.setEnabled(false);
                listFriends.setSelectedIndex(-1);
            }
        });

        this.menuItemOptionLogOff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logOut();
            }
        });
    }

    private void startChatFrame(String pUsername) {
        this.setVisible(false);
        this.setSize(1280, 720);
        this.setLocationRelativeTo(null);
        this.cardlayout.show(pnlMain, "Chat");
        this.lbUsername.setText("Logged in as: " + pUsername);
        this.refreshFriends();
        this.setJMenuBar(menuBar);
        this.btSendMessage.setEnabled(false);
        this.tfMessage.setEnabled(false);
        this.menuItemOptionDeleteFriend.setEnabled(false);
        this.setVisible(true);
    }

    private void refreshFriends(){
        this.listFriends.setListData(this.control.getFriendsList());
    }

    private void refreshChat(){
        String text = this.control.getChat((String)this.listFriends.getSelectedValue());
        lbChat.setText(text);
    }

    private void logOut(){
        this.lbChat.setText("");
        this.listFriends.setSelectedIndex(-1);
        this.lbFriendName.setText("");
        this.tfLoginPassword.setText("");
        this.control.changeUser();
        this.setSize(320,440);
        this.setLocationRelativeTo(null);
        this.cardlayout.show(pnlMain, "Login");
        this.setVisible(true);
    }
}
