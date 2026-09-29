package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.User;

import java.util.List;

public interface UserService {
    User login(String username, String password);
    boolean register(User user);
    List<User> getAllUsers();
    User getUserById(int id);
    boolean deleteUser(int id);
    boolean updateUser(User user);
}
