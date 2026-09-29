package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.model.User;
import com.joysistvi.recordingapp.repository.UserRepo;
import com.joysistvi.recordingapp.repository.UserRepoImpl;
import com.joysistvi.recordingapp.service.UserService;
import com.joysistvi.recordingapp.service.UserServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserView extends JFrame {

    private UserService userService = new UserServiceImpl();
    private UserRepo userRepo = new UserRepoImpl();

    private JTable table;
    private DefaultTableModel model;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField searchField;

    public UserView() {
        setTitle("User View - Recording App");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        loadUsers();
    }

    private void initUI() {
        // TOP - Search
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Search:"));
        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");
        JButton refreshBtn = new JButton("Refresh");
        top.add(searchField);
        top.add(searchBtn);
        top.add(refreshBtn);

        // CENTER - Table using your User model
        String[] cols = {"ID", "Username", "Email"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        // BOTTOM - Form connected to your User constructor
        // public User(String username, String password, String email)
        JPanel form = new JPanel(new GridLayout(2,1));
        JPanel inputs = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernameField = new JTextField(12);
        passwordField = new JPasswordField(12);
        emailField = new JTextField(15);

        inputs.add(new JLabel("Username:"));
        inputs.add(usernameField);
        inputs.add(new JLabel("Password:"));
        inputs.add(passwordField);
        inputs.add(new JLabel("Email:"));
        inputs.add(emailField);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBtn = new JButton("Add User");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton clearBtn = new JButton("Clear");
        buttons.add(addBtn);
        buttons.add(updateBtn);
        buttons.add(deleteBtn);
        buttons.add(clearBtn);

        form.add(inputs);
        form.add(buttons);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(form, BorderLayout.SOUTH);

        // ACTIONS
        searchBtn.addActionListener(e -> search());
        refreshBtn.addActionListener(e -> loadUsers());
        addBtn.addActionListener(e -> addUser());
        updateBtn.addActionListener(e -> updateUser());
        deleteBtn.addActionListener(e -> deleteUser());
        clearBtn.addActionListener(e -> clear());

        table.getSelectionModel().addListSelectionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                // uses your getters: getUsername(), getEmail()
                usernameField.setText(model.getValueAt(r, 1).toString());
                emailField.setText(model.getValueAt(r, 2).toString());
            }
        });
    }

    private void loadUsers() {
        model.setRowCount(0);
        List<User> users = userService.getAllUsers(); // uses UserRepoImpl
        for (User u : users) {
            // uses your getters: getId(), getUsername(), getEmail()
            model.addRow(new Object[]{u.getId(), u.getUsername(), u.getEmail()});
        }
    }

    private void search() {
        String keyword = searchField.getText().trim().toLowerCase();
        if (keyword.isEmpty()) { loadUsers(); return; }
        model.setRowCount(0);
        for (User u : userService.getAllUsers()) {
            if (u.getUsername().toLowerCase().contains(keyword) || u.getEmail().toLowerCase().contains(keyword)) {
                model.addRow(new Object[]{u.getId(), u.getUsername(), u.getEmail()});
            }
        }
    }

    private void addUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password required!");
            return;
        }
        // This uses your exact constructor from screenshot:
        // public User(String username, String password, String email)
        User user = new User(username, password, email);

        if (userService.register(user)) {
            JOptionPane.showMessageDialog(this, "User added!");
            clear();
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(this, "Username already exists!");
        }
    }

    private void updateUser() {
        int r = table.getSelectedRow();
        if (r < 0) { JOptionPane.showMessageDialog(this, "Select user first!"); return; }

        int id = (int) model.getValueAt(r, 0);
        User user = userRepo.getUserById(id);
        // uses your setters: setUsername(), setEmail(), setPassword(), setId()
        user.setUsername(usernameField.getText().trim());
        user.setEmail(emailField.getText().trim());
        String newPass = new String(passwordField.getPassword());
        if (!newPass.isEmpty()) {
            user.setPassword(newPass);
        }
        if (userRepo.updateUser(user)) {
            JOptionPane.showMessageDialog(this, "Updated!");
            loadUsers();
        }
    }

    private void deleteUser() {
        int r = table.getSelectedRow();
        if (r < 0) return;
        int id = (int) model.getValueAt(r, 0);
        if (JOptionPane.showConfirmDialog(this, "Delete this user?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (userService.deleteUser(id)) {
                loadUsers();
            }
        }
    }

    private void clear() {
        usernameField.setText("");
        passwordField.setText("");
        emailField.setText("");
        searchField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserView().setVisible(true));
    }
}
