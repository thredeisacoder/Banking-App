package Controllers;

import javax.swing.*;

import Models.*;
import Views.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;



public class AppController {

    User user;
    Account account;
    List<Transaction> transactionList;
    List<Notification> notificationList;
    LoginPage loginPage;
    HomePage homePage;
    TransferPage transferPage;
    RegisterPage registerPage;
    AuthenPage authenPage;
    ResetPassWordPage resetPassWordPage;
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
    // login at first
    public void startApp() {
        changeToLoginPage();
    }
    // validate login input and redirect to home page
    public void hanleLogin(Map<String, String> userInput) {
        System.out.println(userInput);
        //validate phone and password
        if (!validateLogin(userInput.get("phone"), userInput.get("password"))){
            JOptionPane.showMessageDialog(loginPage, "Invalid Input!!!");
            return;
        }
        //get user matches phone and password -> save it to user variable
        user=User.getUser(userInput.get("phone"), userInput.get("password"));

        if(user!=null) {
            //found user in database -> redirect to home page with informations of that user
            account=Account.getAccount(user.getId());
            transactionList=Transaction.getTransactionList(user.getId());
            notificationList=Notification.getNotificationList(user.getId());
            loginPage.dispose();
            changeToHomePage();
        }
        else{//if user is not exist in database
            JOptionPane.showMessageDialog(loginPage, "User not found!!!");
        }
    }
    public void changeToLoginPage(){
        loginPage= new LoginPage();
        JButton loginButton = loginPage.getLoginBtn();
        loginButton.addActionListener(e -> {
            hanleLogin(loginPage.getUserInput());
        });
        JButton registerBtn = loginPage.getRegisterBtn();
        registerBtn.addActionListener(e ->{
            loginPage.dispose();
            changeToRegisterPage();
        });

        JButton resetPasswordBtn = loginPage.getResetPassBtn();
        resetPasswordBtn.addActionListener(e -> {
            loginPage.dispose();
            changeToAuthenPage();
        });
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
        BigDecimal balance = new BigDecimal(account.getBalance());
        balanceLabel.setText(("Balance: "+balance.toString()+"$"));
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
    }

    public void changeToRegisterPage(){
        registerPage = new RegisterPage();
        JButton submitBtn = registerPage.getSubmitBtn();

        JComboBox<String> bankNameInput = registerPage.getBankNameInput();
        ArrayList<String> bankNameLst = Bank.getBankList();
        for (String bankName : bankNameLst ) {
            bankNameInput.addItem(bankName);
        }

        submitBtn.addActionListener(e -> handleRegister(registerPage.getUserInput()));


        JButton turnBackBtn = registerPage.getTurnBackBtn();
        turnBackBtn.addActionListener(e->{
            registerPage.dispose();
            changeToLoginPage();
        });
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

        JButton saveBtn= detailUserPage.getSaveBtn();
        JButton editBtn = detailUserPage.getEditBtn();
        JButton turnBackBtn= detailUserPage.getTurnBackBtn();
        JButton changePasswordBtn= detailUserPage.getChangePasswordBtn();

        changePasswordBtn.addActionListener(e->{
            detailUserPage.dispose();
            changeToResetPassPage(user.getPhone());
        });

        editBtn.addActionListener(e->{
            saveBtn.setVisible(true);
            editBtn.setVisible(false);
            turnBackBtn.setVisible(false);
            changePasswordBtn.setVisible(true);
            nameField.setEditable(true);
            emailField.setEditable(true);
            addressField.setEditable(true);
        });

        turnBackBtn.addActionListener(e->{
            detailUserPage.dispose();
            changeToHomePage();
        });
        saveBtn.addActionListener(e->{
            saveBtn.setVisible(false);
            editBtn.setVisible(true);
            turnBackBtn.setVisible(true);
            changePasswordBtn.setVisible(false);
            nameField.setEditable(false);
            emailField.setEditable(false);
            addressField.setEditable(false);
            handleEdit(nameField.getText(),phoneField.getText(),emailField.getText(),addressField.getText());
        });
    }

    public void handleEdit(String name,String phone,String email,String address){
        System.out.println(name+phone+email+address);

        if (!Constraints.validUsername(name)||!Constraints.validPhone(phone)||!Constraints.validAddress(email)||!Constraints.validAddress(address)){//invalid input
            detailUserPage.dispose();
            changeToDetailPage();
        } else if (name.equals(user.getName())&&phone.equals(user.getPhone())&&email.equals(user.getEmail())&&address.equals(user.getAddress())) {
            //if nothing changed -> do nothing
        } else if(User.updateUser(name,phone,email,address)){//update new information
            user=  User.getUser(user.getPhone(),user.getPassword());
            JOptionPane.showMessageDialog(detailUserPage, "Successfully updated!!!");
        }
        else{//if update error
            JOptionPane.showMessageDialog(detailUserPage, "Update failed!!!");
            detailUserPage.dispose();
            changeToDetailPage();
        }
    }
    public void changeToTransferPage(){
        transferPage = new TransferPage();
        JButton returnButton = transferPage.getReturnButton();
        returnButton.addActionListener(e->{
            transferPage.dispose();
            changeToHomePage();
        });

        /* Balance */
        JLabel balanceLabel = transferPage.getBalanceLabel();
        balanceLabel.setText("Số dư: "+account.getBalance()+"$");

        JLabel destionationOwnerLabel = transferPage.getDestionationOwnerLabel();
//        destionationOwnerLabel.setText();

        JButton checkAccountNumberButton = transferPage.getCheckAccountNumberButton();
        checkAccountNumberButton.addActionListener(e->{
            System.out.println("clicked");
            JTextField destinationInput = transferPage.getDestinationInput();
            String accno = destinationInput.getText();
            System.out.println(accno);
//            if(accno.equals(account.getAccno())){
//                return;
//            }
            String user_name = Account.getUserName(accno);
            System.out.println(user_name+"ok");
        });

        /**/
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
        });

    }
    //create otp contains 6 numbers
    public String createOtp(){
        String otp = "";
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int randomNumber = random.nextInt(10);
            otp = otp + String.valueOf(randomNumber);
        }
        return otp;
    }

    public void changeToAuthenPage(){
        authenPage = new AuthenPage();
        JTextField phoneInput = authenPage.getPhoneInput();
        JButton otpBtn = authenPage.getSendOtpBtn();
        JButton submitBtn = authenPage.getSubmitBtn();
        JTextField otpInput = authenPage.getOtpInput();
        JLabel timeLabel = authenPage.getTimeLabel();
        StringBuilder otp= new StringBuilder("");

        otpBtn.addActionListener(e->{
            String phone = phoneInput.getText();
            if(!Constraints.validPhone(phone)){//validate phone number
                JOptionPane.showMessageDialog(authenPage, "Phone number is incorrect");
                return;
            }
            if(!User.exist(phone)){//find user with phone number
                JOptionPane.showMessageDialog(authenPage, "User not found");
                return;
            }
            //prepare for sending otp
            SpeedSMSAPI sender = new SpeedSMSAPI();
            //create counter for 5 minutes
            Thread countDown=new Thread(()->{
                int seconds = 180;
                try {
                    while (seconds > 0) {
                        int minutes = seconds / 60;
                        int remainingSeconds = seconds % 60;

                        timeLabel.setText(minutes+":" +(remainingSeconds<10? "0"+ remainingSeconds :remainingSeconds));
                        Thread.sleep(1000);
                        seconds--;
                    }
                    otpBtn.setEnabled(true);
                    timeLabel.setText("");
                } catch (InterruptedException er) {
                    System.out.println("countdown error");
                }
            });
            try {
                otp.delete(0,otp.length());
                otp.append(createOtp());
                //sender.sendSMS(phone,otp.toString());
                if(otp.toString().equals("")){
                    throw new IOException();
                }
                otpBtn.setEnabled(false);
                countDown.start();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });

        submitBtn.addActionListener(e->{
            if(otp.toString().equals("")){
                JOptionPane.showMessageDialog(authenPage,"No otp found");
            }
            else if(otp.toString().equals(otpInput.getText())){//if otp is correct
                System.out.println(otp);
                authenPage.dispose();
                changeToResetPassPage(phoneInput.getText());
            }
            else{
                JOptionPane.showMessageDialog(authenPage, "OTP is incorrect");
            }
        });
    }

    public void changeToResetPassPage(String phone){
        resetPassWordPage= new ResetPassWordPage(phone);

        JPasswordField  passwordInput = resetPassWordPage.getPassword();
        JPasswordField confirmInput = resetPassWordPage.getConfirmPassWord();
        JButton submitBtn = resetPassWordPage.getSubmitBtn();

        submitBtn.addActionListener(e->{
            //validate password
            if(Constraints.validPassword(passwordInput.getText())&&passwordInput.getText().equals(confirmInput.getText())){
                if(User.updatePassword(phone,passwordInput.getText()))//if update success
                {
                    JOptionPane.showMessageDialog(detailUserPage,"Password changed successfully, please login again!");
                    resetPassWordPage.dispose();
                    changeToLoginPage();
                }
                else {//error update
                    JOptionPane.showMessageDialog(detailUserPage,"update password error");
                }

            }
            else{//password is not matched with confirm
                JOptionPane.showMessageDialog(detailUserPage,"Password not match");
            }
        });
    }


    public void handleRegister(Map<String, String> userInput){
        if(!validateRegister(userInput)){//validate input
            JOptionPane.showMessageDialog(registerPage, "Invalid Input!!!");
            return;
        }
        else if(User.exist(userInput.get("phone"))){//check exist phone number in database
            JOptionPane.showMessageDialog(registerPage, "phone was registered!!!");
            return;
        }
        //create new user
        User newUser= new User(-1, userInput.get("name"), userInput.get("email"), userInput.get("password"), userInput.get("address"), userInput.get("phone"));
        if(User.addUser(newUser)){//add user to database
            newUser = User.getUser(newUser.getPhone(),newUser.getPassword());
            //create account for new user
            Account newAccount = new Account(null,0,newUser.getId(), userInput.get("bankName"));
            if(Account.addAccount(newAccount)){//add account to database
                JOptionPane.showMessageDialog(registerPage, "resgister successfully");
                registerPage.dispose();
                changeToLoginPage();
            }
            else{
                JOptionPane.showMessageDialog(registerPage, "error!!");
            }
        }
        else{
            JOptionPane.showMessageDialog(registerPage, "error!!!");
        }
    }

    public void logout(){
        //reset all information about user
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

        Map<String, String> input = new HashMap<>();
        input.put("source", account.getAccno());
        input.put("destination", destinationInput.getText());
        input.put("money", getMoneyInput.getText());
        input.put("contents", getContentsInput.getText());
        input.put("bankName", (String)bankNameComboBox.getSelectedItem());

        if(!validateTransfer(input)){
            JOptionPane.showMessageDialog(transferPage, "Invalid Input!!!");
            return;
        }
        if(!validateHandleMoney(input)){
            JOptionPane.showMessageDialog(transferPage, "Not enough money!!!");
            return;
        }

    }
    public boolean validateLogin(String phone, String password) {
        //validate phone and pass
        return Constraints.validPhone(phone) && Constraints.validPassword(password);
    }
    public boolean validateRegister(Map<String,String> userInput){
        System.out.println(userInput);
        if(!userInput.get("confirmPass").equals(userInput.get("password"))){
            return false;
        }
        else return (Constraints.validPhone(userInput.get("phone"))
                && Constraints.validPassword(userInput.get("password"))
                && Constraints.validAddress("address")
                && Constraints.validEmail(userInput.get("email"))
        );
    }
    public boolean validateTransfer(Map<String, String> userInput){
        if(userInput.get("money").equals("") || userInput.get("contents").equals("") || userInput.get("bankName").equals("")) return false;{
            return true;
        }
    }
    public boolean validateHandleMoney(Map<String, String> userInput){
        double money = Double.parseDouble(userInput.get("money"));
        if(money > account.getBalance()) return false;
        return true;
    }
}

