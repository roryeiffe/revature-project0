package com.company;

import java.sql.SQLException;
import java.util.List;

public interface AccountDao {
    // These methods affect the account database:
    public void add(Account account) throws SQLException;
    public void update(Account account) throws SQLException;
    public List<Account> getAccountsForCustomer(int custId) throws SQLException;
    public List<Account> getAllAccounts() throws SQLException;
    public Account getAccountById(int Accountid) throws SQLException;
}
