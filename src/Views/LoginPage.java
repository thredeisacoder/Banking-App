package Views;

import javax.swing.*;


import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LoginPage extends JFrame {
    private int width, height;
    private JPanel titlePanel;
    private JPanel formPanel;
    private JPanel btnPanel;

    private JButton loginBtn;
    private JTextField phoneInput;
    private JPasswordField passwordInput;

    private JButton registerBtn;

    public LoginPage() {
        super("Login Page");
        //phoneLabel = new JLabel("Phone Number: ");
        this.init();

    }
    public void init(){
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        // initial label, text field...
        titlePanel = new JPanel();

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        ImageIcon icon= new ImageIcon("./src/Resourses/img/bank.png");
        titleLabel.setIcon(icon);
        titlePanel.setBackground(Color.CYAN);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));  // Viền đen
        titlePanel.add(titleLabel);
        formPanel = new JPanel();
        formPanel.setLayout(new BorderLayout());

        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));

        btnPanel = new JPanel();

        formPanel.setLayout(new GridLayout(8, 1));
        JLabel phoneLabel = new JLabel("Phone Number");
        JLabel passwordLabel = new JLabel("Password: ");
        phoneInput = new JTextField();
        passwordInput = new JPasswordField();
        loginBtn = new JButton("Login");
        registerBtn =new JButton("register");
        registerBtn.setForeground(Color.BLUE);
        registerBtn.setFont(new Font("Arial", Font.ITALIC, 15));
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        formPanel.add(phoneLabel);
        formPanel.add(phoneInput);
        formPanel.add(passwordLabel);
        formPanel.add(passwordInput);
        btnPanel.add(loginBtn, BorderLayout.NORTH);
        formPanel.add(btnPanel);
        formPanel.add(registerBtn);
        loginBtn.addActionListener(e -> {
            String phone = phoneInput.getText();
            String password = String.valueOf(passwordInput.getPassword());
        });
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public JButton getLoginBtn() {
        return loginBtn;
    }

    public Map<String,String> getUserInput(){
        Map<String,String> userInput = new HashMap<>();
        userInput.put("phone",phoneInput.getText());
        userInput.put("password",String.valueOf(passwordInput.getPassword()));
        return userInput;
    }
    public JButton getRegisterBtn() {
        return registerBtn;
    }
}
