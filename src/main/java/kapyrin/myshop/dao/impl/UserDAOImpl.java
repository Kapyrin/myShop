package kapyrin.myshop.dao.impl;

import kapyrin.myshop.configuration.MyConnectionPool;
import kapyrin.myshop.dao.DAOInterfaces.AuthenticateUser;
import kapyrin.myshop.dao.DAOInterfaces.RepositoryWithOneParameterInSomeMethods;
import kapyrin.myshop.entities.Role;
import kapyrin.myshop.exception.entities.UserException;
import kapyrin.myshop.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum UserDAOImpl implements AuthenticateUser<User> {
    INSTANCE;
    private static final String ADD_USER = "INSERT INTO users (first_name, last_name, email, password, phone_number, address, role_id) VALUES (?, ?, ?, ?,?,?,?)";
    private static final String UPDATE_USER = "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ?, phone_number = ?, address = ?, role_id = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String AUTHENTICATE_USER = "SELECT id, first_name, last_name, email, password, phone_number, address, role_id FROM users WHERE email = ? AND password = ?";


    private static final Logger logger = LogManager.getLogger(UserDAOImpl.class);

    private static final String DB_ID = "id";
    private static final String DB_FIRST_NAME = "first_name";
    private static final String DB_LAST_NAME = "last_name";
    private static final String DB_EMAIL = "email";
    private static final String DB_PASSWORD = "password";
    private static final String DB_PHONE_NUMBER = "phone_number";
    private static final String DB_ADDRESS = "address";
    private static final String DB_ROLE_ID = "role_id";


    @Override
    public void add(User user) {
        logger.debug("Adding user: " + user.getLastName() + " " + user.getFirstName());
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            setPreparedStatementFields(preparedStatement, user);
            preparedStatement.executeUpdate();
            logger.info("User" + user.getLastName() + " " + user.getFirstName() + " added successfully");
        } catch (SQLException e) {
            logger.error(e);
            throw new UserException("Failed to add user: " + user.getLastName() + " " + user.getFirstName(), e);
        }
    }

    @Override
    public void update(User user) {
        logger.debug("Updating user: " + user.getLastName() + " " + user.getFirstName());
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            setPreparedStatementFields(preparedStatement, user);
            preparedStatement.setLong(8, user.getId());
            preparedStatement.executeUpdate();
            logger.info("User" + user.getLastName() + " " + user.getFirstName() + " updated successfully");
        } catch (SQLException e) {
            logger.error(e);
            throw new UserException("Failed to update user: " + user.getLastName() + " " + user.getFirstName(), e);
        }
    }

    @Override
    public void deleteById(long id) {
        logger.debug("Deleting user with id : " + id);
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            logger.info("User " + id + " deleted successfully");
        } catch (SQLException e) {
            logger.error(e);
            throw new UserException("Failed to delete user: " + id, e);
        }
    }


    @Override
    public void deleteByEntity(User user) {
        logger.debug("Deleting user: " + user.getLastName() + " " + user.getFirstName());
        if (user == null || user.getId() == null) {
            throw new UserException("User is null");
        }
        long userId = user.getId();
        deleteById(userId);
    }

    @Override
    public List<User> getAll() {
        logger.debug("Getting all users");
        List<User> users = new ArrayList<>();
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS);
             ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                users.add(createUserFromResultSet(set));
            }
            logger.info("Retrieved all users");

        } catch (SQLException e) {
            logger.error(e);
            throw new UserException("Failed to retrieve all users", e);
        }
        return users;
    }

    @Override
    public Optional<User> getById(long userId) {
        logger.debug("Getting user with id : " + userId);
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setLong(1, userId);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    return Optional.of(createUserFromResultSet(set));
                }
            }
            logger.info("Retrieved user with id : " + userId);
        } catch (SQLException e) {
            logger.error(e);
            throw new UserException("Failed to retrieve user with id : " + userId, e);
        }
        return Optional.empty();
    }
@Override
    public Optional<User> authenticate(String email, String password) {
        logger.debug("Authenticating user with email : " + email);
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AUTHENTICATE_USER)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    return Optional.of(createUserFromResultSet(set));
                }
            }
            logger.info("Authenticated user with email : " + email);
        } catch (SQLException e) {
            logger.error(e);
            throw new UserException("Failed to authenticate user with email : " + email, e);
        }
        return Optional.empty();
    }

    private User createUserFromResultSet(ResultSet set) throws SQLException {
        long roleId = set.getLong(DB_ROLE_ID);
        Optional<Role> optionalRole = RoleDAOImpl.INSTANCE.getById(roleId);
        Role role = optionalRole.orElseThrow(() -> new UserException("Failed to retrieve role with id : " + roleId));

        return User.builder()
                .id(set.getLong(DB_ID))
                .firstName(set.getString(DB_FIRST_NAME))
                .lastName(set.getString(DB_LAST_NAME))
                .email(set.getString(DB_EMAIL))
                .password(set.getString(DB_PASSWORD))
                .phoneNumber(set.getString(DB_PHONE_NUMBER))
                .address(set.getString(DB_ADDRESS))
                .role(role)
                .build();
    }

    private void setPreparedStatementFields(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, user.getPhoneNumber());
        preparedStatement.setString(6, user.getAddress());
        preparedStatement.setLong(7, user.getRole().getId());
    }
}

