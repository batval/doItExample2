package com.batval.dao;

import com.batval.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        String url  = null;
        String username = null;
        String password = null;

        //load db properties
            try (InputStream in = UserDAO.class.getClassLoader().getResourceAsStream("persistence.properties")){
                Properties properties = new Properties();
                properties.load(in);

//                url = properties.getProperty(url);
  //              username=properties.getProperty(username);
    //            password=properties.getProperty(password);

                url ="jdbc:mysql://localhost:3306/doit?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                username = "root";
                password = "admin";

            }
         catch (IOException e) {
            e.printStackTrace();
        }

            try {
                connection =DriverManager.getConnection(url,username,password);
                System.out.println("Connection successful!");
            }
            catch (SQLException ex)
            {
                System.out.println("Connection failed!");
                ex.printStackTrace();
            }
    }

    public List<User> getAll() throws SQLException {
        List<User> users  = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("select * from users");
        ResultSet rs = ps.executeQuery();
    while (rs.next()){
        User user = new User();
         user.setName(rs.getString(1));
         user.setSurname(rs.getString(2));
         user.setEmail(rs.getString(3));
         users.add(user);
    }
    return users;
    }
}
