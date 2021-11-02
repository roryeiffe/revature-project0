package com.company.user;

import com.company.user.UserDao;
import com.company.user.UserDaoImpl;

// This class ensures that only one instance of the
// the employee dao implementation is created:
// (singleton)
public class EmployeeDaoFactory {
    private static UserDao dao = null;

    private EmployeeDaoFactory () {
        if(dao == null) {
            dao = new UserDaoImpl("employee");
        }
    }

    // create an instance of the employee dao using
    // employee as the user type:
    public static UserDao getEmployeeDao() {
        if(dao == null) {
            dao = new UserDaoImpl("employee");
        }
        return dao;
    }
}
