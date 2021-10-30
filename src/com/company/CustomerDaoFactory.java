package com.company;

// This class ensures that only one instance of the
// the customer dao implementation is created:
// (singleton)
public class CustomerDaoFactory {
    private static CustomerDao dao = null;

    private CustomerDaoFactory () {
        if(dao == null) {
            dao = new CustomerDaoImpl();
        }
    }

    public static CustomerDao getCustomerDao() {
        if(dao == null) {
            dao = new CustomerDaoImpl();
        }
        return dao;
    }
}
