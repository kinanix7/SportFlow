package com.sportflow.dao;

import com.sportflow.config.DBConnection;
import com.sportflow.model.User;
import com.sportflow.util.HashUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public User authenticate(String username, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try  {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Verify hashed password
                String hashedPassword = resultSet.getString("password");
                if (HashUtil.checkPassword(password, hashedPassword)) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setRole(resultSet.getString("role"));
                    user.setEmail(resultSet.getString("email"));
                    user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                    return user;
                }
            }
            return null; // Authentication failed
        }finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
//             if (connection != null) connection.close();
        }
    }

    public void createUser(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try  {
            connection = DBConnection.getConnection();
            // Hash the password before storing!
            String hashedPassword = HashUtil.hashPassword(user.getPassword());

            String sql = "INSERT INTO users (username, password, role, email) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, hashedPassword); // Store the hash
            preparedStatement.setString(3, user.getRole());
            preparedStatement.setString(4, user.getEmail());

            preparedStatement.executeUpdate();

            // Get the auto-generated user ID
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
        }finally {

            if (preparedStatement != null) preparedStatement.close();
            //if (connection != null) connection.close();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try  {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM users";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password")); // Consider if you want to return the hashed password
                user.setRole(resultSet.getString("role"));
                user.setEmail(resultSet.getString("email"));
                user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                users.add(user);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            // if (connection != null) connection.close();
        }
        return users;
    }

    // Add other CRUD methods (getUserById, updateUser, deleteUser) as needed.  Follow the same pattern.
    public User getUserById(int userId) throws SQLException {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try  {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                user.setEmail(resultSet.getString("email"));
                user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            }
        }  finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            //if (connection != null) connection.close();
        }
        return user;
    }

    public void updateUser(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "UPDATE users SET username = ?, role = ?, email = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getRole());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.executeUpdate();
        } finally {

            if (preparedStatement != null) preparedStatement.close();
            // if (connection != null) connection.close();
        }
    }

    public void deleteUser(int userId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try  {
            connection = DBConnection.getConnection();
            String sql = "DELETE FROM users WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } finally {

            if (preparedStatement != null) preparedStatement.close();
            // if (connection != null) connection.close();
        }
    }

    public List<User> getAllMembers() throws SQLException {
        List<User> members = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try  {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE role = 'MEMBER'";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User member = new User();
                member.setId(resultSet.getInt("id"));
                member.setUsername(resultSet.getString("username"));
                member.setEmail(resultSet.getString("email"));
                member.setRole(resultSet.getString("role"));
                member.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                members.add(member);
            }
        }finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            //if (connection != null) connection.close();
        }
        return members;
    }

    public List<User> getAllEntrainers() throws SQLException {
        List<User> entrainers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE role = 'ENTRAINER'";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User entrainer = new User();
                entrainer.setId(resultSet.getInt("id"));
                entrainer.setUsername(resultSet.getString("username"));
                entrainer.setEmail(resultSet.getString("email"));
                entrainer.setRole(resultSet.getString("role"));
                entrainer.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                entrainers.add(entrainer);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            // if (connection != null) connection.close();
        }
        return entrainers;
    }
}