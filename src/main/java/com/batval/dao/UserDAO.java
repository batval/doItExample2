package com.batval.dao;

import com.batval.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class UserDAO {


    private static Connection connection;

    static {
        String url = null;
        String username = null;
        String password = null;

        //load db properties
        try (InputStream in = UserDAO.class.getClassLoader().getResourceAsStream("persistence.properties")) {
            Properties properties = new Properties();
            properties.load(in);
            Class.forName(properties.getProperty("driverClassName"));
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection successful!");
        } catch (SQLException ex) {
            System.out.println("Connection failed!");
            ex.printStackTrace();
        }
    }

    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("select * from users");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setName(rs.getString(1));
            user.setSurname(rs.getString(2));
            user.setEmail(rs.getString(3));
            users.add(user);
        }
        return users;
    }

    public User getUserByEmail(String email) {
        try {
            PreparedStatement ps = connection.prepareStatement(("select * from users where email =?"));
            ps.setString(1, email);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                User user = new User();
                user.setName(resultSet.getString(1));
                user.setSurname(resultSet.getString(2));
                user.setEmail(resultSet.getString(3));
                return user;
            }
        } catch (SQLException ex) {

        }
        return null;
    }

    public void addUser(User user){
        try {
            PreparedStatement ps = connection.prepareStatement(("insert into users values (?,?,?)"));
            ps.setString(1, user.getName());
            ps.setString(2,user.getSurname());
            ps.setString(3,user.getEmail());
            ps.execute();

        } catch (SQLException ex) {

        }
    }
}
