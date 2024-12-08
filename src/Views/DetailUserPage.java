package Views;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DetailUserPage extends JFrame {

    private JLabel imgLabel;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField addressField;

    private JButton editBtn;

    private JButton turnBackBtn;

    private JButton saveBtn;
    private JButton changePasswordBtn;


    public DetailUserPage(){
        init();
    }

    public void init(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setUndecorated(true);
        setSize(400, 700);

        setLayout(new BorderLayout());

        JPanel imgPanel = new JPanel();
        imgPanel.setLayout(new BorderLayout());
        imgPanel.setBackground(new Color(122, 122, 229));

        try {
            BufferedImage myPicture = ImageIO.read(new File("./src/Resourses/img/profile.png"));
            Image scaledImage=myPicture.getScaledInstance(150,150,Image.SCALE_SMOOTH);
            imgLabel = new JLabel(new ImageIcon(scaledImage));
            imgPanel.add(imgLabel,BorderLayout.CENTER);
        }
        catch (IOException e){
            System.out.println(e);
        }

        Border dashBorder = BorderFactory.createDashedBorder(Color.BLACK,1,5,2,false);
        Border emptyBorder= BorderFactory.createEmptyBorder(5,5,5,5);
        Border inputBorder = BorderFactory.createCompoundBorder(dashBorder,emptyBorder);
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Tahoma",Font.BOLD,15));
        nameLabel.setForeground(new Color(122, 122, 229));
        nameField=new JTextField();
        nameField.setEditable(false);
        nameField.setOpaque(false);
        nameField.setBorder(inputBorder);

        JLabel phoneLabel = new JLabel("Phone");
        phoneLabel.setFont(new Font("Tahoma",Font.BOLD,15));
        phoneLabel.setForeground(new Color(122, 122, 229));
        phoneField= new JTextField();
        phoneField.setEditable(false);
        phoneField.setOpaque(false);
        phoneField.setBorder(inputBorder);
        JLabel emailLabel= new JLabel("Email");
        emailLabel.setFont(new Font("Tahoma",Font.BOLD,15));
        emailLabel.setForeground(new Color(122, 122, 229));
        emailField= new JTextField();
        emailField.setEditable(false);
        emailField.setOpaque(false);
        emailField.setBorder(inputBorder);
        JLabel addressLabel= new JLabel("Address");
        addressLabel.setFont(new Font("Tahoma",Font.BOLD,15));
        addressLabel.setForeground(new Color(122, 122, 229));
        addressField= new JTextField();
        addressField.setEditable(false);
        addressField.setOpaque(false);
        addressField.setBorder(inputBorder);

        Border btnBorder = BorderFactory.createCompoundBorder(dashBorder,BorderFactory.createEmptyBorder(10,10,10,10));


        editBtn = new JButton("Edit");
        editBtn.setFont(new Font("Tahoma",Font.BOLD,15));
        editBtn.setBackground(new Color(122, 122, 229));
        editBtn.setBorder(btnBorder);
        saveBtn = new JButton("Save");
        saveBtn.setFont(new Font("Tahoma",Font.BOLD,15));
        saveBtn.setBackground(new Color(122, 122, 229));
        saveBtn.setBorder(btnBorder);
        saveBtn.setVisible(false);

        changePasswordBtn = new JButton("Change Password");
        changePasswordBtn.setFont(new Font("Tahoma",Font.BOLD,15));
        changePasswordBtn.setBackground(new Color(122, 122, 229));
        changePasswordBtn.setBorder(btnBorder);
        changePasswordBtn.setVisible(false);
        turnBackBtn= new JButton("Back");
        turnBackBtn.setBackground(new Color(122, 122, 229));
        turnBackBtn.setFont(new Font("Tahoma",Font.BOLD,15));
        turnBackBtn.setBorder(btnBorder);

        JPanel inforPangel= new JPanel();
        inforPangel.setLayout(new GridLayout(8,2));
        inforPangel.setBackground(new Color(198, 198, 254));
        inforPangel.setBorder(emptyBorder);
        inforPangel.add(nameLabel);
        inforPangel.add(nameField);
        inforPangel.add(phoneLabel);
        inforPangel.add(phoneField);
        inforPangel.add(emailLabel);
        inforPangel.add(emailField);
        inforPangel.add(addressLabel);
        inforPangel.add(addressField);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(2,2));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(80,20,80,20));
        btnPanel.setBackground(new Color(198, 198, 254));
        btnPanel.add(editBtn);
        btnPanel.add(turnBackBtn);
        btnPanel.add(saveBtn);
        btnPanel.add(changePasswordBtn);
        add(imgPanel,BorderLayout.NORTH);
        add(inforPangel,BorderLayout.CENTER);
        add(btnPanel,BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JTextField getPhoneField() {
        return phoneField;
    }

    public JTextField getAddressField() {
        return addressField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JButton getEditBtn() {
        return editBtn;
    }
    public JButton getSaveBtn(){
        return saveBtn;
    }
    public JButton getTurnBackBtn(){
        return turnBackBtn;
    }

    public JButton getChangePasswordBtn() {
        return changePasswordBtn;
    }
}