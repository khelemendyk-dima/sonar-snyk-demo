package com.example.demo.sonardemo.repository;

import com.example.demo.sonardemo.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Repository;
// unused import
import java.util.Optional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    // Hardcoded credentials and unused constant
    private static final String SALT_UNUSED_CONSTANT = "staticSalt";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "superSecretPassword";

    public List<User> getAllUsersByUsername(String username) {
        // SQL Injection vulnerability
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE username = " + username;

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                users.add(extractUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            String message = "User wasn't found because of " + e.getMessage();

            // logger should be used instead
            System.out.println(message);
            throw new RuntimeException(message);
        }

        return users;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, username, email, password FROM users";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                users.add(extractUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            String message = "Users weren't found because of " + e.getMessage();

            System.out.println(message);
            throw new RuntimeException(message);
        }
        return users;
    }

    public User getUserById(Long id) {
        String query = "SELECT id, username, email, password FROM users WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery(query);

            if (resultSet.next()) {
                return extractUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            String message = "Users wasn't found because of " + e.getMessage();

            System.out.println(message);
            throw new RuntimeException(message);
        }

        return null;
    }

    public void createUser(User user) {
        String query = "INSERT INTO users (username, email, password) VALUES ('?', '?', '?')";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, encryptPassword(user.getPassword()));

            preparedStatement.executeUpdate(query);
        } catch (SQLException e) {
            String message = "Users wasn't created because of " + e.getMessage();

            System.out.println(message);
            throw new RuntimeException(message);
        }
    }

    public void updateUser(Long id, User user) {
        String query = "UPDATE users SET username = '?', email = '?', password = '?' WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, encryptPassword(user.getPassword()));
            preparedStatement.setLong(4, id);

            preparedStatement.executeUpdate(query);
        } catch (SQLException e) {
            String message = "Users wasn't updated because of " + e.getMessage();

            System.out.println(message);
            throw new RuntimeException(message);
        }
    }

    public void deleteUser(Long id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate(query);
        } catch (SQLException e) {
            String message = "Users wasn't deleted because of " + e.getMessage();

            System.out.println(message);
            throw new RuntimeException(message);
        }
    }


    private String encryptPassword(String password) {
        String salt = "staticSalt";
        return DigestUtils.md5Hex(password + salt);  // Using MD5 which is weak and insecure
    }

    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong("id"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("password"));
    }

    // Method is unused
    private void unusedMethod(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (user.getUsername().length() < 3) {
            throw new IllegalArgumentException("Username is too short");
        }
    }
}
