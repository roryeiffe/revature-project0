package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao{
    Connection connection;

    public TransactionDaoImpl() {
        // create the connection:
        connection = ConnectionFactory.getConnection();
    }

    // add a transaction to the database:
    @Override
    public void add(Transaction transaction) throws SQLException {
        String sql = "insert into transaction (donor_id, recip_id, amount) values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1,transaction.getDonor_id());
        preparedStatement.setInt(2,transaction.getRecip_id());
        preparedStatement.setInt(3,transaction.getAmount());
        int count = preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (count == 1) {
            System.out.println("Transaction requested.");
            resultSet.next();
            int id = resultSet.getInt(1);
            transaction.setId(id);
        }
        else{
            System.out.println("Oops! Transaction not requested.");
        }

    }

    // update a transaction:
    @Override
    public void update(Transaction transaction) throws SQLException {
        // only ever need to change status, since the others should stay the same:
        String sql = "update transaction set status ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,transaction.getStatus());
        preparedStatement.setInt(2,transaction.getId());
        int count = preparedStatement.executeUpdate();
        if (count == 1) {
            System.out.println("Transaction update successful!");
        }
        else{
            System.out.println("Oops! Transaction update failed!");
        }
    }

    // get a specific transaction:
    @Override
    public Transaction get(int id) throws SQLException {
        Transaction transaction = null;
        String sql = "Select * from transaction where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            int id_ = resultSet.getInt(1);
            int donor_id = resultSet.getInt(2);
            int recip_id = resultSet.getInt(3);
            int amount = resultSet.getInt(4);
            String status = resultSet.getString(5);
            transaction = new Transaction(id_,donor_id,recip_id,amount,status);
        }
        return transaction;
    }

    // View all transactions in the database:
    @Override
    public List<Transaction> getAll() throws SQLException {
        Transaction transaction = null;
        List<Transaction> transactions = new ArrayList<Transaction>();
        String sql = "Select * from transaction";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            int id_ = resultSet.getInt(1);
            int donor_id = resultSet.getInt(2);
            int recip_id = resultSet.getInt(3);
            int amount = resultSet.getInt(4);
            String status = resultSet.getString(5);
            transaction = new Transaction(id_,donor_id,recip_id,amount,status);
            transactions.add(transaction);
        }
        return transactions;
    }

    @Override
    public List<Transaction> getAllIncoming(int custId) throws SQLException {
        Transaction transaction = null;
        List<Transaction> transactions = new ArrayList<Transaction>();
        String sql = "Select * from transaction where recip_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,custId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            int id_ = resultSet.getInt(1);
            int donor_id = resultSet.getInt(2);
            int recip_id = resultSet.getInt(3);
            int amount = resultSet.getInt(4);
            String status = resultSet.getString(5);
            transaction = new Transaction(id_,donor_id,recip_id,amount,status);
            transactions.add(transaction);
        }
        // return the transactions that belong to this customer:
        return transactions;
    }
}
