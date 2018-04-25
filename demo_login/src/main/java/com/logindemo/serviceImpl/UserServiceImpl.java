package com.logindemo.serviceImpl;

import com.logindemo.dao.UserDao;
import com.logindemo.domain.Employee;
import com.logindemo.domain.User;
import com.logindemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by marnon on 2017/7/23.
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public boolean signUp(String account, String pass, String name) {

        if(userDao.addUser(account, pass, name, null,null,null, 0,0)){
            //注册成功
            return true;
        }

        return false;
    }

    @Override
    public boolean signIn(String account, String pass) {
        if(userDao.checkPassByAccount(account) != null && userDao.checkPassByAccount(account).equals(pass)){
            return true;
        }else
            return false;
    }

    @Override
    public boolean checkAccount(String account) {
        if(userDao.checkPassByAccount(account) != null){
            return true;
        }

        return false;
    }

    @Override
    public User getUserMessage(String account) {
        return userDao.getUserByAccount(account);
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public List<Employee> getEmployee(int employer) {
        return userDao.getEmployeeByEmployer(employer);
    }

    @Override
    public void addEmployee(List<Employee> list, int employer) {
        for(Employee employee : list){
            if(employee.getName().equals("")){
                break;
            }
            userDao.addEmployee(employee, employer);
        }
    }

}
