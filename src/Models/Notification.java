package Models;

import Controllers.ConnectDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.mysql.cj.protocol.Resultset;

import java.util.logging.Level;

public class Notification {
    // khởi tạo 1 logger để log các thông báo
    private static final Logger LOGGER = Logger.getLogger(Notification.class.getName());
    private LocalDateTime date;
    private String message;
    private String id;

    private int user_id;
    // thêm thuộc tính mới
    private boolean isRead;
    private String type;
    private double amount;
    // ví dụ như:"transaction, security,system"
    public Notification(String message, int user_id) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Thông điệp không được để trống");
        }
        if (user_id <= 0) {
            throw new IllegalArgumentException("User ID không hợp lệ");
        }
        this.date = LocalDateTime.now();
        this.message = message;
        this.user_id = user_id;
    }
    public Notification(){}
    public Notification(String message, int user_id, String type, boolean status, LocalDateTime date, double amount) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Thông điệp không được để trống");
        }
        if (user_id <= 0) {
            throw new IllegalArgumentException("User ID không hợp lệ");
        }
        this.date = date;
        this.message = message;
        this.user_id = user_id;
        this.type = type;
        this.isRead = status;
        this.amount = amount;
    }

    // đánh giấu thông báo đã đọc
    public boolean markAsRead() {
        String query = "UPDATE  notificaction SET is_read =true WHERE id = ?";
        try (Connection conn = ConnectDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, this.id);
            int result = stmt.executeUpdate();
            if (result > 0) {
                this.isRead = true;
                return true;
            }
            return false;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "kh��ng đánh dấu thông báo đã đọc được", e);
            return false;
        }
    }

    // lấy thông báo chưa đọc từ user
    public static List<Notification> getUnreadNotifications(int user_id) {
        List<Notification> notificationList = new ArrayList<>();
        String query = "SELECT * FROM notifications WHERE user_id = ? AND is_read = false ORDER BY date DESC";
        try (Connection conn = ConnectDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, user_id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notificationList.add(createFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "không lấy được thông báo chương đọc", e);
        }
        return notificationList;
    }

    // lấy thông báo theo loại
    public static List<Notification> getNotificationsByType(int user_id, String type) {
        List<Notification> notificationList  = new ArrayList<>();
        String query ="SELECT * FROM notifications WHERE user_id = ? AND type =? ORDER BY date DESC";
        try(Connection conn = ConnectDatabase.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1,user_id);
            stmt.setString(2,type);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    notificationList.add(createFromResultSet(rs));
                }
            }
        }catch(SQLException e){
            LOGGER.log(Level.SEVERE,"không lấy được thông báo theo loai",e);
        }
        return notificationList;
    }

    // Đếm số thông báo chưa đọc 
    public static int countUnReadNotifications(int user_id) {
        String query = "SELECT COUNT(*) FROm notifications WHERE user_id= ? AND is_read = false";
        try (Connection conn = ConnectDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, user_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "không đếm được thông báo chưa đọc", e);
        }
        return 0;
    }

    // helper method để tạo đối tượng Notification từ ResultSet
    private static Notification createFromResultSet(ResultSet rs) throws SQLException {
        Notification notification = new Notification(rs.getString("message"), rs.getInt("user_id"),
                rs.getString("type"));
        notification.id = rs.getString("id");
        notification.date = LocalDate.parse(rs.getString("date"));
        return notification;
    }

    public static boolean markAsDeleted(String date, String type, String message) {
        try {
            // In ra định dạng chính xác của date từ database
            String checkQuery = "SELECT date FROM notifications WHERE type = ? AND message = ?";
            PreparedStatement checkStmt = ConnectDatabase.getConnection().prepareStatement(checkQuery);
            checkStmt.setString(1, type);
            checkStmt.setString(2, message);
            ResultSet rs = checkStmt.executeQuery();
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime dateTime = LocalDateTime.parse(date, inputFormatter);
            String formattedDate = dateTime.format(outputFormatter);
            String sql = "UPDATE notifications SET isdeleted = 1 WHERE date = ? AND type = ? AND message = ?";
            PreparedStatement stmt = ConnectDatabase.getConnection().prepareStatement(sql);
            stmt.setString(1, formattedDate);
            stmt.setString(2, type);
            stmt.setString(3, message);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // lấy tất cả thông báo
    public static List<Notification> getNotificationList(int user_id) {
        List<Notification> notificationList = new ArrayList<>();
        try {
            Connection connection = ConnectDatabase.getConnection();
            String query = "select * from notifications where user_id = ? AND isdeleted=0";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            while (resultSet.next()) {
                LocalDateTime date = LocalDateTime.parse(resultSet.getString("date"), formatter);
                String type = resultSet.getString("type");
                String message = resultSet.getString("message");
                String id = resultSet.getString("id");
                boolean status = resultSet.getBoolean("is_read");
                double amount = resultSet.getDouble("amount");
                notificationList.add(new Notification(message, user_id,type,status,date,amount));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return notificationList;
    }

    // phương thức lưu thông báo vào bên trong database
    public boolean saveNotification() {
        String query = "INSERT INTO notification (data,message,user_id) VALUES (?,?,?)";
        try (Connection connection = ConnectDatabase.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1, date.toString());
            preparedStatement.setString(2, message);
            preparedStatement.setInt(3, user_id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "không lưu được thông báo vào trong database", e);
            return false;
        }
    }

    public static boolean updateStatus(String date, String type, String message, String newStatus) {
        try {
            // In ra định dạng chính xác của date từ database
            String checkQuery = "SELECT date FROM notifications WHERE type = ? AND message = ?";
            PreparedStatement checkStmt = ConnectDatabase.getConnection().prepareStatement(checkQuery);
            checkStmt.setString(1, type);
            checkStmt.setString(2, message);
            ResultSet rs = checkStmt.executeQuery();
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime dateTime = LocalDateTime.parse(date, inputFormatter);
            String formattedDate = dateTime.format(outputFormatter);
            if (rs.next()) {
                System.out.println("Date in database: " + rs.getString("date"));
                System.out.println("Date from parameter: " + formattedDate);
            }
            String query = "UPDATE notifications SET is_read = ? WHERE date = ? AND type = ? AND message = ?";
            PreparedStatement stmt = ConnectDatabase.getConnection().prepareStatement(query);

            // Chuyển đổi trạng thái thành "true" nếu là "Đã đọc", ngược lại là "false"
           
            
            stmt.setBoolean(1, newStatus.equals("Đã đọc"));
            System.out.println(newStatus.equals("Đã đọc"));
            // stmt.setString(2, date);
            stmt.setString(2, formattedDate);
            stmt.setString(3, type);
            stmt.setString(4, message);

            // Debug: In ra các giá trị để kiểm tra
            System.out.println("Updating notification:");
            System.out.println("Date: " + date);
            System.out.println("Type: " + type);
            System.out.println("Message: " + message);
            System.out.println("New status: " + newStatus);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật trạng thái: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    // phương thức lấy thông báo
    // public static List<Notification> getNotifications(int user_id) {
    //     List<Notification> notificationList = new ArrayList<>();
    //     String query = "SELECT * FROM notifications WHERE user_id=?";
    //     try (Connection connection = ConnectDatabase.getConnection();
    //             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
    //         preparedStatement.setInt(1, user_id);
    //         try (ResultSet resultSet = preparedStatement.executeQuery()) {
    //             while (resultSet.next()) {
    //                 Notification notification = new Notification(resultSet.getString("message"),
    //                         resultSet.getInt("user_id"));
    //                 notification.id = resultSet.getString("id");
    //                 notification.date = LocalDate.parse(resultSet.getString("date"));
    //                 notificationList.add(notification);
    //             }
    //         }
    //     } catch (SQLException e) {
    //         LOGGER.log(Level.SEVERE, "không lấy được thông báo từ database", e);
    //     }
    //     return notificationList;

    // }
    public boolean isRead() {
        return isRead;
    }

    public String getType() {
        return type;
}
    public LocalDateTime getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }
    public double getAmount() {
        return amount;
    }
}
