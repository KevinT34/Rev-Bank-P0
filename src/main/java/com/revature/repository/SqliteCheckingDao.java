package com.revature.repository;

import com.revature.entity.Checking;
import com.revature.exception.CheckingAccountSQLException;
import com.revature.exception.UserSQLException;
import com.revature.utility.DatabaseConnector;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SqliteCheckingDao implements CheckingDao{

    @Override
    public Checking createChecking(Checking newCheckingAccount) {
        String sql = "INSERT INTO Accounts(balance, user_id) VALUES (?, ?)";

        try(Connection connection = DatabaseConnector.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, newCheckingAccount.getSum());
            preparedStatement.setInt(2, newCheckingAccount.getUserId());
            preparedStatement.executeUpdate();

            //retrieve the account number here
            ResultSet pkResultSet = preparedStatement.getGeneratedKeys();

            if(pkResultSet.next()) {
                int generatedAccountId = pkResultSet.getInt(1);
                Checking newAccount = new Checking();
                newAccount.setNumber(generatedAccountId);
                newAccount.setSum(newCheckingAccount.getSum());
                newAccount.setUserId(newCheckingAccount.getUserId());
                return newAccount;
            }

            throw new CheckingAccountSQLException("Checking Account could not be created: please try again");

        } catch (SQLException e) {
            throw new UserSQLException(e.getMessage());
        }
    }

    @Override
    public Map<Integer, Checking> getAllAccounts(int userId) {
        String sql = "SELECT * FROM Accounts WHERE user_id = ?";

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

    @Override
    public void deleteAccountByAccountId(int accountId, int userId) {
        String sql = "DELETE FROM Accounts WHERE account_number = ? AND user_id = ?";

        try (Connection connection = DatabaseConnector.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, userId);

            int rows = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + rows);


        } catch (SQLException e) {
            throw new UserSQLException(e.getMessage());
        }

    }

    //update
    @Override
    public void modifyAccount(int accountId, double newBalance, int userId) {
        String sql = "UPDATE Accounts SET balance = ? WHERE account_number = ? AND user_id = ?";

        try (Connection connection = DatabaseConnector.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setInt(2, accountId);
            preparedStatement.setInt(3, userId);

            int rows = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + rows);

        } catch (SQLException e) {
            throw new UserSQLException(e.getMessage());
        }
    }


}
