package Views;

import javax.swing.*;
import java.awt.*;
public class NotificationDetailPage extends JFrame {
    private JLabel dateLabel;
    private JLabel typeLabel;
    private JLabel statusLabel;
    private JLabel messageLabel;
    private JLabel amountLabel;
    private JLabel dateValueLabel;
    private JLabel typeValueLabel;
    private JTextArea messageValueArea;
    private JLabel amountValueLabel;
    private JLabel statusValueLabel;
    private JButton backButton;

    public NotificationDetailPage(){
        // Khởi tạo tất cả components trước
        dateLabel = new JLabel("Ngày:");
        typeLabel = new JLabel("Loại:");
        messageLabel = new JLabel("Nội dung:");
        amountLabel = new JLabel("Số tiền:");
        statusLabel = new JLabel("Trạng thái:");
        
        dateValueLabel = new JLabel("");
        typeValueLabel = new JLabel("");
        messageValueArea = new JTextArea();
        messageValueArea.setLineWrap(true);
        messageValueArea.setWrapStyleWord(true);
        messageValueArea.setEditable(false);
        messageValueArea.setBackground(new Color(236, 240, 241));
        messageValueArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        
        JScrollPane messageScrollPane = new JScrollPane(messageValueArea);
        messageScrollPane.setBorder(null);
        messageScrollPane.setPreferredSize(new Dimension(250, 100));
        
        amountValueLabel = new JLabel("");
        statusValueLabel = new JLabel("");
        backButton = new JButton("Quay lại");

        // Thiết lập frame
        setTitle("Chi tiết thông báo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 700);
        setResizable(false);
        
        // Tạo panel chứa nội dung với padding lớn hơn
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        contentPanel.setBackground(new Color(236, 240, 241));

        // Tùy chỉnh GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Tăng khoảng cách giữa các components
        gbc.anchor = GridBagConstraints.WEST;

        // Tùy chỉnh font và style
        Font labelFont = new Font("Segoe UI", Font.BOLD, 15);
        Font valueFont = new Font("Segoe UI", Font.PLAIN, 15);

        // Tạo panel riêng cho từng cặp label-value
        for (int i = 0; i < 5; i++) {
            JPanel rowPanel = new JPanel(new GridLayout(1, 2, 20, 0));
            rowPanel.setBackground(new Color(236, 240, 241));
            
            JLabel label = null;
            Component valueComponent = null;
            
            switch(i) {
                case 0:
                    label = dateLabel;
                    valueComponent = dateValueLabel;
                    break;
                case 1:
                    label = typeLabel;
                    valueComponent = typeValueLabel;
                    break;
                case 2:
                    label = amountLabel;
                    valueComponent = amountValueLabel;
                    break;
                case 3:
                    label = statusLabel;
                    valueComponent = statusValueLabel;
                    break;
                case 4:
                    label = messageLabel;
                    valueComponent = messageScrollPane;
                    rowPanel.setLayout(new BorderLayout(20, 0));
                    label.setVerticalAlignment(SwingConstants.TOP);
                    label.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
                    rowPanel.add(label, BorderLayout.NORTH);
                    rowPanel.add(valueComponent, BorderLayout.CENTER);
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.weighty = 1.0;
                    break;
            }
            
            if (label != null && valueComponent != null) {
                label.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label.setForeground(new Color(44, 62, 80));
                
                if (i != 4) { // Nếu không phải là message row
                    rowPanel.add(label);
                    rowPanel.add(valueComponent);
                }
                
                gbc.gridy = i;
                gbc.gridx = 0;
                gbc.gridwidth = 2;
                
                if (i != 4) {
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    gbc.weighty = 0.0;
                }
                
                contentPanel.add(rowPanel, gbc);
            }
        }

        // Thêm separator
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(189, 195, 199));
        gbc.gridy = 5;
        gbc.insets = new Insets(20, 10, 20, 10);
        contentPanel.add(separator, gbc);

        // Tùy chỉnh nút back
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setBackground(new Color(41, 128, 185));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Panel riêng cho nút back
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(236, 240, 241));
        buttonPanel.add(backButton);
        
        gbc.gridy = 6;
        contentPanel.add(buttonPanel, gbc);

        // Thêm contentPanel vào frame
        add(contentPanel);
        setLocationRelativeTo(null);
    }
    
    // Getters
    public JLabel getDateValueLabel() {
        return dateValueLabel;
    }
   public JLabel getTypeValueLabel(){
    return typeValueLabel;
   }

   public JTextArea getMessageValueArea() {
       return messageValueArea;
   }

   public JLabel getAmountValueLabel() {
       return amountValueLabel;
   }
   public JLabel getStatusValueLabel(){
    return statusValueLabel;
   }
   public JButton getBackButton(){
    return backButton;
   }

}
