package Models;

import Controllers.ConnectDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String address;
    private String phone;
    public User(String id, String name, String email, String password, String address, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static User Login(String phone, String password) {
        try{
            Connection connection = ConnectDatabase.getConnection();
            String query= "select * from users where phone= ? and password= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, phone);
            preparedStatement.setString(2, password);
            ResultSet resultSet =preparedStatement.executeQuery();
            return User.getUser(resultSet);
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    private static User getUser(ResultSet resultSet) {
        String id = "";
        String name = "";
        String email = "";
        String password = "";
        String address = "";
        String phone = "";
        try {
            if (resultSet.next()){
                 id = resultSet.getString("id");
                 name = resultSet.getString("name");
                 email = resultSet.getString("email");
                 password = resultSet.getString("password");
                 address = resultSet.getString("address");
                 phone = resultSet.getString("phone");
            }
            else return null;
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return new User(id, name, email, password, address, phone);
    }

    private static void addUser(User user) {
        try{
            Connection connection = ConnectDatabase.getConnection();
            String query= "insert into users(id, name, email, password, address, phone) values(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.getPhone());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

}
