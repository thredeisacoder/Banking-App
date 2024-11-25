package Controllers;

import javax.swing.*;

import Models.Account;
import Models.Notification;
import Models.Transaction;
import Views.DetailUserPage;
import Views.HomePage;
import Views.LoginPage;
import Views.RegisterPage;
import Models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class AppController {

    User user;
    Account account;
    List<Transaction> transactionList;
    List<Notification> notificationList;
    LoginPage loginPage;
    HomePage homePage;

    RegisterPage registerPage;

    DetailUserPage detailUserPage;

    public AppController() {
        startApp();
        try {
            ConnectDatabase.getConnection();
            System.out.println("connect to database successfully!!!");
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
        loginPage.dispose();
        homePage.setVisible(true);
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
            Account newAccount = new Account(null,0,newUser.getId());
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
}

