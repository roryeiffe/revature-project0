package com.company;

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

    public static UserDao getEmployeeDao() {
        if(dao == null) {
            dao = new UserDaoImpl("employee");
        }
        return dao;
    }
}
