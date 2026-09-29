package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepoImpl implements UserRepo {

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet rs = prep.executeQuery()) {

            while (rs.next()) {
                users.add(mapToUser(rs));
            }
        } catch (SQLException e) {
            System.out.println("Get All Users: " + e.getMessage());
        }
        return users;
    }

    @Override
    public User getUserById(int id) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            if (rs.next()) {
                return mapToUser(rs);
            }
        } catch (SQLException e) {
            System.out.println("Get User By ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, username);
            ResultSet rs = prep.executeQuery();
            if (rs.next()) {
                return mapToUser(rs);
            }
        } catch (SQLException e) {
            System.out.println("Get User By Username: " + e.getMessage());
        }
        return null;
    }

    @Override
    public User login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, username);
            prep.setString(2, password);
            ResultSet rs = prep.executeQuery();
            if (rs.next()) {
                return mapToUser(rs);
            }
        } catch (SQLException e) {
            System.out.println("Login: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean createUser(User user) {
        String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, user.getUsername());
            prep.setString(2, user.getPassword());
            prep.setString(3, user.getEmail());

            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Create User: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {
        String query = "UPDATE users SET username = ?, email = ? WHERE id = ?";
        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, user.getUsername());
            prep.setString(2, user.getEmail());
            prep.setInt(3, user.getId());

            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Update User: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteUser(int id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Delete User: " + e.getMessage());
            return false;
        }
    }

    private User mapToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        return user;
    }
}
