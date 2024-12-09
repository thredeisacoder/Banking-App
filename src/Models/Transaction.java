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
    private int id;
    private String type;
    private Double money;
    private LocalDate date;
    private String source;
    private String destination;
    private String bankName;
    private String content;
    public Transaction(int id, Double money, LocalDate date, String source, String destination, String bankName, String content) {
        this.id = id;
        this.money = money;
        this.date = date;
        this.source = source;
        this.destination = destination;
        this.bankName = bankName;
        this.content = content;
    }
    public int getId() {
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
    public String getContents() {
        return content;
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
                int id = resultSet.getInt("id");
                Double money = resultSet.getDouble("money");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String source = resultSet.getString("source");
                String destination = resultSet.getString("destination");
                String bankName = resultSet.getString("bankName");
                String content = resultSet.getString("content");
                transactionList.add(new Transaction(id, money, date, source, destination, bankName, content));
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return transactionList;
    }

    public static boolean addTransaction(Transaction transaction){
        try {
            Connection connection = ConnectDatabase.getConnection();
            String query = "insert into transactions(money, date, source, destination, bankName, content) values(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1, transaction.getId());
            preparedStatement.setDouble(1, transaction.getMoney());
            preparedStatement.setDate(2, java.sql.Date.valueOf(transaction.getDate()));
            preparedStatement.setString(3, transaction.getSource());
            preparedStatement.setString(4, transaction.getDestination());
            preparedStatement.setString(5, transaction.getBankName());
            preparedStatement.setString(6, transaction.getContents());
            preparedStatement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            System.out.println(e);
            return false;
        }
    }
    public static String getTransactionId(String source, String destination){
        String id = "";
        try (Connection connection = ConnectDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM transactions where source = ? and destination = ? ORDER BY id DESC LIMIT 1"
             )) {
            preparedStatement.setString(1, source);
            preparedStatement.setString(2, destination);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getString("id");
//                    System.out.println("ID: " + id);
                    if (id == null || id.trim().isEmpty()) {
                        id = "";
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error while retrieving id: " + e.getMessage());
            e.printStackTrace();
        }

        return id;
    }
    public static String getTransactionDate(String source, String destination){
        String date = "";
        try (Connection connection = ConnectDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM transactions where source = ? and destination = ? ORDER BY id DESC LIMIT 1"
             )) {
            preparedStatement.setString(1, source);
            preparedStatement.setString(2, destination);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    date = resultSet.getString("date");
//                    System.out.println("ID: " + date);
                    if (date == null || date.trim().isEmpty()) {
                        date = "";
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error while retrieving date: " + e.getMessage());
            e.printStackTrace();
        }

        return date;
    }
}
