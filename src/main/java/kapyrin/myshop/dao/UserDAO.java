package kapyrin.myshop.dao;

import configuration.MyConnectionPool;
import exception.UserException;
import kapyrin.myshop.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String ADD_USER = "INSERT INTO users (first_name, last_name, email, password, phone_number, address, role_id) VALUES (?, ?, ?, ?,?,?,?)";
    private static final String UPDATE_USER = "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ?, phone_number = ?, address = ?, role_id = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final Logger logger = LogManager.getLogger(UserDAO.class);

    public void addUser(User user) throws UserException {
        logger.debug("Adding user: " + user.getLastName() + " " + user.getFirstName());
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setInt(7, user.getRoleId());
            preparedStatement.executeUpdate();
            logger.info("User" + user.getLastName() + " " + user.getFirstName() + " added successfully");
        } catch (SQLException e) {
            logger.error(e);
            throw new UserException("Failed to add user: " + user.getLastName() + " " + user.getFirstName(), e);
        }
    }

    public void updateUser(User user) throws UserException {
        logger.debug("Updating user: " + user.getLastName() + " " + user.getFirstName());
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setInt(7, user.getRoleId());
            preparedStatement.setLong(8, user.getId());
            preparedStatement.executeUpdate();
            logger.info("User" + user.getLastName() + " " + user.getFirstName() + " updated successfully");
        } catch (SQLException e) {
            logger.error(e);
            throw new UserException("Failed to update user: " + user.getLastName() + " " + user.getFirstName(), e);
        }
    }

    public void deleteUserById(long userId) throws UserException {
        logger.debug("Deleting user with id : " + userId);
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.executeUpdate();
            logger.info("User " + userId + " deleted successfully");
        } catch (SQLException e) {
            logger.error(e);
            throw new UserException("Failed to delete user: " + userId, e);
        }
    }

    public void deleteUser(User user) throws UserException {
        logger.debug("Deleting user: " + user.getLastName() + " " + user.getFirstName());
        if (user == null) {
            throw new UserException("User is null");
        }
        long userId = user.getId();
        deleteUserById(userId);
    }


    public List<User> getAllUsers() throws UserException {
        logger.debug("Getting all users");
        List<User> users = new ArrayList<>();
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS);
             ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                long id = set.getLong("id");
                String firstName = set.getString("first_name");
                String lastName = set.getString("last_name");
                String email = set.getString("email");
                String password = set.getString("password");
                String phoneNumber = set.getString("phone_number");
                String address = set.getString("address");
                int roleId = set.getInt("role_id");
                users.add(new User(id, firstName, lastName, email, password, phoneNumber, address, roleId));
            }
            logger.info("Retrieved all users");

        } catch (SQLException e) {
            logger.error(e);
            throw new UserException("Failed to retrieve all users", e);
        }
        return users;
    }

    public User getUserById(long userId) throws UserException {
        logger.debug("Getting user with id : " + userId);
        User user = null;
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setLong(1, userId);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    long id = set.getLong("id");
                    String firstName = set.getString("first_name");
                    String lastName = set.getString("last_name");
                    String email = set.getString("email");
                    String password = set.getString("password");
                    String phoneNumber = set.getString("phone_number");
                    String address = set.getString("address");
                    int roleId = set.getInt("role_id");
                    user = new User(id, firstName, lastName, email, password, phoneNumber, address, roleId);
                }
            }
            logger.info("Retrieved user with id : " + userId);
        } catch (SQLException e) {
            logger.error(e);
            throw new UserException("Failed to retrieve user with id : " + userId, e);
        }
        return user;
    }
}

