package Models;

import Controllers.ConnectDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private String id;
    private String type;
    private Double money;
    private LocalDate date;
    private String source;
    private String destination;
    private String bankName;
    public Transaction(String id, Double money, LocalDate date, String source, String destination, String bankName) {
        this.id = id;
        this.money = money;
        if(this.date==null)date= LocalDate.now();
        else{
            this.date = date;
        }
        this.source = source;
        this.destination = destination;
        this.bankName = bankName;
    }
    public String getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public Double getMoney() {
        return money;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getSource() {
        return source;
    }
    public String getDestination() {
        return destination;
    }
    public String getBankName() {
        return bankName;
    }

    public static List<Transaction> getTransactionList(int user_id) {
        List<Transaction> transactionList = new ArrayList<>();
        try {
            Connection connection = ConnectDatabase.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from transactions where source = ? or destination = ?");
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                Double money = resultSet.getDouble("money");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String source = resultSet.getString("source");
                String destination = resultSet.getString("destination");
                String bankName = resultSet.getString("bankName");
                transactionList.add(new Transaction(id, money, date, source, destination, bankName));
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return transactionList;
    }
}
