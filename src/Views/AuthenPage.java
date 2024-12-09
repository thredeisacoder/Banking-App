package Views;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class AuthenPage extends JFrame {
    JTextField phoneInput;
    JTextField otpInput;
    JButton sendOtpBtn;
    JButton submitBtn;
    JLabel timeLabel;

    public AuthenPage() {
        super("AuthenPage");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400,700);
        setLayout(new BorderLayout());
        Border emptyBorder= BorderFactory.createEmptyBorder(5,5,5,5);
        Border dashedBorder =BorderFactory.createDashedBorder(Color.BLACK,5,2);
        Border inputBorder = BorderFactory.createCompoundBorder(dashedBorder,emptyBorder) ;

        JLabel title = new JLabel("Authentification");
        title.setFont(new Font("Tahoma",Font.BOLD,30));
        JLabel phoneLabel = new JLabel("Phone Number");
        phoneInput = new JTextField();
        phoneInput.setBorder(inputBorder);
        phoneInput.setOpaque(false);
        JLabel otpLabel = new JLabel("OTP");
        otpInput = new JTextField();
        otpInput.setBorder(inputBorder);
        otpInput.setOpaque(false);
        sendOtpBtn = new JButton("Send OTP");
        sendOtpBtn.setBackground(Color.GREEN);
        submitBtn = new JButton("Submit");
        submitBtn.setBackground(new Color(122, 122, 229));
        submitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        timeLabel = new JLabel(" ");
        timeLabel.setFont(new Font("Tahoma",Font.BOLD,20));
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new BorderLayout());
        timeLabel.setVisible(true);
        timePanel.add(timeLabel, BorderLayout.EAST);
        timePanel.setBackground(new Color(198, 198, 254));


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10,1));
        panel.setBorder(BorderFactory.createEmptyBorder(150,10,10,10));
        panel.setBackground(new Color(198, 198, 254));

        JPanel phonePanel = new JPanel();
        phonePanel.setLayout(new BorderLayout());
        phonePanel.setBackground(new Color(198, 198, 254));
        phonePanel.add(phoneInput, BorderLayout.CENTER);
        phonePanel.add(sendOtpBtn, BorderLayout.EAST);

        panel.add(phoneLabel);
        panel.add(phonePanel);
        panel.add(timePanel);
        panel.add(otpLabel);
        panel.add(otpInput);
        panel.add(submitBtn);
        setVisible(true);
        add(title,BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }

    public JButton getSendOtpBtn() {
        return sendOtpBtn;
    }
    public JButton getSubmitBtn() {
        return submitBtn;
    }
    public JLabel getTimeLabel() {
        return timeLabel;
    }
    public JTextField getPhoneInput() {
        return phoneInput;
    }
    public JTextField getOtpInput() {
        return otpInput;
    }

}
