package Controllers;

import javax.swing.*;

import Models.Account;
import Models.Notification;
import Models.Transaction;
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
    LoginPage LoginPage;
    HomePage HomePage;

    RegisterPage RegisterPage;


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
            JOptionPane.showMessageDialog(LoginPage, "Invalid Input!!!");
            return;
        }
        user=User.Login(userInput.get("phone"), userInput.get("password"));
        if(user!=null) {
            account=Account.getAccount(user.getId());
            transactionList=Transaction.getTransactionList(user.getId());
            notificationList=Notification.getNotificationList(user.getId());
            LoginPage.dispose();
            changeToHomePage();
        }
        else{
            JOptionPane.showMessageDialog(LoginPage, "User not found!!!");
        }
    }
    public void changeToLoginPage(){
        LoginPage= new LoginPage();
        JButton loginButton = LoginPage.getLoginBtn();
        loginButton.addActionListener(e -> hanleLogin(LoginPage.getUserInput()));
        JButton registerBtn = LoginPage.getRegisterBtn();
        registerBtn.addActionListener(e -> changeToRegisterPage());
    }
    public void changeToHomePage(){
        HomePage = new HomePage();
        JButton logoutBtn = HomePage.getLogoutBtn();
        logoutBtn.addActionListener(e -> logout());
        LoginPage.dispose();
        HomePage.setVisible(true);
    }

    public void changeToRegisterPage(){
        RegisterPage = new RegisterPage();
        JButton submitBtn = RegisterPage.getSubmitBtn();
        submitBtn.addActionListener(e -> hanleRegister(RegisterPage.getUserInput()));
        LoginPage.dispose();
        RegisterPage.setVisible(true);
    }

    public boolean validateLogin(String phone, String password) {
        if(phone.equals("") || password.equals("")) return false;
        else if(phone.length()!=10||password.length()<4) return false;
        else return true;
    }

//    public boolean validateRegister(String )


    public void hanleRegister(Map<String, String> userInput){
        System.out.println(userInput);

//        User newUser= new User(null, userInput.get("name"), userInput.get("email"), userInput.get("password"), userInput.get("address"), userInput.get("phone"));
    }

    public void logout(){
        user=null;
        account=null;
        transactionList.clear();
        notificationList.clear();
        HomePage.dispose();
        changeToLoginPage();
    }
}

