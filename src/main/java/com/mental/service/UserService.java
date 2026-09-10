package com.mental.service;

import com.mental.entity.User;
import com.mental.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean register(User user) {
        if (userMapper.findByUsername(user.getUsername()) != null) {
            return false;
        }
        user.setRole("student");
        return userMapper.insert(user) > 0;
    }

    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public List<User> findStudents() {
        return userMapper.findByRole("student");
    }

    public boolean update(User user) {
        return userMapper.update(user) > 0;
    }

    public boolean delete(Integer id) {
        return userMapper.deleteById(id) > 0;
    }

    public int countAll() {
        return userMapper.countAll();
    }

    public int countStudents() {
        return userMapper.countByRole("student");
    }
}
