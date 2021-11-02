package com.company;

// This class ensures that only one instance of the
// the employee dao implementation is created:
// (singleton)
public class EmployeeDaoFactory {
    private static EmployeeDao dao = null;

    private EmployeeDaoFactory () {
        if(dao == null) {
            dao = new EmployeeDaoImpl();
        }
    }

    public static EmployeeDao getEmployeeDao() {
        if(dao == null) {
            dao = new EmployeeDaoImpl();
        }
        return dao;
    }
}
