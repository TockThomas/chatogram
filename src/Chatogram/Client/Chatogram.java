package Chatogram.Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class Chatogram extends JFrame {
    private JPanel pnlMain;
    private JPanel pnlLogin;
    private JTextField tfLoginUsername;
    private JPasswordField tfLoginPassword;
    private JButton btLoginRegister;
    private JButton btLoginLogin;
    private JPanel pnlRegister;
    private JTextField tfRegisterUsername;
    private JPasswordField tfRegisterPassword;
    private JPasswordField tfRegisterConfirmPassword;
    private JButton btRegisterReturn;
    private JButton btRegisterRegister;
    private JLabel lbLoginMessage;
    private JLabel lbRegisterMessage;
    private CardLayout cardlayout = (CardLayout)pnlMain.getLayout();

    public Chatogram(){
        /*this.setContentPane(new Chatogram().pnlMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(320,440);
        this.setLocationRelativeTo(null);
        this.setTitle("Chatogram");
        //frame.setIconImage(icon.getImage());
        this.setVisible(true);*/
        btLoginRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardlayout.show(pnlMain, "Register");
                lbLoginMessage.setText("");
            }
        });
        btRegisterReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardlayout.show(pnlMain, "Login");
            }
        });
        btRegisterRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!tfRegisterUsername.getText().equals("") && !tfRegisterPassword.getText().equals("") && tfRegisterPassword.getText().equals(tfRegisterConfirmPassword.getText())) {
                    lbRegisterMessage.setText("");
                    lbLoginMessage.setText("Account has been created!");
                    cardlayout.show(pnlMain, "Login");
                } else {
                    if(!tfRegisterUsername.getText().equals("")){
                        lbRegisterMessage.setText("Username is missing!");
                    } else if(!tfRegisterPassword.equals("")){
                        lbRegisterMessage.setText("Password is missing!");
                    } else {
                        lbRegisterMessage.setText("Password are not same!");
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new Chatogram();
        try {
            URL resource = frame.getClass().getResource("test.jpg");
            BufferedImage image = ImageIO.read(new FileInputStream("src/Chatogram/Client/img/ChatogramIcon.png"));
            frame.setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setContentPane(new Chatogram().pnlMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(320,440);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Chatogram");
        //frame.setIconImage(icon.getImage());
        frame.setVisible(true);
    }

}
