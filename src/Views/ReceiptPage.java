package Views;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ReceiptPage extends JFrame {
    private JButton returnButton;
    private JButton continueButton;
    private JLabel moneyLabel;
    private JLabel moneyTransferredLabel;
    private JLabel sourceLabel;
    private JLabel sourceNameLabel;
    private JLabel sourceOwnerLabel;
    private JLabel destinationLabel;
    private JLabel destinationNameLabel;
    private JLabel destinationAccountLabel;
    private JLabel destinationBankLabel;
    private JLabel balanceLabel;
    private JLabel dateLabel;
    private JLabel idLabel;
    private JLabel contentsLabel;

    private static final Color BACKGROUND_COLOR = new Color(198, 198, 254);
    private static final Color FOREGROUND_COLOR = new Color(0, 0, 0);
    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 16);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 18);
    private static final Color BUTTON_BACKGROUND_COLOR = new Color(122, 122, 229);

    public ReceiptPage() {
        this.setTitle("Transaction Details");
        this.setSize(400, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(BUTTON_BACKGROUND_COLOR);

        /* Title */
        JLabel titleLabel = new JLabel("Transaction Details", JLabel.CENTER);
        titleLabel.setForeground(FOREGROUND_COLOR);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24).deriveFont(Font.BOLD, 24f * Toolkit.getDefaultToolkit().getScreenResolution() / 72f));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        returnButton = StyledButton("←", new Font("Tahoma", Font.BOLD, 20));
        titlePanel.add(returnButton, BorderLayout.WEST);
        add(titlePanel, BorderLayout.NORTH);

        /* Content */
        moneyLabel = new JLabel("Amount", JLabel.CENTER);
        moneyLabel.setForeground(FOREGROUND_COLOR);
        moneyLabel.setFont(DEFAULT_FONT.deriveFont(Font.BOLD, 22));
        moneyTransferredLabel = new JLabel("", JLabel.CENTER);
        moneyTransferredLabel.setForeground(FOREGROUND_COLOR);
        moneyTransferredLabel.setFont(DEFAULT_FONT.deriveFont(Font.PLAIN, 36));

        sourceLabel = StyledLabel("From account: ", DEFAULT_FONT);
        sourceNameLabel = StyledLabel("", TITLE_FONT);
        sourceOwnerLabel = StyledLabel("", TITLE_FONT);

        destinationLabel = StyledLabel("To account: ", DEFAULT_FONT);
        destinationNameLabel = StyledLabel("", TITLE_FONT);
        destinationAccountLabel = StyledLabel("", TITLE_FONT);
        destinationBankLabel = StyledLabel("", TITLE_FONT);

        balanceLabel = StyledLabel("Post-transaction balance: ", DEFAULT_FONT);
        dateLabel = StyledLabel("Transaction time: ", DEFAULT_FONT);
        idLabel = StyledLabel("Transaction code: ", DEFAULT_FONT);
        contentsLabel = StyledLabel("Description: ", DEFAULT_FONT);

        /* Panel */
        JPanel formPanel = new JPanel();
        formPanel.setBackground(BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setLayout(new GridLayout(14, 1, 5, 5));

        formPanel.add(moneyLabel);
        formPanel.add(moneyTransferredLabel);
        formPanel.add(sourceLabel);
        formPanel.add(sourceNameLabel);
        formPanel.add(sourceOwnerLabel);
        formPanel.add(destinationLabel);
        formPanel.add(destinationNameLabel);
        formPanel.add(destinationAccountLabel);
        formPanel.add(destinationBankLabel);
        formPanel.add(balanceLabel);
        formPanel.add(dateLabel);
        formPanel.add(idLabel);
        formPanel.add(contentsLabel);
        add(formPanel, BorderLayout.CENTER);

        /* Buttons */
        continueButton = StyledButton("Continue transfer", DEFAULT_FONT);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(returnButton);
        buttonPanel.add(continueButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton StyledButton(String text, Font font) {
        Border dashBorder = BorderFactory.createDashedBorder(Color.BLACK,1,5,2,false);
        Border buttonBorder = BorderFactory.createCompoundBorder(dashBorder,BorderFactory.createEmptyBorder(10,10,10,10));
        JButton button = new JButton(text);
        button.setForeground(FOREGROUND_COLOR);
        button.setBackground(BUTTON_BACKGROUND_COLOR);
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        button.setFont(font);
        button.setBorder(buttonBorder);
        return button;
    }

    private JLabel StyledLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setForeground(FOREGROUND_COLOR);
        label.setFont(font);
        return label;
    }

    public JButton getReturnButton() {
        return returnButton;
    }

    public JButton getContinueButton() {
        return continueButton;
    }

    public JLabel getMoneyLabel() {
        return moneyLabel;
    }

    public JLabel getMoneyTransferredLabel() {
        return moneyTransferredLabel;
    }

    public JLabel getSourceLabel() {
        return sourceLabel;
    }

    public JLabel getSourceNameLabel() {
        return sourceNameLabel;
    }

    public JLabel getSourceOwnerLabel() {
        return sourceOwnerLabel;
    }

    public JLabel getDestinationLabel() {
        return destinationLabel;
    }

    public JLabel getDestinationNameLabel() {
        return destinationNameLabel;
    }

    public JLabel getDestinationAccountLabel() {
        return destinationAccountLabel;
    }

    public JLabel getDestinationBankLabel() {
        return destinationBankLabel;
    }

    public JLabel getBalanceLabel() {
        return balanceLabel;
    }

    public JLabel getDateLabel() {
        return dateLabel;
    }

    public JLabel getIdLabel() {
        return idLabel;
    }

    public JLabel getContentsLabel() {
        return contentsLabel;
    }
}
