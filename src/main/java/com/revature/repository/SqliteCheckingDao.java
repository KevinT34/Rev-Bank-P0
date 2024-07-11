package com.revature.repository;

import com.revature.entity.Checking;
import com.revature.exception.UserSQLException;
import com.revature.utility.DatabaseConnector;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SqliteCheckingDao implements CheckingDao{

    @Override
    public Checking createChecking(int userId, Checking newCheckingAccount) {
        return null;
    }

    @Override
    public Map<Integer, Checking> getAllAccounts(int userId) {
        String sql = "SELECT * FROM Checking WHERE user_id = ?";

        try(Connection connection = DatabaseConnector.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            Map<Integer, Checking> accounts = new HashMap<>();
            while(resultSet.next()) {
                Checking newAccount = new Checking();
                newAccount.setNumber(resultSet.getInt("account_number"));
                newAccount.setSum(resultSet.getDouble("balance"));
                newAccount.setUserId(resultSet.getInt("user_id"));
                accounts.put(newAccount.getNumber(), newAccount);
            }
            return accounts;

        } catch (SQLException e) {
            throw new UserSQLException(e.getMessage());
        }

    }

}
