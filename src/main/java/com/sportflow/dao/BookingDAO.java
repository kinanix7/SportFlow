package com.sportflow.dao;

import com.sportflow.config.DBConnection;
import com.sportflow.model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    // Add this method to check for existing bookings!
    public boolean bookingExists(int memberId, int sessionId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT 1 FROM bookings WHERE member_id = ? AND session_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, memberId);
            preparedStatement.setInt(2, sessionId);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Returns true if a row exists, false otherwise
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            //  if (connection != null) connection.close(); // Connection handling
        }
    }
    public void createBooking(Booking booking) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "INSERT INTO bookings (member_id, session_id) VALUES (?, ?)"; //status removed beceause default value confirmed
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, booking.getMemberId());
            preparedStatement.setInt(2, booking.getSessionId());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    booking.setId(generatedKeys.getInt(1));
                }
            }
        } finally {

            if (preparedStatement != null) preparedStatement.close();
            // if (connection != null) connection.close();
        }
    }

    // ... other methods ...
    public List<Booking> getBookingsByMemberId(int memberId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM bookings WHERE member_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, memberId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setId(resultSet.getInt("id"));
                booking.setMemberId(resultSet.getInt("member_id"));
                booking.setSessionId(resultSet.getInt("session_id"));
                booking.setBookingDate(resultSet.getTimestamp("booking_date").toLocalDateTime());
                booking.setStatus(resultSet.getString("status"));
                bookings.add(booking);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            // if (connection != null) connection.close();
        }
        return bookings;
    }

    // Method to get bookings by session ID
    public List<Booking> getBookingsBySessionId(int sessionId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM bookings WHERE session_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, sessionId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setId(resultSet.getInt("id"));
                booking.setMemberId(resultSet.getInt("member_id"));
                booking.setSessionId(resultSet.getInt("session_id"));
                booking.setBookingDate(resultSet.getTimestamp("booking_date").toLocalDateTime());
                booking.setStatus(resultSet.getString("status"));
                bookings.add(booking);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            // Consider how connection closing is handled.
        }
        return bookings;
    }

    // Method to update the status of a booking
    public void updateBookingStatus(int bookingId, String status) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "UPDATE bookings SET status = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, bookingId);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            // Consider connection management.
        }
    }

    public void deleteBooking(int bookingId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try  {
            connection = DBConnection.getConnection();
            String sql = "DELETE FROM bookings WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookingId);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            // if (connection != null) connection.close();
        }
    }
}