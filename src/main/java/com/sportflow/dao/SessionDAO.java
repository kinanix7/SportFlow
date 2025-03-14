// SessionDAO.java
package com.sportflow.dao;

import com.sportflow.config.DBConnection;
import com.sportflow.model.Session;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SessionDAO {

    public void createSession(Session session) {
        String sql = "INSERT INTO sessions (entrainer_id, title, description, session_date, start_time, end_time, max_participants) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, session.getEntrainerId());
            pstmt.setString(2, session.getTitle());
            pstmt.setString(3, session.getDescription());
            pstmt.setObject(4, session.getSessionDate());
            pstmt.setObject(5, session.getStartTime());
            pstmt.setObject(6, session.getEndTime());
            pstmt.setInt(7, session.getMaxParticipants());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); //  handle appropriately
        }
    }

    public List<Session> getAllSessions() {
        List<Session> sessions = new ArrayList<>();
        String sql = "SELECT * FROM sessions";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Session session = new Session();
                session.setId(rs.getInt("id"));
                session.setEntrainerId(rs.getInt("entrainer_id"));
                session.setTitle(rs.getString("title"));
                session.setDescription(rs.getString("description"));
                session.setSessionDate(rs.getDate("session_date").toLocalDate());
                session.setStartTime(rs.getTime("start_time").toLocalTime());
                session.setEndTime(rs.getTime("end_time").toLocalTime());
                session.setMaxParticipants(rs.getInt("max_participants"));
                sessions.add(session);
            }
        } catch (SQLException e) {
            e.printStackTrace(); //  handle appropriately
        }
        return sessions;
    }
    public List<Session> getSessionsByEntrainerId(Integer entrainerId) {
        List<Session> sessions = new ArrayList<>();
        String sql = "SELECT * FROM sessions WHERE entrainer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, entrainerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Session session = new Session();
                session.setId(rs.getInt("id"));
                session.setEntrainerId(rs.getInt("entrainer_id"));
                session.setTitle(rs.getString("title"));
                session.setDescription(rs.getString("description"));
                session.setSessionDate(rs.getDate("session_date").toLocalDate());
                session.setStartTime(rs.getTime("start_time").toLocalTime());
                session.setEndTime(rs.getTime("end_time").toLocalTime());
                session.setMaxParticipants(rs.getInt("max_participants"));
                sessions.add(session);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or re-throw the exception
        }
        return sessions;
    }
    public Session getSessionById(int sessionId) {
        Session session = null;
        String sql = "SELECT * FROM sessions WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, sessionId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                session = new Session();
                session.setId(rs.getInt("id"));
                session.setEntrainerId(rs.getInt("entrainer_id"));
                session.setTitle(rs.getString("title"));
                session.setDescription(rs.getString("description"));
                session.setSessionDate(rs.getDate("session_date").toLocalDate());
                session.setStartTime(rs.getTime("start_time").toLocalTime());
                session.setEndTime(rs.getTime("end_time").toLocalTime());
                session.setMaxParticipants(rs.getInt("max_participants"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or re-throw the exception
        }
        return session;
    }


    public void updateSession(Session session) {
        String sql = "UPDATE sessions SET title = ?, description = ?, session_date = ?, start_time = ?, end_time = ?, max_participants = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, session.getTitle());
            pstmt.setString(2, session.getDescription());
            pstmt.setObject(3, session.getSessionDate());
            pstmt.setObject(4, session.getStartTime());
            pstmt.setObject(5, session.getEndTime());
            pstmt.setInt(6, session.getMaxParticipants());
            pstmt.setInt(7, session.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); //  handle appropriately
        }
    }
    public void deleteSession(int sessionId) {
        String sql = "DELETE FROM sessions WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, sessionId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); //  handle appropriately
        }
    }


    // Other methods
}