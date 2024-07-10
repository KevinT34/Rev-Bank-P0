package com.revature.repository;

import com.revature.entity.User;
import com.revature.exception.UserSQLException;
import com.revature.utility.DatabaseConnector;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteUserDao implements UserDao {

    @Override
    public User createUser(User newUserCredentials) {
        //need sql statement
        //need a connection object
        //need to return newly generated user
        String sql = "INSERT INTO user VALUES (?, ?)";
        try (Connection connection = DatabaseConnector.createConnection()){
            //User PreparedStatement to control how the user date is injected into our query.
            //PS helps to format the data to help protect against SQL injection attacks.
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //Indexing starts at 1 for Java SQL resources
            preparedStatement.setString(1, newUserCredentials.getUsername());
            preparedStatement.setString(2, newUserCredentials.getPassword());
            int result = preparedStatement.executeUpdate();
            //check if update was successful
            if (result == 1) {
                return newUserCredentials;
            }
            //if user not created, throw exception to handle the problem elsewhere
            throw new UserSQLException("User could not be created: please try again");
        } catch (SQLException e) {
            throw new UserSQLException(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {

        //need a sql statement
        //need a connection object
        //need a list of users to return
        String sql = "SELECT * FROM user";

        try(Connection connection = DatabaseConnector.createConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<User> users = new ArrayList<>();
            //use if if just 1 record expected
            while(resultSet.next()) {
                User userRecord = new User();
                userRecord.setUsername(resultSet.getString("username"));
                userRecord.setPassword(resultSet.getString("password"));
                users.add(userRecord);
            }
            return users;

        } catch (SQLException e) {
            // SQLException is a checked exception, so we need to handle i here, throw a custom exception, so we can
            // handle it in the appropriate layer
            throw new UserSQLException(e.getMessage());
        }

    }
}
