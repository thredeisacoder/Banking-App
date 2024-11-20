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
    public Transaction(String id, Double money, LocalDate date, String source, String destination) {
        this.id = id;
        this.money = money;
        this.date = date;
        this.source = source;
        this.destination = destination;
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

    public static List<Transaction> getTransactionList(String user_id) {
        List<Transaction> transactionList = new ArrayList<>();
        try {
            Connection connection = ConnectDatabase.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from transactions where source = ? or destination = ?");
            preparedStatement.setString(1, user_id);
            preparedStatement.setString(2, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                Double money = resultSet.getDouble("money");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String source = resultSet.getString("source");
                String destination = resultSet.getString("destination");
                transactionList.add(new Transaction(id, money, date, source, destination));
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return transactionList;
    }
}
