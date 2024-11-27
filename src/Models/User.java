package Models;

import Controllers.ConnectDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String address;
    private String phone;
    public User(int id, String name, String email, String password, String address, String phone) {
        if(id!=-1) this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }
    public int getId() {
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

    public static User getUser(String phone, String password) {
        try{
            Connection connection = ConnectDatabase.getConnection();
            String query= "select * from users where phone= ? and password= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, phone);
            preparedStatement.setString(2, password);
            ResultSet resultSet =preparedStatement.executeQuery();
            if (resultSet.next()){
                int id =0;
                String name = "";
                String email = "";
                String address = "";
                id = resultSet.getInt("id");
                name = resultSet.getString("name");
                email = resultSet.getString("email");
                password = resultSet.getString("password");
                address = resultSet.getString("address");
                phone = resultSet.getString("phone");
                return new User(id, name, email, password, address, phone);
           }
            else return  null;
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

//    public static String getUsername(int user_id){
//        try {
//            Connection connection = ConnectDatabase.getConnection();
//            String query = "select name from users where user_id = ?";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1, user_id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                String name = resultSet.getString("name");
//                return name;
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return null;
//    }
    public static boolean exist(String phone){
        try {
            Connection connection = ConnectDatabase.getConnection();
            String query= "select * from users where phone= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, phone);
            ResultSet resultSet =preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else{
                return false;
            }
        }
        catch (SQLException e){
            System.out.println(e);
            return false;
        }


    }

    public static boolean addUser(User user) {
        try{
            Connection connection = ConnectDatabase.getConnection();
            String query= "insert into users( name, email, password, address, phone) values(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3,user.getPassword());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setString(5,user.getPhone());
            preparedStatement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            System.out.println(e);
            return false;
        }
    }

}
