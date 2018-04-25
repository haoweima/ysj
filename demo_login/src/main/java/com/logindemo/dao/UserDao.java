package com.logindemo.dao;

import com.logindemo.domain.Employee;
import com.logindemo.domain.User;

import java.util.List;

/**
 * Created by marnon on 2017/7/23.
 */


public interface UserDao {
    User getUserByAccount(String account);
    String checkPassByAccount(String account);
    boolean addUser(String account, String pass, String name, String sex, String tel, String card, int employer, int power);
    User getUserById(int id);
    List<Employee> getEmployeeByEmployer(int employer);
    void addEmployee(Employee employee, int employer);
}
