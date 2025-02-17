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
public class LoginPage extends JFrame {

    private JButton loginBtn;
    private JTextField phoneInput;
    private JPasswordField passwordInput;
    private JButton registerBtn;
    private JButton resetPassBtn;
    JDialog dialog ;

    public LoginPage() {
        super("Login Page");
        this.init();

    }
    public void init()  {
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        this.setUndecorated(true);
        JPanel titleBar = new JPanel();
        titleBar.setLayout(new BorderLayout());
        titleBar.setBackground(Color.WHITE);
        JButton closeButton = new JButton("X");
        closeButton.setBackground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(( e) -> System.exit(0));
//        titleBar.add(closeButton, BorderLayout.EAST);

        this.setSize(400, 700);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        // initial label, text field...
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(titleBar,BorderLayout.NORTH);
        titlePanel.setBackground(Color.white);

        try {
            BufferedImage myPicture = ImageIO.read(new File("./src/Resourses/img/bank-bg.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            picLabel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
            titlePanel.add(picLabel,BorderLayout.CENTER);
        }
        catch (IOException e){
            System.out.println(e);
        }
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(new GridLayout(4,1));
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.white);
        btnPanel.setLayout(new GridLayout(4,1));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(0,100,150,100));


        JLabel phoneLabel = new JLabel("Phone Number");
        phoneLabel.setFont(new Font("Tahoma",Font.BOLD,15));
        phoneLabel.setForeground(new Color(122, 122, 229));
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setForeground(new Color(122, 122, 229));
        passwordLabel.setFont(new Font("Tahoma",Font.BOLD,15));
        phoneInput = new JTextField();
        passwordInput = new JPasswordField();

        loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Tahoma",Font.BOLD,20));
        loginBtn.setForeground(new Color(198, 198, 254));
        loginBtn.setBackground(new Color(122, 122, 229));
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));


        registerBtn =new JButton("Register");
        registerBtn.setForeground(Color.BLUE);
        registerBtn.setBackground(Color.white);
        registerBtn.setFont(new Font("Arial", Font.ITALIC, 15));
        registerBtn.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        resetPassBtn = new JButton("Forgot Password");
        resetPassBtn.setForeground(Color.BLUE);
        resetPassBtn.setBackground(Color.white);
        resetPassBtn.setFont(new Font("Arial", Font.ITALIC, 15));
        resetPassBtn.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        resetPassBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel emptyLabel = new JLabel();
        emptyLabel.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        btnPanel.add(loginBtn);
        btnPanel.add(emptyLabel);
        btnPanel.add(registerBtn);
        btnPanel.add(resetPassBtn);

        formPanel.add(phoneLabel);
        formPanel.add(phoneInput);
        formPanel.add(passwordLabel);
        formPanel.add(passwordInput);
        loginBtn.addActionListener(e -> {
            String phone = phoneInput.getText();
            String password = String.valueOf(passwordInput.getPassword());
        });


        // loading dialog
        dialog = new JDialog();
        dialog.setTitle("Loading...");
        dialog.setSize(200, 100);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        //
        JLabel label = new JLabel("Loading...");
        label.setIcon(new ImageIcon(String.valueOf(UIManager.getIcon("OptionPane.informationIcon"))));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        //
        dialog.add(label);

        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

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

    public JDialog getDialog() {
        return dialog;
    }

    public JButton getResetPassBtn() {
        return resetPassBtn;
    }
}
