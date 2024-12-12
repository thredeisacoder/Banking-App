package Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Models.Notification;

public class notificationPage extends JFrame {

	private JPanel contentPane;
	private JTable notificationTable;
	private JScrollPane scrollPane;
	private JLabel titleLabel;
	private JButton refreshButton;
	private JButton markReadButton;
	private JButton deleteButton;
	private JButton backButton;
	private JPanel userInfoPanel;
	private JLabel nameLabel, accountLabel, balanceLabel;
	private JLabel nameValue, accountValue, balanceValue;
	private JButton allNotificationsButton;
	private JButton debitNotificationsButton;
	private JButton creditNotificationsButton;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					notificationPage frame = new notificationPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public notificationPage() {
		setTitle("Thông báo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 700);
		setLocationRelativeTo(null);
		// Thay đổi contentPane layout thành BorderLayout
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(10, 10)); // Thêm gap 10px
		setContentPane(contentPane);
		// Panel phía trên chứa thông tin user và tiêu đề
		JPanel topPanel = new JPanel(new BorderLayout(0, 10));
		// tiêu đề 
		titleLabel = new JLabel("Danh sách thông báo");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		// Create user info panel with GridBagLayout for better control
		// Điều chỉnh userInfoPanel
		userInfoPanel = new JPanel(new GridBagLayout());
		userInfoPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
		userInfoPanel.setBackground(new Color(204, 204, 255)); // Light purple background
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 2, 2, 10); // Small padding between components

		// Create and style labels
		nameLabel = new JLabel("Tên:");
		nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
		nameValue = new JLabel("");
		nameValue.setFont(new Font("Arial", Font.PLAIN, 14));

		accountLabel = new JLabel("Số tài khoản:");
		accountLabel.setFont(new Font("Arial", Font.BOLD, 14));
		accountValue = new JLabel("");
		accountValue.setFont(new Font("Arial", Font.PLAIN, 14));

		balanceLabel = new JLabel("Số dư:");
		balanceLabel.setFont(new Font("Arial", Font.BOLD, 14));
		balanceValue = new JLabel("");
		balanceValue.setFont(new Font("Arial", Font.PLAIN, 14));

		// Style cho labels
		Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
		Font valueFont = new Font("Segoe UI", Font.PLAIN, 14);
		
		nameLabel.setFont(labelFont);
		accountLabel.setFont(labelFont);
		balanceLabel.setFont(labelFont);
		
		nameValue.setFont(valueFont);
		accountValue.setFont(valueFont);
		balanceValue.setFont(valueFont);
		balanceValue.setForeground(new Color(0, 100, 0)); // Dark green for balance

		// Add components with GridBagLayout
		// Row 1
		gbc.gridx = 0;
		gbc.gridy = 0;
		userInfoPanel.add(nameLabel, gbc);

		gbc.gridx = 1;
		userInfoPanel.add(nameValue, gbc);

		// Row 2
		gbc.gridx = 0;
		gbc.gridy = 1;
		userInfoPanel.add(accountLabel, gbc);

		gbc.gridx = 1;
		userInfoPanel.add(accountValue, gbc);

		// Row 3
		gbc.gridx = 0;
		gbc.gridy = 2;
		userInfoPanel.add(balanceLabel, gbc);

		gbc.gridx = 1;
		userInfoPanel.add(balanceValue, gbc);

		// Create a wrapper panel
		JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		wrapperPanel.add(userInfoPanel);
		// Thêm panel mới cho các nút filter
		JPanel filterPanel = new JPanel(new GridLayout(3, 1, 8, 8));
		filterPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		allNotificationsButton = new JButton("Tất cả thông báo");
		debitNotificationsButton = new JButton("Thông báo trừ tiền");
		creditNotificationsButton = new JButton("Thông báo cộng tiền");

		Dimension buttonSize = new Dimension(380, 30);
		allNotificationsButton.setPreferredSize(buttonSize);
		debitNotificationsButton.setPreferredSize(buttonSize);
		creditNotificationsButton.setPreferredSize(buttonSize);

		// Custom button style
		Color primaryColor = new Color(153, 153, 255); // Purple color for buttons
		Color textColor = Color.WHITE;
		
		// styleButton(allNotificationsButton, primaryColor, textColor);
		// styleButton(debitNotificationsButton, primaryColor, textColor);
		// styleButton(creditNotificationsButton, primaryColor, textColor);
		// styleButton(backButton, primaryColor, textColor);
		// styleButton(refreshButton, primaryColor, textColor);
		// styleButton(markReadButton, primaryColor, textColor);
		// styleButton(deleteButton, primaryColor, textColor);

		filterPanel.add(allNotificationsButton);
		filterPanel.add(debitNotificationsButton);
		filterPanel.add(creditNotificationsButton);
		// Thêm filterPanel vào topPanel
		topPanel.add(filterPanel, BorderLayout.SOUTH);
		// Add panels to the content pane
		topPanel.add(wrapperPanel, BorderLayout.NORTH);
		topPanel.add(titleLabel, BorderLayout.CENTER);

		contentPane.add(topPanel, BorderLayout.NORTH);
		// tạo bảng thông báo
		String[] columnNames = { "thời gian", "loại thông báo", "nội dung", "Trạng thái","số tiền" };
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		notificationTable = new JTable(tableModel);
		notificationTable.setRowHeight(30);
		notificationTable.getTableHeader().setReorderingAllowed(false);
		notificationTable.setFillsViewportHeight(true);

		notificationTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		notificationTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // thời gian
		notificationTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // loại thông báo
		notificationTable.getColumnModel().getColumn(2).setPreferredWidth(120); // nội dung
		notificationTable.getColumnModel().getColumn(3).setPreferredWidth(60);  // Trạng thái
		notificationTable.getColumnModel().getColumn(4).setPreferredWidth(60);  // số tiền

		// tạo scroll pane cho bảng
		scrollPane = new JScrollPane(notificationTable);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(380, 400));
		contentPane.add(scrollPane, BorderLayout.CENTER);
		// panel chứa các nút điều khiển
		JPanel controlPanel = new JPanel(new GridLayout(4, 1, 8, 8));
		controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		refreshButton = new JButton("làm mới");
		markReadButton = new JButton("Đánh dấu đã đọc");
		deleteButton = new JButton("Xóa thông báo");
		backButton = new JButton("Quay lại");
		backButton.setPreferredSize(buttonSize);
		refreshButton.setPreferredSize(buttonSize);
		markReadButton.setPreferredSize(buttonSize);
		deleteButton.setPreferredSize(buttonSize);
		controlPanel.add(backButton);
		controlPanel.add(refreshButton);
		controlPanel.add(markReadButton);
		controlPanel.add(deleteButton);

		contentPane.add(controlPanel, BorderLayout.SOUTH);
		// load dữ liệu vào trong bảng
		// Thiết lập kích thước tối thiểu cho frame
		setMinimumSize(new Dimension(400, 700));
		setPreferredSize(new Dimension(400, 700));

		// Style cho bảng
		notificationTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		notificationTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
		notificationTable.getTableHeader().setBackground(new Color(153, 153, 255));
		notificationTable.getTableHeader().setForeground(Color.WHITE);
		notificationTable.setRowHeight(35);
		notificationTable.setShowGrid(true);
		notificationTable.setGridColor(new Color(230, 230, 230));

		// Style cho control buttons
		Color dangerColor = new Color(217, 83, 79); // Bootstrap danger red
		Color successColor = new Color(92, 184, 92); // Bootstrap success green
		Color infoColor = new Color(91, 192, 222); // Bootstrap info blue
		styleButton(allNotificationsButton, primaryColor, textColor);
		styleButton(debitNotificationsButton, primaryColor, textColor);
		styleButton(creditNotificationsButton, primaryColor, textColor);
		styleButton(backButton, primaryColor, textColor);
		styleButton(refreshButton, primaryColor, textColor);
		styleButton(markReadButton, primaryColor, textColor);
		styleButton(deleteButton, primaryColor, textColor);
		styleButton(backButton, primaryColor, textColor);
		styleButton(refreshButton, primaryColor, textColor);
		styleButton(markReadButton, successColor, textColor);
		styleButton(deleteButton, dangerColor, textColor);

		// Add shadow border to main panels
		addPanelShadow(userInfoPanel);
		addPanelShadow(filterPanel);
		addPanelShadow(scrollPane);
		addPanelShadow(controlPanel);

		// Thay đổi màu nền chính của contentPane
		contentPane.setBackground(new Color(230, 230, 255)); // Very light purple
	}

	// Helper method để style các buttons
	private void styleButton(JButton button, Color bgColor, Color fgColor) {
		button.setFont(new Font("Segoe UI", Font.BOLD, 13));
		button.setBackground(bgColor);
		button.setForeground(fgColor);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setOpaque(true);
		
		// Thêm hover effect
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(darken(bgColor));
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(bgColor);
			}
		});
	}

	// Helper method để tạo màu tối hơn cho hover effect
	private Color darken(Color color) {
		float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
		return Color.getHSBColor(hsb[0], hsb[1], Math.max(0, hsb[2] - 0.05f));
	}

	// Helper method để thêm shadow border cho panels
	private void addPanelShadow(JComponent component) {
		component.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(new Color(220, 220, 220)),
			BorderFactory.createEmptyBorder(5, 5, 5, 5)
		));
	}

	// phương thức thêm dữ liệu vào bảng
	public void addNotification(String time, String type, String content, String status, double amount) {
		DefaultTableModel model = (DefaultTableModel) notificationTable.getModel();
		model.addRow(new Object[] { time, type, content, status, String.format("%,.0f", amount) });
	}
	// nhưng phương thức get
	// public void setContentPane(JPanel contentPane) {
	// 	this.contentPane = contentPane;
	// }
	public JLabel getNameValue() {
		return nameValue;
	}
	public JLabel getAccountValue() {
		return accountValue;
	}
	public JLabel getBalanceValue() {
		return balanceValue;
	}
	public JButton getBackButton() {
		return backButton;
	}
	public JTable getNotificationTable() {
		return notificationTable;
	}

	public JButton getRefeshButton() {
		return refreshButton;
	}

	public JButton getMarkReadButton() {
		return markReadButton;
	}

	public JButton getDeleteButton() {
		return deleteButton;
	}
	
	// Thêm các getter cho các nút mới
	public JButton getAllNotificationsButton() {
		return allNotificationsButton;
	}

	public JButton getDebitNotificationsButton() {
		return debitNotificationsButton;
	}

	public JButton getCreditNotificationsButton() {
		return creditNotificationsButton;
	}
}
