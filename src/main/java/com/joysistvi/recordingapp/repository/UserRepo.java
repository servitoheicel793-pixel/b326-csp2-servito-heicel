package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.User;

import java.util.List;

public interface UserRepo {
    List<User> getAllUsers();
    User getUserById(int id);
    User getUserByUsername(String username);
    User login(String username, String password);
    boolean createUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int id);
}
