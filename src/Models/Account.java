package Models;

import Controllers.ConnectDatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Account {
    private String accno;
    private double balance;
    private String user_id;
    public Account(String accno, double balance,String user_id) {
        this.accno = accno;
        this.balance = balance;
        this.user_id=user_id;
    }
    public String getAccno() {
        return accno;
    }
    public double getBalance() {
        return balance;
    }
    private void setBalance (double balance) {
        this.balance = balance;
    }
    public void addTransaction(Transaction transaction) {
        double money = transaction.getMoney();
        setBalance(money + balance);
    }

    public static Account getAccount(String user_id) {

        try {
            Connection connection = ConnectDatabase.getConnection();
            String query = "select * from accounts where user_id = ?";
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String accno = resultSet.getString("accno");
                double balance = resultSet.getDouble("balance");
                return new Account(accno, balance, user_id);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


}
