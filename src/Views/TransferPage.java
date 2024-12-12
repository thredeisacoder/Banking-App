package Views;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class TransferPage extends JFrame {
    private JButton transferButton;
    private JButton checkAccountNumberButton;
    private JLabel balanceLabel;
    private JLabel destinationOwnerLabel;
    private JTextField destinationInput;
    private JTextField moneyInput;
    private JTextField contentsInput;
    private JComboBox bankNameComboBox;
    private JButton returnButton;

    private static final Color BACKGROUND_COLOR = new Color(198, 198, 254);
    private static final Color FOREGROUND_COLOR = new Color(0, 0, 0);
    private static final Font DEFAULT_FONT = new Font("Tahoma", Font.PLAIN, 16);
    private static final Color BUTTON_BACKGROUND_COLOR = new Color(122, 122, 229);

    public TransferPage() {
        this.setTitle("Transfer");
        this.setSize(400, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLayout(new BorderLayout());

        JLabel bankNameLabel;
        JLabel destinationLabel;
        JLabel moneyLabel;
        JLabel contentsLabel;

        bankNameComboBox = new JComboBox();
        bankNameComboBox.setBackground(BACKGROUND_COLOR);
        bankNameComboBox.setForeground(FOREGROUND_COLOR);
        bankNameComboBox.setFont(DEFAULT_FONT);

        checkAccountNumberButton = new JButton("#");
        styleButton(checkAccountNumberButton);

        returnButton = new JButton("←");
        styleButton(returnButton);
        returnButton.setFont(new Font("Arial", Font.BOLD, 20));

        transferButton = new JButton("Transfer");
        styleButton(transferButton);
        transferButton.setFont(new Font("Arial", Font.BOLD, 20));

        destinationInput = styleTextField(new JTextField());
        moneyInput = styleTextField(new JTextField());
        contentsInput = styleTextField(new JTextField());

        balanceLabel = styleLabel(new JLabel("Balance: "));
        bankNameLabel = styleLabel(new JLabel("Select bank: "));
        destinationOwnerLabel = styleLabel(new JLabel("Recipient name: "));
        destinationLabel = styleLabel(new JLabel("Account number: "));
        moneyLabel = styleLabel(new JLabel("Amount: "));
        contentsLabel = styleLabel(new JLabel("Description: "));

        JPanel formPanel = new JPanel();
        formPanel.setBackground(BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setLayout(new GridLayout(12, 1));

        JPanel checkPanel = new JPanel();
        checkPanel.setBackground(BUTTON_BACKGROUND_COLOR);
        checkPanel.setLayout(new BorderLayout());
        checkPanel.add(destinationInput, BorderLayout.CENTER);
        checkPanel.add(checkAccountNumberButton, BorderLayout.EAST);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(BUTTON_BACKGROUND_COLOR);
        JLabel titleLabel = new JLabel("Transfer Money", JLabel.CENTER);
        titleLabel.setForeground(FOREGROUND_COLOR);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16).deriveFont(Font.BOLD, 24f * Toolkit.getDefaultToolkit().getScreenResolution() / 72f));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(returnButton, BorderLayout.WEST);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);

        formPanel.add(balanceLabel);
        formPanel.add(bankNameLabel);
        formPanel.add(bankNameComboBox);
        formPanel.add(destinationLabel);
        formPanel.add(checkPanel);
        formPanel.add(destinationOwnerLabel);
        formPanel.add(moneyLabel);
        formPanel.add(moneyInput);
        formPanel.add(contentsLabel);
        formPanel.add(contentsInput);
        formPanel.add(transferButton);
        add(formPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void styleButton(JButton button) {
        Border dashBorder = BorderFactory.createDashedBorder(Color.BLACK,1,5,2,false);
        Border buttonBorder = BorderFactory.createCompoundBorder(dashBorder,BorderFactory.createEmptyBorder(10,10,10,10));
        button.setForeground(FOREGROUND_COLOR);
        button.setBackground(BUTTON_BACKGROUND_COLOR);
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        button.setFont(DEFAULT_FONT);
        button.setBorder(buttonBorder);
    }

    private JLabel styleLabel(JLabel label) {
        label.setForeground(FOREGROUND_COLOR);
        label.setFont(DEFAULT_FONT);
        return label;
    }

    private JTextField styleTextField(JTextField textField) {
        Border dashBorder = BorderFactory.createDashedBorder(Color.BLACK,1,5,2,false);
        Border emptyBorder= BorderFactory.createEmptyBorder(5,5,5,5);
        Border textFieldBorder = BorderFactory.createCompoundBorder(dashBorder,emptyBorder);
        textField.setBackground(BACKGROUND_COLOR);
        textField.setForeground(FOREGROUND_COLOR);
        textField.setCaretColor(FOREGROUND_COLOR);
        textField.setFont(DEFAULT_FONT);
        textField.setBorder(textFieldBorder);
        return textField;
    }

    public JButton getTransferButton() {
        return transferButton;
    }
    public JButton getCheckAccountNumberButton() {
        return checkAccountNumberButton;
    }
    public JLabel getBalanceLabel() {
        return balanceLabel;
    }
    public JLabel getDestionationOwnerLabel() {
        return destinationOwnerLabel;
    }
    public JTextField getDestinationInput() {
        return destinationInput;
    }
    public JTextField getMoneyInput() {
        return moneyInput;
    }
    public JTextField getContentsInput() {
        return contentsInput;
    }
    public JComboBox getBankNameComboBox() {
        return bankNameComboBox;
    }
    public JButton getReturnButton() {
        return returnButton;
    }
}
