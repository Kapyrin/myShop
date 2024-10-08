package kapyrin.myshop.dao.impl;

import kapyrin.myshop.configuration.MyConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kapyrin.myshop.dao.DAOInterfaces.RepositoryWithOneParameterInSomeMethods;
import kapyrin.myshop.entities.OrderStatus;
import kapyrin.myshop.exception.entities.OrderStatusException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum OrderStatusDAOImp implements RepositoryWithOneParameterInSomeMethods<OrderStatus> {
    INSTANCE;
    private static final String ADD_ORDER_STATUS = "INSERT INTO order_status(status_name) VALUES (?)";
    private static final String UPDATE_ORDER_STATUS = "UPDATE order_status SET status_name = ? WHERE id = ?";
    private static final String DELETE_ORDER_STATUS = "DELETE FROM order_status WHERE id = ?";
    private static final String SELECT_ORDER_STATUS_BY_ID = "SELECT * FROM order_status WHERE id = ?";
    private static final String SELECT_ALL_ORDER_STATUS = "SELECT * FROM order_status";

    private static final Logger logger = LogManager.getLogger(OrderStatusDAOImp.class);

    private static final String DB_ID = "id";
    private static final String DB_STATUS = "status_name";

    @Override
    public void add(OrderStatus status) {
        logger.debug("Adding order status " + status.getStatusName());
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDER_STATUS)) {
            preparedStatement.setString(1, status.getStatusName());
            preparedStatement.executeUpdate();
            logger.debug("Order status added");
        } catch (SQLException e) {
            logger.error(e);
            throw new OrderStatusException("Failed to add order status " + status.getStatusName(), e);
        }
    }

    @Override
    public void update(OrderStatus status) {
        logger.debug("Updating order status " + status.getStatusName());
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS)) {
            preparedStatement.setString(1, status.getStatusName());
            preparedStatement.setLong(2, status.getId());
            preparedStatement.executeUpdate();
            logger.info("Order status " + status.getStatusName() + " updated");
        } catch (SQLException e) {
            logger.error(e);
            throw new OrderStatusException("Failed to update order status " + status.getStatusName(), e);
        }
    }

    @Override
    public void deleteById(long id) {
        logger.debug("Deleting order status " + id);
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER_STATUS)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            logger.info("Order status " + id + " has been deleted");
        } catch (SQLException e) {
            logger.error(e);
            throw new OrderStatusException("Failed to delete order status " + id, e);
        }

    }

    @Override
    public void deleteByEntity(OrderStatus status) {
        logger.debug("Deleting order status ");
        if (status == null || status.getId() == null) {
            logger.error("Order status is null");
            throw new OrderStatusException("Order status is null");
        }
        deleteById(status.getId());
        logger.info("The status " + status.getStatusName() + " has been deleted");
    }

    @Override
    public List<OrderStatus> getAll() {
        logger.debug("Getting all order status ");
        List<OrderStatus> orderStatuses = new ArrayList<>();
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDER_STATUS);
             ResultSet set = preparedStatement.executeQuery()) {
            while (set.next()) {
                orderStatuses.add(createOrderStatusFromResultSet(set));
            }
            logger.info("Retrieved all order statuses");
        } catch (SQLException e) {
            logger.error(e);
            throw new OrderStatusException("Failed to retrieve order statuses", e);
        }
        return orderStatuses;
    }

    @Override
    public Optional<OrderStatus> getById(long id) {
        logger.debug("Getting order status with id " + id);
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_STATUS_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(createOrderStatusFromResultSet(resultSet));
                }
            }
            logger.info("Retrieved order status with id " + id);
        } catch (SQLException e) {
            logger.error(e);
            throw new OrderStatusException("Failed to retrieve order status with id " + id, e);
        }
        return Optional.empty();
    }

    private OrderStatus createOrderStatusFromResultSet(ResultSet resultSet) throws SQLException {
        return OrderStatus.builder()
                .id(resultSet.getLong(DB_ID))
                .statusName(resultSet.getString(DB_STATUS))
                .build();
    }
}
