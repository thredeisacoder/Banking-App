package Models;

import Controllers.ConnectDatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Bank {
    private String bankName;
    public Bank(String bankName) {
        this.bankName = bankName;
    }
    public String getBankName() {
        return bankName;
    }
    public static ArrayList<String> getBankList() {
        try{
            Connection connection = ConnectDatabase.getConnection();
            String query = "select * from banks";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            ArrayList<String> bankList = new ArrayList<>();
            while (resultSet.next()) {
                String bankName = resultSet.getString("bankName");
                bankList.add(bankName);
            }
            return bankList;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
