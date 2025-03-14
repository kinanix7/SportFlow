// EntrainerDAO.java (Add this method)
package com.sportflow.dao;
import com.sportflow.config.DBConnection;
import com.sportflow.model.Entrainer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntrainerDAO {

    public void createEntrainer(Entrainer entrainer) {
        String sql = "INSERT INTO entrainers (user_id, first_name, last_name, specialty) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, entrainer.getUserId());
            pstmt.setString(2, entrainer.getFirstName());
            pstmt.setString(3, entrainer.getLastName());
            pstmt.setString(4, entrainer.getSpecialty());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            // Log and handle appropriately
        }
    }

    public List<Entrainer> getAllEntrainers() {
        List<Entrainer> entrainers = new ArrayList<>();
        String sql = "SELECT * FROM entrainers";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Entrainer entrainer = new Entrainer();
                entrainer.setId(rs.getInt("id"));
                entrainer.setUserId(rs.getInt("user_id"));
                entrainer.setFirstName(rs.getString("first_name"));
                entrainer.setLastName(rs.getString("last_name"));
                entrainer.setSpecialty(rs.getString("specialty"));
                entrainers.add(entrainer);
            }
        } catch (SQLException e) {
            e.printStackTrace(); //  handle appropriately
        }
        return entrainers;
    }
    public Entrainer getEntrainerById(int id) {
        String sql = "SELECT * FROM entrainers WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Entrainer entrainer = new Entrainer();
                    entrainer.setId(rs.getInt("id"));
                    entrainer.setUserId(rs.getInt("user_id"));
                    entrainer.setFirstName(rs.getString("first_name"));
                    entrainer.setLastName(rs.getString("last_name"));
                    entrainer.setSpecialty(rs.getString("specialty"));
                    return entrainer;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); //  handle appropriately
        }
        return null;
    }


    public void updateEntrainer(Entrainer entrainer) {
        String sql = "UPDATE entrainers SET first_name = ?, last_name = ?, specialty = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, entrainer.getFirstName());
            pstmt.setString(2, entrainer.getLastName());
            pstmt.setString(3, entrainer.getSpecialty());
            pstmt.setInt(4, entrainer.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); //  handle appropriately
        }
    }

    public void deleteEntrainer(int entrainerId) {
        String sql = "DELETE FROM entrainers WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, entrainerId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); //  handle appropriately
        }
    }

    public Entrainer getEntrainerByUserId(int userId) {
        String sql = "SELECT * FROM entrainers WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Entrainer entrainer = new Entrainer();
                    entrainer.setId(rs.getInt("id"));
                    entrainer.setUserId(rs.getInt("user_id"));
                    entrainer.setFirstName(rs.getString("first_name"));
                    entrainer.setLastName(rs.getString("last_name"));
                    entrainer.setSpecialty(rs.getString("specialty"));
                    return entrainer;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Log and handle appropriately
        }
        return null;
    }

    // Other methods as needed
}