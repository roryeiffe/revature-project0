package com.company;

// This class ensures that only one instance of the
// the customer dao implementation is created:
// (singleton)
public class CustomerDaoFactory {
    private static UserDao dao = null;

    private CustomerDaoFactory () {
        if(dao == null) {
            dao = new UserDaoImpl("customer");
        }
    }

    // create an instance of the user dao and pass in
    // "customer" as the user type:
    public static UserDao getCustomerDao() {
        if(dao == null) {
            dao = new UserDaoImpl("customer");
        }
        return dao;
    }
}
