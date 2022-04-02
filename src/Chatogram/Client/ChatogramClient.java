package Chatogram.Client;

import javax.swing.*;
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
    private ClientSocket clientSocket;


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
                for(int i = 0; i < response.length; i++) {
                    System.out.println(response[i]);
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
                    JOptionPane.showMessageDialog(null, "Account has been created!", "Register success", JOptionPane.PLAIN_MESSAGE);
                    cardlayout.show(pnlMain, "Login");
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
    }
}
