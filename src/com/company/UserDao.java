package com.company;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    // These methods update/query the employee and customer
    // databases:
    boolean add(User user) throws SQLException;
    void update(User user) throws SQLException;
    List<User> getUsers() throws SQLException;
    User getUserById(int id) throws SQLException;

}
