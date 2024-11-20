package Views;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends JFrame {
    private JTextField addressInput;
    private JPasswordField passwordInput;
    private JTextField emailInput;
    private JTextField nameInput;
    private JTextField phoneInput;
    private JButton submitBtn;
    public RegisterPage() {
        init();
    }

    public void init() {
        setTitle("Register Page");
        setLayout(new BorderLayout());
        setSize(300, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));

        JLabel addressLabel = new JLabel("Address:");
        addressInput = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordInput = new JPasswordField();

        JLabel emailLabel = new JLabel("Email:");
        emailInput = new JTextField();

        JLabel nameLabel = new JLabel("Name:");
        nameInput = new JTextField();


        JLabel phoneLabel = new JLabel("Phone:");
        phoneInput = new JTextField();

        submitBtn = new JButton("Register");

        panel.add(nameLabel);
        panel.add(nameInput);
        panel.add(phoneLabel);
        panel.add(phoneInput);
        panel.add(emailLabel);
        panel.add(emailInput);
        panel.add(addressLabel);
        panel.add(addressInput);
        panel.add(passwordLabel);
        panel.add(passwordInput);


        add(panel, BorderLayout.CENTER);
        add(submitBtn, BorderLayout.SOUTH);
        setVisible(true);
    }
    public JButton getSubmitBtn() {
        return submitBtn;
    }
    public Map<String, String> getUserInput() {
        Map<String, String> userInput = new HashMap<>();
        userInput.put("address", addressInput.getText());
        userInput.put("password", String.valueOf(passwordInput.getPassword()));
        userInput.put("email", emailInput.getText());
        userInput.put("name",nameInput.getText());
        userInput.put("phone", phoneInput.getText());
        return userInput;
    }
}