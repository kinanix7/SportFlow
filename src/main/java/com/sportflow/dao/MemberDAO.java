package com.sportflow.dao;

import com.sportflow.config.DBConnection;
import com.sportflow.model.Member;
import com.sportflow.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    public void createMember(Member member) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "INSERT INTO members (user_id, first_name, last_name, birth_date, sport) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, member.getUserId());
            preparedStatement.setString(2, member.getFirstName());
            preparedStatement.setString(3, member.getLastName());
            preparedStatement.setDate(4, Date.valueOf(member.getBirthDate()));
            preparedStatement.setString(5, member.getSport());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    member.setId(generatedKeys.getInt(1));
                }
            }
        } finally {

            if (preparedStatement != null) preparedStatement.close();
            // if (connection != null) connection.close();
        }
    }


    public List<Member> getAllMembers() throws SQLException {
        List<Member> members = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try  {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM members";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Member member = new Member();
                member.setId(resultSet.getInt("id"));
                member.setUserId(resultSet.getInt("user_id"));
                member.setFirstName(resultSet.getString("first_name"));
                member.setLastName(resultSet.getString("last_name"));
                member.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
                member.setSport(resultSet.getString("sport"));
                member.setRegistrationDate(resultSet.getTimestamp("registration_date").toLocalDateTime());
                members.add(member);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
        }
        return members;
    }
    public List<Member> getAllMembersWithUserDetails() throws SQLException {
        List<Member> members = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT m.*, u.username, u.email, u.role FROM members m JOIN users u ON m.user_id = u.id";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Member member = new Member();
                member.setId(resultSet.getInt("id"));
                member.setUserId(resultSet.getInt("user_id"));
                member.setFirstName(resultSet.getString("first_name"));
                member.setLastName(resultSet.getString("last_name"));
                member.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
                member.setSport(resultSet.getString("sport"));
                member.setRegistrationDate(resultSet.getTimestamp("registration_date").toLocalDateTime());

                User user = new User();
                user.setId(resultSet.getInt("user_id")); // Use user_id from the result set
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
                member.setUser(user); // Set the user object!

                members.add(member);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
        }
        return members;
    }


    public Member getMemberById(int memberId) throws SQLException {
        Member member = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try  {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM members WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, memberId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                member = new Member();
                member.setId(resultSet.getInt("id"));
                member.setUserId(resultSet.getInt("user_id"));
                member.setFirstName(resultSet.getString("first_name"));
                member.setLastName(resultSet.getString("last_name"));
                member.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
                member.setSport(resultSet.getString("sport"));
                member.setRegistrationDate(resultSet.getTimestamp("registration_date").toLocalDateTime());
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
        }
        return member;
    }

    public void updateMember(Member member) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "UPDATE members SET first_name = ?, last_name = ?, birth_date = ?, sport = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getFirstName());
            preparedStatement.setString(2, member.getLastName());
            preparedStatement.setDate(3, Date.valueOf(member.getBirthDate()));
            preparedStatement.setString(4, member.getSport());
            preparedStatement.setInt(5, member.getId());
            preparedStatement.executeUpdate();
        } finally {

            if (preparedStatement != null) preparedStatement.close();
            // if (connection != null) connection.close();
        }
    }
    public Member getMemberByUserId(int userId) throws SQLException {
        Member member = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try  {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM members WHERE user_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                member = new Member();
                member.setId(resultSet.getInt("id"));
                member.setUserId(resultSet.getInt("user_id"));
                member.setFirstName(resultSet.getString("first_name"));
                member.setLastName(resultSet.getString("last_name"));
                member.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
                member.setSport(resultSet.getString("sport"));
                member.setRegistrationDate(resultSet.getTimestamp("registration_date").toLocalDateTime());
            }
        }  finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
        }
        return member;
    }

    public void deleteMember(int memberId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "DELETE FROM members WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, memberId);
            preparedStatement.executeUpdate();
        }  finally {

            if (preparedStatement != null) preparedStatement.close();
        }
    }
}