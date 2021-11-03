package com.company.account;

// ensures that only one instance of the account dao implementation is created:
public class AccountDaoFactory {
    private static AccountDaoImpl dao = null;

    private AccountDaoFactory() {
        if(dao == null) {
            dao = new AccountDaoImpl();
        }
    }

    public static AccountDao getAccountDao() {
        if (dao == null) {
            dao = new AccountDaoImpl();
        }
        return dao;
    }
}
