package Models;

import Controllers.ConnectDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Account {
    private String accno;
    private double balance;
    private int user_id;
    private String bankName;
    public Account(String accno, double balance,int user_id, String bankName) {
        if(accno!=null) this.accno = accno;
        else this.accno= generateUniqueCode();
        if(balance!=0) this.balance =balance;
        else this.balance =0.0;
        this.bankName=bankName;
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
    public String getBankName() {
        return bankName;
    }

    public int getUser_id() {
        return user_id;
    }

    public void addTransaction(Transaction transaction) {
        double money = transaction.getMoney();
        setBalance(money + balance);
    }
    private static boolean isCodeExists(String code){
        try{
            Connection cn = ConnectDatabase.getConnection();
            String query = "select * from accounts where accno=?";
            PreparedStatement ps= cn.prepareStatement(query);
            ps.setString(1,code);
            ResultSet res=ps.executeQuery();
            if(res.next()){
                return true;
            }
            else{
                return false;
            }
        }
        catch (SQLException e){
            System.out.println(e);
            return true;
        }
    }
    public static String generateUniqueCode() {
        Random random = new Random();
        String code;

        do {
            //
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                sb.append(random.nextInt(10)); // Sinh số từ 0-9
            }
            code = sb.toString();
        } while (isCodeExists(code)); // Kiểm tra trùng trong cơ sở dữ liệu

        return code;
    }
    public static Account getAccount(int user_id) {

        try {
            Connection connection = ConnectDatabase.getConnection();
            String query = "select * from accounts where user_id = ?";
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String accno = resultSet.getString("accno");
                double balance = resultSet.getDouble("balance");
                resultSet.getInt("user_id");
                String bankName = resultSet.getString("bankName");
                return new Account(accno, balance, user_id, bankName);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static boolean addAccount(Account account){
        try{
            Connection connection = ConnectDatabase.getConnection();
            String query= "insert into accounts(accno, balance, user_id) values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, account.getAccno());
            preparedStatement.setDouble(2, account.getBalance());
            preparedStatement.setInt(3, account.getUser_id());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
}
