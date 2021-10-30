package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccountDaoImpl implements AccountDao{

    Connection connection;

    // set up connection to the database:
    public AccountDaoImpl() {
        connection = ConnectionFactory.getConnection();
    }

    // Given an account object, insert into the database:
    @Override
    public void add(Account account) throws SQLException {
        String sql = "insert into account (name, cust_id) values(?, ?)";
        // make sure we get the account id back:
        PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,account.getName());
        preparedStatement.setInt(2,account.getOwnerId());
        int count = preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if(count == 1) {
            // Success:
            System.out.println("Account requested! Please wait for one of our employees to verify it.");
            resultSet.next();
            int id = resultSet.getInt(1);
            account.setId(id);
        }
        else{
            System.out.println("Oops! There was an error applying for an account.");
        }
    }

    // update the given account in the database:
    @Override
    public void update(Account account) {

    }

    // Given a custid, return a list of account objects that belong to that customer id:
    @Override
    public List<Account> getAccountsForCustomer(int custId) {
        return null;
    }

    // get all accounts in the database:
    @Override
    public List<Account> getAllAccounts() {
        return null;
    }

    // get a specific account by id:
    @Override
    public Account getAccountById(int Accountid) {
        return null;
    }
}
