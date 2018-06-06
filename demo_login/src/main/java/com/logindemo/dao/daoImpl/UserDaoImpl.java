package com.logindemo.dao.daoImpl;

import com.logindemo.dao.UserDao;
import com.logindemo.domain.Employee;
import com.logindemo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by marnon on 2017/7/23.
 */

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User getUserByAccount(String account) {
        List<User> list = jdbcTemplate.query("select * from User where account = ?", new Object[]{account}, new BeanPropertyRowMapper(User.class));
        if(null != list && list.size()>0){
            User user = list.get(0);
            return user;
        }else{
            return null;
        }
    }

    @Override
    public String checkPassByAccount(String account) {
        List<String> list = jdbcTemplate.queryForList("select pass from User where account='" + account + "';", String.class);
        if(null != list && list.size()>0){
            String pass = list.get(0);
            return pass;
        }else{
            return null;
        }
    }

    @Override
    public boolean addUser(String account, String pass, String name, String sex, String tel, String card, int employer, int power) {
        if(jdbcTemplate.update("insert into User(idUser, account, pass, name, sex, number, card, employer, power) VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?)",null,  account, pass, name, sex, tel, card, employer, power) != 0){
            return true;
        }else return false;
    }

    @Override
    public User getUserById(int id) {
        List<User> users = jdbcTemplate.query("select * from ysj_Manager.User where idUser = " + id , new BeanPropertyRowMapper(User.class));
        if(users != null && users.size() > 0){
            return users.get(0);
        }else return null;
    }

    @Override
    public List<Employee> getEmployeeByEmployer(int employer) {
        String sql = "select name, sex, number, card, account, pass from User where employer = ? and idUser != ?";

        List<Employee> employees = jdbcTemplate.query(sql, new Object[]{employer, employer}, new BeanPropertyRowMapper(User.class));

        if(employees != null && employees.size() > 0){
            return employees;
        }else
            return null;
    }

    @Override
    public void addEmployee(Employee employee, int employer) {
        String sql = "insert into User(idUser, account, pass, name, sex, number, card, employer, power) value(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, null, employee.getAccount(), employee.getPass(), employee.getName(), employee.getSex(), employee.getNumber(), employee.getCard(), employer, 0);
    }
}
