package Views;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Font;

public class HomePage extends JFrame {

	private JPanel contentPanel;
	private JButton logoutBtn;

	public HomePage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		contentPanel = new JPanel();
		setSize(300, 500);
		contentPanel.setLayout(new BorderLayout(0, 0));
		logoutBtn = new JButton("Logout");

		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Home");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 45));
		panel.add(lblNewLabel, BorderLayout.CENTER);
		add(contentPanel, BorderLayout.CENTER);
		add(logoutBtn, BorderLayout.SOUTH);
	}

	public JButton getLogoutBtn() {
		return logoutBtn;
	}

}
