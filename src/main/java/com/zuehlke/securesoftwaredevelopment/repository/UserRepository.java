package com.zuehlke.securesoftwaredevelopment.repository;

import com.zuehlke.securesoftwaredevelopment.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);

    private DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User findUser(String username) {
        String query = "SELECT id, username, password, passwordChangeAttempts, isBlocked FROM users WHERE username='" + username + "'";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            if (rs.next()) {
                int id = rs.getInt(1);
                String username1 = rs.getString(2);
                String password = rs.getString(3);
                int passwordChangeAttempts = rs.getInt(4);
                Boolean isBlocked = rs.getBoolean(5);
                return new User(id, username1, password, passwordChangeAttempts, isBlocked);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean validCredentials(String username, String password) {
        String query = "SELECT username FROM users WHERE username='" + username + "' AND password='" + password + "'";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void delete(int userId) {
        String query = "DELETE FROM users WHERE id = " + userId;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean changePassword(String username, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE username = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, username);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean incPasswordChangeAttempts(String username, int passwordChangeAttempts) {
        String query = "UPDATE users SET passwordChangeAttempts = ? WHERE username = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, passwordChangeAttempts);
            preparedStatement.setString(2, username);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean blockUser(String username) {
        String query = "UPDATE users SET isBlocked = ? WHERE username = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, username);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
