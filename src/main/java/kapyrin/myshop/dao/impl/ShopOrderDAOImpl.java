package kapyrin.myshop.dao.impl;

import kapyrin.myshop.configuration.MyConnectionPool;
import kapyrin.myshop.dao.DAOInterfaces.RepositoryWithOneParameterInSomeMethods;
import kapyrin.myshop.entities.OrderStatus;
import kapyrin.myshop.entities.ShopOrder;
import kapyrin.myshop.entities.User;
import kapyrin.myshop.exception.entities.OrderException;
import kapyrin.myshop.exception.entities.OrderStatusException;
import kapyrin.myshop.exception.entities.UserException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum ShopOrderDAOImpl implements RepositoryWithOneParameterInSomeMethods<ShopOrder> {
    INSTANCE;
    private static final String ADD_ORDER = "INSERT INTO shop_order (customer_id, order_creation_date, order_close_date, status_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_ORDER = "UPDATE shop_order SET customer_id = ?, order_creation_date = ?, order_close_date = ?, status_id = ? WHERE id = ?";
    private static final String DELETE_ORDER = "DELETE FROM shop_order WHERE id = ?";
    private static final String SELECT_ALL_ORDERS = "SELECT * FROM shop_order";
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM shop_order WHERE id = ?";
    private static final String SELECT_ORDERS_BY_CUSTOMER_ID = "SELECT * FROM shop_order WHERE customer_id = ?";

    private static final Logger logger = LogManager.getLogger(ShopOrderDAOImpl.class);

    private static final String DB_ID = "id";
    private static final String DB_CUSTOMER_ID = "customer_id";
    private static final String DB_ORDER_CREATION_DATE = "order_creation_date";
    private static final String DB_ORDER_CLOSE_DATE = "order_close_date";
    private static final String DB_STATUS = "status_id";


    @Override
    public void add(ShopOrder order) {
        logger.info("Adding order from user: {} {}", order.getCustomer().getFirstName(), order.getCustomer().getLastName());
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_ORDER)) {
            setPreparedStatementFields(statement, order);
            statement.executeUpdate();
            logger.info("Added order with ID: {}", order.getId());
        } catch (SQLException e) {
            logger.error(e);
            throw new OrderException("Error adding order: " + order.getId(), e);
        }

    }

    @Override
    public void update(ShopOrder order) {
        logger.debug("Updating order: " + order.getId());
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER)) {
            setPreparedStatementFields(statement, order);
            statement.setLong(5, order.getId());
            statement.executeUpdate();
            logger.info("Updated order: " + order.getId());
        } catch (SQLException e) {
            logger.error(e);
            throw new OrderException("Error updating order: " + order.getId(), e);
        }
    }

    @Override
    public void deleteById(long id) {
        logger.debug("Deleting order: " + id);
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ORDER)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            logger.info("Deleted order: " + id);
        } catch (SQLException e) {
            logger.error(e);
            throw new OrderException("Error deleting order: " + id, e);
        }
    }

    @Override
    public void deleteByEntity(ShopOrder order) {
        if (order == null || order.getId() == null) {
            logger.error("Deleting order is null");
            throw new OrderException("Error deleting order is null");
        }
        deleteById(order.getId());
    }

    @Override
    public List<ShopOrder> getAll() {
        logger.debug("Getting all orders");
        List<ShopOrder> orders = new ArrayList<>();
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ORDERS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                orders.add(createShopOrderFromResultSet(resultSet));
            }
            logger.info("Retrieved all orders");
        } catch (SQLException e) {
            logger.error(e);
            throw new OrderException("Error retrieving orders", e);
        }
        return orders;
    }

    @Override
    public Optional<ShopOrder> getById(long id) {
        logger.debug("Getting order by id: " + id);
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ORDER_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    return Optional.of(createShopOrderFromResultSet(set));
                }
            }
            logger.info("Retrieved order by id: " + id);
        } catch (SQLException e) {
            logger.error(e);
            throw new OrderException("Error retrieving order with id " + id, e);
        }
        return Optional.empty();
    }

    public List<ShopOrder> getAllOrdersByUserId(long userId) {
        logger.debug("Getting all orders by userId: " + userId);
        List<ShopOrder> orders = new ArrayList<>();
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ORDERS_BY_CUSTOMER_ID)) {
            statement.setLong(1, userId);
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    orders.add(createShopOrderFromResultSet(set));
                }
                logger.info("Retrieved orders by customer id: " + userId);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new OrderException("Error retrieving orders by customer id: " + userId, e);
        }
        return orders;
    }

    private void setPreparedStatementFields(PreparedStatement statement, ShopOrder order) throws SQLException {
        statement.setLong(1, order.getCustomer().getId());
        statement.setDate(2, order.getOrderCreationDate());
        statement.setDate(3, order.getOrderCloseDate());
        statement.setLong(4, order.getStatus().getId());
    }

    private ShopOrder createShopOrderFromResultSet(ResultSet resultSet) throws SQLException {
        long userId = resultSet.getLong(DB_CUSTOMER_ID);
        Optional<User> userOptional = UserDAOImpl.INSTANCE.getById(userId);
        User user = userOptional.orElseThrow(() -> new UserException("User not found with id " + userId));

        long orderStatusId = resultSet.getLong(DB_STATUS);
        Optional<OrderStatus> optionalOrderStatus = OrderStatusDAOImp.INSTANCE.getById(orderStatusId);
        OrderStatus status = optionalOrderStatus.orElseThrow(() -> new OrderStatusException("Order status not found with id " + orderStatusId));

        return ShopOrder.builder()
                .id(resultSet.getLong(DB_ID))
                .customer(user)
                .orderCreationDate(resultSet.getDate(DB_ORDER_CREATION_DATE))
                .orderCloseDate(resultSet.getDate(DB_ORDER_CLOSE_DATE))
                .status(status)
                .build();

    }
}
