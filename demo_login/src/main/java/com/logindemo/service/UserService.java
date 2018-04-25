package com.logindemo.service;

import com.logindemo.domain.Employee;
import com.logindemo.domain.User;

import java.util.List;

/**
 * Created by marnon on 2017/7/23.
 */

public interface UserService {
    boolean signUp(String account, String pass, String name);
    boolean signIn(String account, String pass);
    boolean checkAccount(String account);
    User getUserMessage(String account);
    User getUserById(int id);
    List<Employee> getEmployee(int employer);
    void addEmployee(List<Employee> list, int employer);
}
