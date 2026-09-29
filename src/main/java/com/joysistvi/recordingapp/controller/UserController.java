package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.User;
import com.joysistvi.recordingapp.repository.UserRepo;
import com.joysistvi.recordingapp.repository.UserRepoImpl;
import com.joysistvi.recordingapp.service.UserService;
import com.joysistvi.recordingapp.service.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

public class UserController {

    private final UserService userService;
    private final UserRepo userRepo;

    // Default constructor - fixes your current code
    public UserController() {
        this.userRepo = new UserRepoImpl();
        this.userService = new UserServiceImpl(userRepo);
    }

    // Constructor injection - matches your AlbumView / SongView pattern
    public UserController(UserService userService, UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

    public User login(String username, String password) {
        return userService.login(username, password);
    }

    public boolean register(String username, String password, String email) {
        if (username == null || username.trim().isEmpty()) return false;
        User user = new User(username, password, email);
        return userService.register(user);
    }

    public boolean register(User user) {
        return userService.register(user);
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public User getUserById(int id) {
        return userService.getUserById(id);
    }

    public List<User> searchUsers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllUsers();
        }
        String lower = keyword.toLowerCase();
        return getAllUsers().stream()
                .filter(u -> u.getUsername().toLowerCase().contains(lower) ||
                        u.getEmail().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public boolean updateUser(User user) {
        return userService.updateUser(user);
    }

    public boolean updateUser(int id, String username, String email, String newPassword) {
        User user = userRepo.getUserById(id);
        if (user == null) return false;

        user.setUsername(username);
        user.setEmail(email);
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            user.setPassword(newPassword);
        }
        return userService.updateUser(user);
    }

    public boolean deleteUser(int id) {
        return userService.deleteUser(id);
    }
}
