package Views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HomePage extends JFrame {

	private JButton logoutBtn;
	private JButton notificationBtn;

	public JLabel balanceLabel;

	public JLabel accNoLabel;

	public JLabel nameLabel;

	public JButton detailBtn;

	public JButton transferBtn;


	public HomePage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		this.setUndecorated(true);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3,1));


		JPanel contentPanel = new JPanel();

		setSize(400, 700);
		contentPanel.setLayout(new GridLayout(5,1));
		contentPanel.setBackground(new Color(198, 198, 254));
		contentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		logoutBtn = new JButton();
		logoutBtn.setBackground(new Color(122, 122, 229));
		logoutBtn.setToolTipText("logout");

		try {
            BufferedImage myPicture = ImageIO.read(new File("./src/Resourses/img/logout.png"));
            Image scaledImage=myPicture.getScaledInstance(30,30,Image.SCALE_SMOOTH);
            JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
            logoutBtn.add(picLabel);
        }
        catch (IOException e){
            System.out.println(e);
        }
		notificationBtn= new JButton();
		notificationBtn.setBackground(new Color(122,122,229));
		notificationBtn.setToolTipText("see your notification");
		try {
            BufferedImage myPicture = ImageIO.read(new File("./src/Resourses/img/notification.png"));
            Image scaledImage=myPicture.getScaledInstance(30,30,Image.SCALE_SMOOTH);
            JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
            notificationBtn.add(picLabel);
        }
        catch (IOException e){
            System.out.println(e);
        }

		detailBtn =new JButton();
		detailBtn.setBackground(new Color(122,122,229));
		detailBtn.setToolTipText("show your information");
		try {
            BufferedImage myPicture = ImageIO.read(new File("./src/Resourses/img/profile-user.png"));
            Image scaledImage=myPicture.getScaledInstance(30,30	,Image.SCALE_SMOOTH);
            JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
            detailBtn.add(picLabel);
        }
        catch (IOException e){
            System.out.println(e);
        }

		JPanel headPanel = new JPanel();
		headPanel.setLayout(new BorderLayout());
		headPanel.setBackground(new Color(122,122,229));

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(detailBtn,BorderLayout.WEST);
		menuBar.add(notificationBtn,BorderLayout.CENTER);
		menuBar.add(logoutBtn, BorderLayout.EAST);
		headPanel.add(menuBar,BorderLayout.EAST);


		nameLabel= new JLabel();
		nameLabel.setFont(new Font("Tahoma", Font.BOLD,20));
		accNoLabel= new JLabel();
		accNoLabel.setFont(new Font("Tahoma", Font.BOLD,15));
		balanceLabel = new JLabel();
		balanceLabel.setFont(new Font("Tahoma", Font.BOLD,20));

		JPanel btnPanel=  new JPanel();
		btnPanel.setLayout(new GridLayout(2,3));
		btnPanel.setBorder(BorderFactory.createEmptyBorder());
		transferBtn = new JButton("Transfer");
		transferBtn.setBackground(new Color(122,122,229));
		btnPanel.add(transferBtn);
		contentPanel.add(nameLabel);
		contentPanel.add(accNoLabel);
		contentPanel.add(balanceLabel);
		contentPanel.add(btnPanel);

		contentPanel.add(headPanel, BorderLayout.NORTH);
		add(headPanel, BorderLayout.NORTH);
		mainPanel.add(contentPanel);
		mainPanel.add(btnPanel);
		add(mainPanel, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public JButton getLogoutBtn() {
		return logoutBtn;
	}

	public JLabel getBalanceLabel(){
		return balanceLabel;
	}
	public JLabel getAccNoLabel(){
		return accNoLabel;
	}
	public JLabel getNameLabel(){
		return nameLabel;
	}

	public JButton getDetailBtn() {
		return detailBtn;
	}
}
