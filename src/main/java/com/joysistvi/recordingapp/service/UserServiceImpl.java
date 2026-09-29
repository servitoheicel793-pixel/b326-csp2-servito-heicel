package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.User;
import com.joysistvi.recordingapp.repository.UserRepo;
import com.joysistvi.recordingapp.repository.UserRepoImpl;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    public UserServiceImpl() {
        // This connects Service to your Repo
        this.userRepo = new UserRepoImpl();
    }

    // For testing
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty()) return null;
        if (password == null || password.trim().isEmpty()) return null;
        // calls UserRepoImpl login
        return userRepo.login(username, password);
    }

    @Override
    public boolean register(User user) {
        // Uses your model getters from screenshot: getUsername(), getPassword(), getEmail()
        if (user == null) return false;
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            System.out.println("Username is required");
            return false;
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            System.out.println("Password is required");
            return false;
        }
        // Check duplicate
        User existing = userRepo.getUserByUsername(user.getUsername());
        if (existing != null) {
            System.out.println("Username already exists!");
            return false;
        }
        return userRepo.createUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        if (id <= 0) return null;
        return userRepo.getUserById(id);
    }

    @Override
    public boolean deleteUser(int id) {
        if (id <= 0) return false;
        return userRepo.deleteUser(id);
    }

    @Override
    public boolean updateUser(User user) {
        if (user == null || user.getId() <= 0) return false;
        return userRepo.updateUser(user);
    }
}
