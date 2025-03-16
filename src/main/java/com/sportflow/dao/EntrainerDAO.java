package com.sportflow.dao;

import com.sportflow.config.DBConnection;
import com.sportflow.model.Entrainer;
import com.sportflow.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntrainerDAO {

    public void createEntrainer(Entrainer entrainer) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "INSERT INTO entrainers (user_id, first_name, last_name, specialty) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, entrainer.getUserId());
            preparedStatement.setString(2, entrainer.getFirstName());
            preparedStatement.setString(3, entrainer.getLastName());
            preparedStatement.setString(4, entrainer.getSpecialty());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entrainer.setId(generatedKeys.getInt(1));
                }
            }
        } finally {

            if (preparedStatement != null) preparedStatement.close();
            //  if (connection != null) connection.close();
        }
    }


    public List<Entrainer> getAllEntrainers() throws SQLException {
        List<Entrainer> entrainers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try  {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM entrainers";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Entrainer entrainer = new Entrainer();
                entrainer.setId(resultSet.getInt("id"));
                entrainer.setUserId(resultSet.getInt("user_id"));
                entrainer.setFirstName(resultSet.getString("first_name"));
                entrainer.setLastName(resultSet.getString("last_name"));
                entrainer.setSpecialty(resultSet.getString("specialty"));
                entrainers.add(entrainer);
            }
        }finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            //  if (connection != null) connection.close();
        }
        return entrainers;
    }
    public List<Entrainer> getAllEntrainersWithUserDetails() throws SQLException {
        List<Entrainer> entrainers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT e.*, u.username, u.email,u.role FROM entrainers e JOIN users u ON e.user_id = u.id";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Entrainer entrainer = new Entrainer();
                entrainer.setId(resultSet.getInt("id"));
                entrainer.setUserId(resultSet.getInt("user_id"));
                entrainer.setFirstName(resultSet.getString("first_name"));
                entrainer.setLastName(resultSet.getString("last_name"));
                entrainer.setSpecialty(resultSet.getString("specialty"));

                // Add user details
                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
                entrainer.setUser(user); //Set the User Object
                entrainers.add(entrainer);
            }
        }  finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            // if (connection != null) connection.close(); // Consider connection pooling
        }
        return entrainers;
    }


    public Entrainer getEntrainerById(int entrainerId) throws SQLException {
        Entrainer entrainer = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM entrainers WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, entrainerId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                entrainer = new Entrainer();
                entrainer.setId(resultSet.getInt("id"));
                entrainer.setUserId(resultSet.getInt("user_id"));
                entrainer.setFirstName(resultSet.getString("first_name"));
                entrainer.setLastName(resultSet.getString("last_name"));
                entrainer.setSpecialty(resultSet.getString("specialty"));
            }
        }finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            // if (connection != null) connection.close();
        }
        return entrainer;
    }

    public void updateEntrainer(Entrainer entrainer) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "UPDATE entrainers SET first_name = ?, last_name = ?, specialty = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entrainer.getFirstName());
            preparedStatement.setString(2, entrainer.getLastName());
            preparedStatement.setString(3, entrainer.getSpecialty());
            preparedStatement.setInt(4, entrainer.getId());
            preparedStatement.executeUpdate();
        } finally {

            if (preparedStatement != null) preparedStatement.close();
            // if (connection != null) connection.close();
        }
    }
    public Entrainer getEntrainerByUserId(int userId) throws SQLException {
        Entrainer entrainer = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM entrainers WHERE user_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                entrainer = new Entrainer();
                entrainer.setId(resultSet.getInt("id"));
                entrainer.setUserId(resultSet.getInt("user_id"));
                entrainer.setFirstName(resultSet.getString("first_name"));
                entrainer.setLastName(resultSet.getString("last_name"));
                entrainer.setSpecialty(resultSet.getString("specialty"));
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            // Consider if connection should be closed here, or managed externally (connection pool).
        }
        return entrainer;
    }


    public void deleteEntrainer(int entrainerId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "DELETE FROM entrainers WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, entrainerId);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            // Consider connection closing strategy.
        }
    }
}