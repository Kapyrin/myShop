package kapyrin.myshop.dao;

import configuration.MyConnectionPool;
import exception.RoleException;
import kapyrin.myshop.entities.Role;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDao {
    private static final String ADD_ROLE = "INSERT INTO role (user_role) VALUES (?)";
    private static final String UPDATE_ROLE = "UPDATE role SET user_role = ? WHERE id = ?";
    private static final String DELETE_ROLE = "DELETE FROM role WHERE id = ?";
    private static final String SELECT_ALL_ROLES = "SELECT * FROM role";
    private static final String SELECT_ROLE_BY_ID = "SELECT * FROM role WHERE id = ?";
    private static final Logger logger = LogManager.getLogger(RoleDao.class);

    public void addRole(Role role) throws RoleException {
        logger.debug("Adding role: " + role);
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

    public void updateRole(Role role) throws RoleException {
        logger.debug("Updating role: " + role);
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROLE)) {
            preparedStatement.setString(1, role.getUserRole());
            preparedStatement.setInt(2, role.getId());
            preparedStatement.executeUpdate();
            logger.info("Updated role: " + role.getUserRole());
        } catch (SQLException e) {
            logger.error(e);
            throw new RoleException("Failed to update roll", e);
        }
    }

    public void deleteRoleById(int roleId) throws RoleException {
        logger.debug("Deleting role: " + roleId);
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROLE)) {
            preparedStatement.setInt(1, roleId);
            preparedStatement.executeUpdate();
            logger.info("Deleted role: " + roleId);
        } catch (SQLException e) {
            logger.error(e);
            throw new RoleException("Failed to delete roll", e);
        }
    }

    public void deleteRole(Role role) throws RoleException {
        logger.debug("Deleting role: " + role.getUserRole());
        if (role == null) throw new RoleException("Role is null");
        int roleId = role.getId();
        deleteRoleById(roleId);
    }


    public List<Role> getAllRoles() throws RoleException {
        logger.debug("Getting all roles");
        List<Role> roles = new ArrayList<>();
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ROLES);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String userRole = resultSet.getString("user_role");
                roles.add(new Role(id, userRole));
            }
            logger.info("Retrieved all roles");
        } catch (
                SQLException e) {
            logger.error(e);
            throw new RoleException("Failed to get all roles", e);
        }
        return roles;
    }

    public Role getRoleById(int roleId) throws RoleException {
        logger.debug("Getting role by id: " + roleId);
        Role role = null;
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROLE_BY_ID)) {
            preparedStatement.setInt(1, roleId);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    int id = set.getInt("id");
                    String userRole = set.getString("user_role");
                    role = new Role(id, userRole);
                }
            }
            logger.info("Getting role by id: " + roleId);
        } catch (SQLException e) {
            logger.error(e);
            throw new RoleException("Failed to get role by id", e);
        }
        return role;
    }

}
