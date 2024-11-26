package Views;

import javax.swing.*;
import java.awt.*;

public class TransferPage extends JFrame {
    private JButton transferButton;
    private JButton checkAccountNumberButton;
    private JLabel balanceLabel;
    private JLabel destionationOwnerLabel;
    private JTextField destinationInput;
    private JTextField moneyInput;
    private JTextField contentsInput;
    private JComboBox bankNameComboBox;
    private JButton returnButton;

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
        checkAccountNumberButton = new JButton("✔");
        returnButton = new JButton("Return");
        transferButton = new JButton("Transfer");
        destinationInput = new JTextField();
        moneyInput = new JTextField();
        contentsInput = new JTextField();

        balanceLabel = new JLabel("Số dư: ");
        bankNameLabel = new JLabel("Chọn ngân hàng: ");
        destionationOwnerLabel = new JLabel("Người nhận: ");
        destinationLabel = new JLabel("Nhập số tài khoản: ");
        moneyLabel = new JLabel("Nhập Số tiền cần chuyển: ");
        contentsLabel = new JLabel("Nội dung chuyển khoản: ");

        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(new GridLayout(12,1));

        JPanel miniPanel = new JPanel();
        miniPanel.setLayout(new BorderLayout());
        miniPanel.add(destinationInput, BorderLayout.CENTER);
        miniPanel.add(checkAccountNumberButton, BorderLayout.EAST);

        formPanel.add(returnButton);
        formPanel.add(balanceLabel);
        formPanel.add(bankNameLabel);
        formPanel.add(bankNameComboBox);
        formPanel.add(destinationLabel);
        formPanel.add(miniPanel);
//        formPanel.add(destinationInput);
        formPanel.add(destionationOwnerLabel);
        formPanel.add(moneyLabel);
        formPanel.add(moneyInput);
        formPanel.add(contentsLabel);
        formPanel.add(contentsInput);
        formPanel.add(transferButton);
        add(formPanel, BorderLayout.CENTER);

        setVisible(true);
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
        return destionationOwnerLabel;
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
