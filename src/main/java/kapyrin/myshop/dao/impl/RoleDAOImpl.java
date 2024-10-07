package kapyrin.myshop.dao.impl;

import kapyrin.myshop.configuration.MyConnectionPool;
import kapyrin.myshop.dao.DAOInterfaces.RepositoryWithOneParameterInSomeMethods;
import kapyrin.myshop.exception.entities.RoleException;
import kapyrin.myshop.entities.Role;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum RoleDAOImpl implements RepositoryWithOneParameterInSomeMethods<Role> {
    INSTANCE;
    private static final String ADD_ROLE = "INSERT INTO role (user_role) VALUES (?)";
    private static final String UPDATE_ROLE = "UPDATE role SET user_role = ? WHERE id = ?";
    private static final String DELETE_ROLE = "DELETE FROM role WHERE id = ?";
    private static final String SELECT_ALL_ROLES = "SELECT * FROM role";
    private static final String SELECT_ROLE_BY_ID = "SELECT * FROM role WHERE id = ?";

    private static final Logger logger = LogManager.getLogger(RoleDAOImpl.class);

    private static final String DB_ID = "id";
    private static final String DB_USER_ROLE = "user_role";

    @Override
    public void add(Role role) {
        logger.debug("Adding role: " + role.getUserRole());
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_ROLE)) {
            preparedStatement.setString(1, role.getUserRole());
            preparedStatement.executeUpdate();
            logger.info("Added role: " + role.getUserRole());
        } catch (SQLException e) {
            logger.error(e);
            throw new RoleException("Failed to add roll", e);
        }

    }

    @Override
    public void update(Role role) {
        logger.debug("Updating role: " + role);
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROLE)) {
            preparedStatement.setString(1, role.getUserRole());
            preparedStatement.setLong(2, role.getId());
            preparedStatement.executeUpdate();
            logger.info("Updated role: " + role.getUserRole());
        } catch (SQLException e) {
            logger.error(e);
            throw new RoleException("Failed to update roll", e);
        }
    }

    @Override
    public void deleteById(long roleId) {
        logger.debug("Deleting role: " + roleId);
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROLE)) {
            preparedStatement.setLong(1, roleId);
            preparedStatement.executeUpdate();
            logger.info("Deleted role: " + roleId);
        } catch (SQLException e) {
            logger.error(e);
            throw new RoleException("Failed to delete roll", e);
        }
    }

    @Override
    public void deleteByEntity(Role role) {
        logger.debug("Deleting role: " + role.getUserRole());
        if (role == null || role.getId() == null) {
            logger.error("Role is null");
            throw new RoleException("Role is null");
        }
        long roleId = role.getId();
        deleteById(roleId);
    }

    @Override
    public List<Role> getAll() {
        logger.debug("Getting all roles");
        List<Role> roles = new ArrayList<>();
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ROLES);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                roles.add(createRoleFromResultSet(resultSet));
            }
            logger.info("Retrieved all roles");
        } catch (
                SQLException e) {
            logger.error(e);
            throw new RoleException("Failed to get all roles", e);
        }
        return roles;
    }

    @Override
    public Optional<Role> getById(long roleId) {
        logger.debug("Getting role by id: " + roleId);
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROLE_BY_ID)) {
            preparedStatement.setLong(1, roleId);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    return Optional.of(createRoleFromResultSet(set));
                }
            }
            logger.info("Getting role by id: " + roleId);
        } catch (SQLException e) {
            logger.error(e);
            throw new RoleException("Failed to get role by id", e);
        }
        return Optional.empty();
    }

    private Role createRoleFromResultSet(ResultSet set) throws SQLException {
        return Role.builder()
                .id(set.getLong(DB_ID))
                .userRole(set.getString(DB_USER_ROLE))
                .build();
    }

}
