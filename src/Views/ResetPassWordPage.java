package Views;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ResetPassWordPage extends JFrame {
    private String phone;
    private JPasswordField password;
    private JPasswordField confirmPassWord;
    private JButton submitBtn;
    public ResetPassWordPage(String phone) {
        setTitle("Reset Password");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(400,700);
        this.phone = phone;


        Border dashBorder = BorderFactory.createDashedBorder(Color.BLACK,1,5,2,false);
        Border emptyBorder= BorderFactory.createEmptyBorder(5,5,5,5);
        Border inputBorder = BorderFactory.createCompoundBorder(dashBorder,emptyBorder);

        JLabel passLabel = new JLabel("Enter Password");
        password = new JPasswordField();
        password.setBorder(inputBorder);
        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmPassWord = new JPasswordField();
        confirmPassWord.setBorder(inputBorder);
        submitBtn = new JButton("Submit");
        submitBtn.setBackground(new Color(122, 122, 229));
        submitBtn.setFont(new Font("Tahoma",Font.BOLD,20));
        submitBtn.setForeground(new Color(198, 198, 254));


        JLabel emptyLabel = new JLabel(" ");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8,1));
        inputPanel.setBackground(new Color(198, 198, 254));;
        inputPanel.add(emptyLabel);
        inputPanel.add(passLabel);
        inputPanel.add(password);
        inputPanel.add(confirmLabel);
        inputPanel.add(confirmPassWord);
        inputPanel.setBorder(emptyBorder);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitBtn);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(122, 122, 229));
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(70,20,120,20),dashBorder));
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel,BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public JButton getSubmitBtn() {
        return submitBtn;
    }

    public JPasswordField getConfirmPassWord() {
        return confirmPassWord;
    }

    public JPasswordField getPassword() {
        return password;
    }
}
