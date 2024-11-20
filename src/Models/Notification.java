package Models;

import Controllers.ConnectDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Notification {
    private LocalDate date;
    private String message;
    private String id;

    public Notification(LocalDate date, String message, String id) {
        this.date = date;
        this.message = message;
        this.id = id;
    }

    public static List<Notification> getNotificationList(String user_id) {
        List<Notification> notificationList = new ArrayList<>();
        try {
            Connection connection = ConnectDatabase.getConnection();
            String query ="select * from notifications where user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LocalDate date = LocalDate.parse(resultSet.getString("date"));
                String type = resultSet.getString("type");
                String message = resultSet.getString("message");
                String id = resultSet.getString("id");
                notificationList.add(new Notification(date, message, id));
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return notificationList;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
    public String getId() {
        return id;
    }
}
