package Views;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends JFrame {
    private JButton turnBackBtn;
    private JTextField addressInput;
    private JPasswordField passwordInput;
    private JComboBox<String> bankNameInput;
    private JTextField emailInput;
    private JTextField nameInput;
    private JTextField phoneInput;
    private JButton submitBtn;

    private JPasswordField confirmPasswordInput;

    public RegisterPage() {
        init();
    }

    public void init() {
        setTitle("Register Page");

        setLayout(new BorderLayout());
        setSize(400, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setUndecorated(true);

        JPanel headPanel= new JPanel();
        headPanel.setBackground(new Color(198, 198, 254));
        headPanel.setLayout(new BorderLayout());
        turnBackBtn= new JButton();
        turnBackBtn.setBackground(new Color(122, 122, 229));
        turnBackBtn.setToolTipText("back to login");
        turnBackBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        headPanel.add(turnBackBtn,BorderLayout.WEST);
        JPanel panel = new JPanel();
        //panel.setBackground(new Color(198, 198, 254));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        panel.setLayout(new GridLayout(14, 1));
        //JLabel titleLabel = new JLabel("");
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setForeground(new Color(122, 122, 229));
        addressLabel.setFont(new Font("Tahoma",Font.BOLD,15));
        Border emptyBorder= BorderFactory.createEmptyBorder(5,5,5,5);
        Border dashedBorder =BorderFactory.createDashedBorder(Color.BLACK,5,2);
        Border inputBorder = BorderFactory.createCompoundBorder(dashedBorder,emptyBorder) ;

        addressInput = new JTextField();
        addressInput.setOpaque(false);
        addressInput.setBorder(inputBorder);

        JLabel bankNameLabel = new JLabel("Choose bank:");
        bankNameLabel.setForeground(new Color(122, 122, 229));
        bankNameLabel.setFont(new Font("Tahoma",Font.BOLD,15));
        bankNameInput = new JComboBox<>();
        bankNameInput.setOpaque(false);
        bankNameInput.setBorder(inputBorder);


        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(new Color(122, 122, 229));
        passwordLabel.setFont(new Font("Tahoma",Font.BOLD,15));
        passwordInput = new JPasswordField();
        passwordInput.setOpaque(false);
        passwordInput.setBorder(inputBorder);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Tahoma",Font.BOLD,15));
        emailLabel.setForeground(new Color(122, 122, 229));
        emailInput = new JTextField();
        emailInput.setOpaque(false);
        emailInput.setBorder(inputBorder);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(new Color(122, 122, 229));
        nameLabel.setFont(new Font("Tahoma",Font.BOLD,15));
        nameInput = new JTextField();
        nameInput.setOpaque(false);
        nameInput.setBorder(inputBorder);


        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setForeground(new Color(122, 122, 229));
        phoneLabel.setFont(new Font("Tahoma",Font.BOLD,15));
        phoneInput = new JTextField();
        phoneInput.setOpaque(false);
        phoneInput.setBorder(inputBorder);

        JLabel confirmPassLabel = new JLabel("Confirm Password");
        confirmPassLabel.setForeground(new Color(122, 122, 229));
        confirmPassLabel.setFont(new Font("Tahoma",Font.BOLD,15));
        confirmPasswordInput = new JPasswordField();
        confirmPasswordInput.setOpaque(false);
        confirmPasswordInput.setBorder(inputBorder);

        submitBtn = new JButton("Register");
        submitBtn.setFont(new Font("Tahoma",Font.BOLD,20));
        submitBtn.setBackground(new Color(122, 122, 229));
        submitBtn.setForeground(new Color(198, 198, 254));
        submitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        try {
            BufferedImage myPicture = ImageIO.read(new File("./src/Resourses/img/back-arrow.png"));
            Image scaledImage=myPicture.getScaledInstance(20,20,Image.SCALE_SMOOTH);
            JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
            turnBackBtn.add(picLabel);
        }
        catch (IOException e){
            System.out.println(e);
        }
        panel.add(nameLabel);
        panel.add(nameInput);
        panel.add(phoneLabel);
        panel.add(phoneInput);
        panel.add(bankNameLabel);
        panel.add(bankNameInput);
        panel.add(emailLabel);
        panel.add(emailInput);
        panel.add(addressLabel);
        panel.add(addressInput);
        panel.add(passwordLabel);
        panel.add(passwordInput);
        panel.add(confirmPassLabel);
        panel.add(confirmPasswordInput);


        JPanel submitPanel= new JPanel();
        submitPanel.setBackground(new Color(198, 198, 254));
        submitPanel.setBorder(BorderFactory.createEmptyBorder(40,10,40,10));
        submitPanel.add(submitBtn);
        add(headPanel,BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(submitPanel,BorderLayout.SOUTH);
        setVisible(true);
    }
    public JButton getSubmitBtn() {
        return submitBtn;
    }
    public JButton getTurnBackBtn(){
        return turnBackBtn;
    }

    public JComboBox<String> getBankNameInput() {
        return bankNameInput;
    }

    public Map<String, String> getUserInput() {
        Map<String, String> userInput = new HashMap<>();
        userInput.put("name",nameInput.getText());
        userInput.put("address", addressInput.getText());
        userInput.put("bankName",bankNameInput.getSelectedItem().toString());
        userInput.put("password", String.valueOf(passwordInput.getPassword()));
        userInput.put("email", emailInput.getText());
        userInput.put("phone", phoneInput.getText());
        userInput.put("confirmPass",String.valueOf(confirmPasswordInput.getPassword()));
        return userInput;
    }
}