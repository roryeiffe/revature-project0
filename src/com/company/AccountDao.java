package com.company;

import java.sql.SQLException;
import java.util.List;

public interface AccountDao {
    public void add(Account account) throws SQLException;
    public void update(Account account);
    public List<Account> getAccountsForCustomer(int custId);
    public List<Account> getAllAccounts();
    public Account getAccountById(int Accountid);
}
