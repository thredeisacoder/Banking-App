package Controllers;

import javax.swing.*;

import Models.*;
import Views.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AppController {

    User user;
    Account account;
    List<Transaction> transactionList;
    List<Notification> notificationList;
    LoginPage loginPage;
    HomePage homePage;
    TransferPage transferPage;
    RegisterPage registerPage;
    ReceiptPage receiptPage;

    DetailUserPage detailUserPage;

    public AppController() {
        startApp();
        try {
            ConnectDatabase.getConnection();
            System.out.println("Connect to database successfully!!!");
        }
        catch (SQLException e) {
        	System.out.println(e);
		}
    }
    public void startApp() {
        changeToLoginPage();
    }
    public void hanleLogin(Map<String, String> userInput) {
        System.out.println(userInput);
        if (!validateLogin(userInput.get("phone"), userInput.get("password"))){
            JOptionPane.showMessageDialog(loginPage, "Invalid Input!!!");
            return;
        }
        user=User.getUser(userInput.get("phone"), userInput.get("password"));
        if(user!=null) {
            account=Account.getAccount(user.getId());
            transactionList=Transaction.getTransactionList(user.getId());
            notificationList=Notification.getNotificationList(user.getId());
            loginPage.dispose();
            changeToHomePage();
        }
        else{
            JOptionPane.showMessageDialog(loginPage, "User not found!!!");
        }
    }
    public void changeToLoginPage(){
        loginPage= new LoginPage();
        JButton loginButton = loginPage.getLoginBtn();
        loginButton.addActionListener(e -> hanleLogin(loginPage.getUserInput()));
        JButton registerBtn = loginPage.getRegisterBtn();
        registerBtn.addActionListener(e -> changeToRegisterPage());
    }
    public void changeToHomePage(){
        homePage = new HomePage();
        JButton logoutBtn = homePage.getLogoutBtn();
        logoutBtn.addActionListener(e -> logout());
        JLabel nameLabel= homePage.getNameLabel();
        nameLabel.setText("Name: "+user.getName());
        JLabel accNoLabel = homePage.getAccNoLabel();
        accNoLabel.setText("Account Number: "+ account.getAccno());
        JLabel balanceLabel= homePage.getBalanceLabel();
        balanceLabel.setText(("Balance: "+account.getBalance())+"$");
        JButton userBtn = homePage.getDetailBtn();
        userBtn.addActionListener(e->{
            homePage.dispose();
            changeToDetailPage();
        });
        JButton transferButton = homePage.getTransferBtn();
        transferButton.addActionListener(e->{
            homePage.dispose();
            changeToTransferPage();
        });
        loginPage.dispose();
        homePage.setVisible(true);
    }
    public void changeToReceiptPage(){
        receiptPage = new ReceiptPage();
        /* Return Button */
        JButton returnButton = receiptPage.getReturnButton();
        returnButton.addActionListener(e->{
            receiptPage.dispose();
            changeToHomePage();
        });

        /* Countiune Button */
        JButton continueButton = receiptPage.getContinueButton();
        continueButton.addActionListener(e->{
            receiptPage.dispose();
            changeToTransferPage();
        });
    }

    public void changeToRegisterPage(){
        registerPage = new RegisterPage();
        JButton submitBtn = registerPage.getSubmitBtn();
        submitBtn.addActionListener(e -> hanleRegister(registerPage.getUserInput()));
        JButton turnBackBtn = registerPage.getTurnBackBtn();
        turnBackBtn.addActionListener(e->{
            registerPage.dispose();
            changeToLoginPage();
        });
        loginPage.dispose();
        registerPage.setVisible(true);
    }

    public void changeToDetailPage(){
        detailUserPage= new DetailUserPage();

        JTextField nameField= detailUserPage.getNameField();
        nameField.setText(user.getName());
        JTextField phoneField= detailUserPage.getPhoneField();
        phoneField.setText(user.getPhone());
        JTextField emailField = detailUserPage.getEmailField();
        emailField.setText(user.getEmail());
        JTextField addressField = detailUserPage.getAddressField();
        addressField.setText(user.getAddress());

        JButton editBtn = detailUserPage.getEditBtn();

        JButton saveBtn = detailUserPage.getSaveBtn();

        JButton turnBackBtn= detailUserPage.getTurnBackBtn();
        turnBackBtn.addActionListener(e->{
            detailUserPage.dispose();
            changeToHomePage();
        });

        homePage.dispose();
    }

    public void changeToTransferPage(){
        transferPage = new TransferPage();

        /* Return Button */
        JButton returnButton = transferPage.getReturnButton();
        returnButton.addActionListener(e->{
            transferPage.dispose();
            changeToHomePage();
        });

        /* Balance */
        JLabel balanceLabel = transferPage.getBalanceLabel();
        balanceLabel.setText("Balance: "+account.getBalance()+"$");


        JButton checkAccountNumberButton = transferPage.getCheckAccountNumberButton();
        checkAccountNumberButton.addActionListener(e->{

        });

        /* Choosing bank names */
        JComboBox bankNameComboBox = transferPage.getBankNameComboBox();
        ArrayList<String> bankNameList = new ArrayList<>();
        bankNameList = Bank.getBankList();
        for(String bankName:bankNameList){
            bankNameComboBox.addItem(bankName);
        }

        /* Transfer button */
        JButton transferButton = transferPage.getTransferButton();
        transferButton.addActionListener(e->{
            handleTransferMoney();
            handleReceiptPage(receiptPage);
            transferPage.dispose();
        });

        /* Check button */
        JButton checkButton = transferPage.getCheckAccountNumberButton();
        checkButton.addActionListener(e->{
            handleCheckAccountNumber();
        });
    }

    public boolean validateLogin(String phone, String password) {
        if(phone.equals("") || password.equals("")) return false;
        else if(phone.length()!=10||password.length()<4) return false;
        else return true;
    }

    public boolean validateRegister(Map<String,String> userInput){
        if(!userInput.get("confirmPass").equals(userInput.get("password"))){
            return false;
        }
        else if(userInput.get("phone").length()!=10||userInput.get("password").length()<4) return false;
        else return true;
    }


    public void hanleRegister(Map<String, String> userInput){
        if(!validateRegister(userInput)){
            JOptionPane.showMessageDialog(registerPage, "Invalid Input!!!");
            return;
        }
        else if(User.exist(userInput.get("phone"))){
            JOptionPane.showMessageDialog(registerPage, "phone was registered!!!");
            return;
        }

        User newUser= new User(-1, userInput.get("name"), userInput.get("email"), userInput.get("password"), userInput.get("address"), userInput.get("phone"));
        if(User.addUser(newUser)){
            newUser = User.getUser(newUser.getPhone(),newUser.getPassword());
            Account newAccount = new Account(null,0,newUser.getId(), userInput.get("bankName"));
            if(Account.addAccount(newAccount)){
                JOptionPane.showMessageDialog(registerPage, "resgister successfully");
                registerPage.dispose();
                changeToLoginPage();
            }
            else{
                JOptionPane.showMessageDialog(registerPage, "error!!!");
            }
        }
        else{
            JOptionPane.showMessageDialog(registerPage, "error!!!");
        }
    }

    public void logout(){
        user=null;
        account=null;
        transactionList.clear();
        notificationList.clear();
        homePage.dispose();
        changeToLoginPage();
    }
    public void handleTransferMoney(){
        /* Destination Number */
        JTextField destinationInput = transferPage.getDestinationInput();

        /* Money */
        JTextField getMoneyInput = transferPage.getMoneyInput();

        /* Contents */
        JTextField getContentsInput = transferPage.getContentsInput();

        /* Combobox Bank Names */
        JComboBox bankNameComboBox = transferPage.getBankNameComboBox();

        Map<String, String> userInput = Map.of("money", getMoneyInput.getText(), "contents", getContentsInput.getText(), "bankName", (String)bankNameComboBox.getSelectedItem());

        try {
            double moneyAmount = Double.parseDouble(getMoneyInput.getText());
            if (!Constraints.validateHandleMoney(Map.of("money", getMoneyInput.getText())) || moneyAmount > account.getBalance()) {
                JOptionPane.showMessageDialog(transferPage, "Not enough money!");
                return;
            }

            LocalDate now = LocalDate.now();
            Transaction input = new Transaction(-1, moneyAmount, now, account.getAccno(), destinationInput.getText(), (String) bankNameComboBox.getSelectedItem(), getContentsInput.getText()
            );

            if (Transaction.addTransaction(input) && Account.tranferMoney(account.getAccno(), destinationInput.getText(), moneyAmount)) {
                account = Account.getAccount(user.getId());
                JOptionPane.showMessageDialog(transferPage, "Chuyển tiền thành công");
                transferPage.dispose();
                changeToReceiptPage();
            } else {
                JOptionPane.showMessageDialog(transferPage, "Chuyển tiền thất bại!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(transferPage, "Sai định dạng số tiền");
        }
    }

    public boolean handleCheckAccountNumber(){
        JComboBox bankNameComboBox = transferPage.getBankNameComboBox();
        JTextField destinationInput = transferPage.getDestinationInput();
        String checkAccount = account.getUserName(destinationInput.getText());
        String checkBankName = account.getBankName(destinationInput.getText());

        if (!Constraints.validateCheckNumber(Map.of("accno", destinationInput.getText()))) {
            JOptionPane.showMessageDialog(transferPage, "Số tài khoản không hợp lệ");
            return false;
        }
        if (checkAccount.isEmpty() || checkBankName.isEmpty() || !checkBankName.equals((String)bankNameComboBox.getSelectedItem())) {
            JOptionPane.showMessageDialog(transferPage, "Không tìm thấy số tài khoản. Vui lòng kiểm tra lại ngân hàng hoặc số ngân hàng!");
            return false;
        }
        else{
            JLabel destionationOwnerLabel = transferPage.getDestionationOwnerLabel();
            destionationOwnerLabel.setText("Recipient name: "+checkAccount);
            return true;
        }

    }
    public void handleReceiptPage(ReceiptPage receiptPage) {
        String moneyTransferred = transferPage.getMoneyInput().getText();
        String sourceName = account.getUserName(account.getAccno());
        String sourceAccNumber = account.getAccno();
        String destinationName = account.getUserName(transferPage.getDestinationInput().getText());
        String destinationAccNumber = transferPage.getDestinationInput().getText();
        String destinationBankName = account.getBankName(transferPage.getDestinationInput().getText());
        String balance = String.format("%,.2f", account.getBalance());
        String contents = transferPage.getContentsInput().getText();
        String transactionId = destinationBankName + "0109928211" + Transaction.getTransactionId(sourceAccNumber, destinationAccNumber);
        String transactionDate = Transaction.getTransactionDate(sourceAccNumber, destinationAccNumber);

        receiptPage.getMoneyTransferredLabel().setText(moneyTransferred);
        receiptPage.getSourceNameLabel().setText(sourceName);
        receiptPage.getSourceOwnerLabel().setText(sourceAccNumber);
        receiptPage.getDestinationNameLabel().setText(destinationName);
        receiptPage.getDestinationAccountLabel().setText(destinationAccNumber);
        receiptPage.getDestinationBankLabel().setText(destinationBankName);
        receiptPage.getBalanceLabel().setText("Post-transaction balance: " + balance);
        receiptPage.getDateLabel().setText("Transaction time: " + transactionDate);
        receiptPage.getIdLabel().setText("Transaction code: " + transactionId);
        receiptPage.getContentsLabel().setText("Description: " + contents);
    }
}

