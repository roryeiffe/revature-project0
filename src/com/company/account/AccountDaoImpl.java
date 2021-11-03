package com.company.account;

import com.company.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        String sql = "insert into account (name, cust_id, balance) values(?, ?, ?)";
        // make sure we get the account id back:
        PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,account.getName());
        preparedStatement.setInt(2,account.getOwnerId());
        preparedStatement.setInt(3,account.getBalance());
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
    public void update(Account account) throws SQLException {
        String sql = "update account set name = ?, balance = ?, status = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,account.getName());
        preparedStatement.setInt(2,account.getBalance());
        preparedStatement.setString(3,account.getStatus());
        preparedStatement.setInt(4,account.getId());
        int count = preparedStatement.executeUpdate();
        if(count != 1){
            System.out.println("Oops! Something went wrong updating the account.");
        }

    }

    // Given a custid, return a list of account objects that belong to that customer id:
    @Override
    public List<Account> getAccountsForCustomer(int custId) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        // use store procedure:
        String sql = "CALL getCustomerAccounts(?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,custId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            // Read data from query:
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            int custId_ = resultSet.getInt(3);
            int balance = resultSet.getInt(4);
            String status = resultSet.getString(5);
            // create account object:
            Account account = new Account(id,name,custId_,balance,status);
            accounts.add(account);
        }
        return accounts;
    }

    // get all accounts in the database:
    @Override
    public List<Account> getAllAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "select * from account";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            // Read data from query:
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            int custId_ = resultSet.getInt(3);
            int balance = resultSet.getInt(4);
            String status = resultSet.getString(5);
            // create account object:
            Account account = new Account(id,name,custId_,balance,status);
            accounts.add(account);
        }
        return accounts;
    }

    // get a specific account by id:
    @Override
    public Account getAccountById(int Accountid) throws SQLException {
        Account account = null;
        String sql = "select * from account where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,Accountid);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            // Read data from query:
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            int custId_ = resultSet.getInt(3);
            int balance = resultSet.getInt(4);
            String status = resultSet.getString(5);
            // create account object:
            account = new Account(id,name,custId_,balance,status);
        }
        return account;
    }
}
