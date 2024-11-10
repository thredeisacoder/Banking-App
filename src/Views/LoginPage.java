package Views;

import Models.Account;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {
    private Account account;
    private int width, height;
    private JLabel phoneLabel;
    private JLabel passwordLabel;
    private JTextField phoneInput;
    private JPasswordField passwordInput;
    private JButton loginBtn;
    public LoginPage() {
        super("Login Page");
        //phoneLabel = new JLabel("Phone Number: ");
        this.init();
    }
    public void init(){
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        // initial label, text field...
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        phoneLabel = new JLabel("Phone Number: ");
        passwordLabel = new JLabel("Password: ");
        phoneInput = new JTextField();
        passwordInput = new JPasswordField();
        loginBtn = new JButton("Login");
        panel.add(phoneLabel);
        panel.add(phoneInput);
        panel.add(passwordLabel);
        panel.add(passwordInput);
        panel.add(loginBtn);

        loginBtn.addActionListener(e -> {
            // change page
        });

        add(panel, BorderLayout.CENTER);
        this.setVisible(true);
    }
}
