package com.sportflow.dao;

import com.sportflow.config.DBConnection;
import com.sportflow.model.Session;
import com.sportflow.model.Member; // Import Member
import com.sportflow.model.Booking; // Import Booking
import com.sportflow.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SessionDAO {

    public void createSession(Session session) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "INSERT INTO sessions (entrainer_id, title, description, session_date, start_time, end_time, max_participants) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, session.getEntrainerId());
            preparedStatement.setString(2, session.getTitle());
            preparedStatement.setString(3, session.getDescription());
            preparedStatement.setDate(4, Date.valueOf(session.getSessionDate()));
            preparedStatement.setTime(5, Time.valueOf(session.getStartTime()));
            preparedStatement.setTime(6, Time.valueOf(session.getEndTime()));
            preparedStatement.setInt(7, session.getMaxParticipants());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    session.setId(generatedKeys.getInt(1));
                }
            }
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            // if (connection != null) connection.close(); // Connection management
        }
    }

    public List<Session> getAllSessions() throws SQLException {
        List<Session> sessions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM sessions";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Session session = new Session();
                session.setId(resultSet.getInt("id"));
                session.setEntrainerId(resultSet.getInt("entrainer_id"));
                session.setTitle(resultSet.getString("title"));
                session.setDescription(resultSet.getString("description"));
                session.setSessionDate(resultSet.getDate("session_date").toLocalDate());
                session.setStartTime(resultSet.getTime("start_time").toLocalTime());
                session.setEndTime(resultSet.getTime("end_time").toLocalTime());
                session.setMaxParticipants(resultSet.getInt("max_participants"));
                session.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                sessions.add(session);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            // Connection closing
        }
        return sessions;
    }


    // Add methods for getting sessions by entrainer ID, member ID (via bookings), updating, and deleting.
    public List<Session> getSessionsByEntrainerId(int entrainerId) throws SQLException {
        List<Session> sessions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try  {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM sessions WHERE entrainer_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, entrainerId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Session session = new Session();
                session.setId(resultSet.getInt("id"));
                session.setEntrainerId(resultSet.getInt("entrainer_id"));
                session.setTitle(resultSet.getString("title"));
                session.setDescription(resultSet.getString("description"));
                session.setSessionDate(resultSet.getDate("session_date").toLocalDate());
                session.setStartTime(resultSet.getTime("start_time").toLocalTime());
                session.setEndTime(resultSet.getTime("end_time").toLocalTime());
                session.setMaxParticipants(resultSet.getInt("max_participants"));
                session.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                sessions.add(session);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            // if (connection != null) connection.close();
        }
        return sessions;
    }
    public Session getSessionById(int sessionId) throws SQLException {
        Session session = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM sessions WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, sessionId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                session = new Session();
                session.setId(resultSet.getInt("id"));
                session.setEntrainerId(resultSet.getInt("entrainer_id"));
                session.setTitle(resultSet.getString("title"));
                session.setDescription(resultSet.getString("description"));
                session.setSessionDate(resultSet.getDate("session_date").toLocalDate());
                session.setStartTime(resultSet.getTime("start_time").toLocalTime());
                session.setEndTime(resultSet.getTime("end_time").toLocalTime());
                session.setMaxParticipants(resultSet.getInt("max_participants"));
                session.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            // if (connection != null) connection.close(); // Connection management
        }
        return session;
    }

    public void updateSession(Session session) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "UPDATE sessions SET title = ?, description = ?, session_date = ?, start_time = ?, end_time = ?, max_participants = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, session.getTitle());
            preparedStatement.setString(2, session.getDescription());
            preparedStatement.setDate(3, Date.valueOf(session.getSessionDate()));
            preparedStatement.setTime(4, Time.valueOf(session.getStartTime()));
            preparedStatement.setTime(5, Time.valueOf(session.getEndTime()));
            preparedStatement.setInt(6, session.getMaxParticipants());
            preparedStatement.setInt(7, session.getId()); // Corrected: Use session.getId() for the WHERE clause
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            // if (connection != null) connection.close(); // Consider connection pooling
        }
    }



    public void deleteSession(int sessionId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try  {
            connection = DBConnection.getConnection();
            String sql = "DELETE FROM sessions WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, sessionId);
            preparedStatement.executeUpdate();
        } finally {

            if (preparedStatement != null) preparedStatement.close();
            //if (connection != null) connection.close();
        }
    }
    // New method to get members enrolled in a session
    public List<Member> getMembersBySessionId(int sessionId) throws SQLException {
        List<Member> members = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            // Join bookings, members, and users tables
            String sql = "SELECT m.*, u.username, u.email, u.role " +
                    "FROM bookings b " +
                    "JOIN members m ON b.member_id = m.id " +
                    "JOIN users u ON m.user_id = u.id " + //Join users
                    "WHERE b.session_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, sessionId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Member member = new Member();
                member.setId(resultSet.getInt("id"));
                member.setUserId(resultSet.getInt("user_id"));
                member.setFirstName(resultSet.getString("first_name"));
                member.setLastName(resultSet.getString("last_name"));
                member.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
                member.setSport(resultSet.getString("sport"));
                // Set user details (create and populate a User object)
                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
                member.setUser(user); // Set the User object in Member
                members.add(member);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            // Connection closing as before
        }
        return members;
    }
}