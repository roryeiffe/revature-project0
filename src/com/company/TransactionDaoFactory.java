package com.company;

// make the transaction dao a singleton:
public class TransactionDaoFactory {
    private static TransactionDao dao = null;

    private TransactionDaoFactory() {
    }

    public static TransactionDao getTransactionDao() {
        if(dao == null) {
            dao = new TransactionDaoImpl();
        }
        return dao;
    }
}
