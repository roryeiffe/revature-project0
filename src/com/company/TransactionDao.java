package com.company;

import java.sql.SQLException;
import java.util.List;

public interface TransactionDao {
    // These methods affect the transaction database:
    public void add(Transaction transaction) throws SQLException;
    public void update(Transaction transaction) throws SQLException;
    public Transaction get(int id) throws SQLException;
    public List<Transaction> getAll() throws SQLException;
    public List<Transaction> getAllIncoming(int custId) throws SQLException;
}
