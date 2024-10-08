package kapyrin.myshop.dao.impl;

import kapyrin.myshop.configuration.MyConnectionPool;
import kapyrin.myshop.dao.DAOInterfaces.RepositoryWithTwoParametersInSomeMethods;
import kapyrin.myshop.entities.Product;
import kapyrin.myshop.entities.ProductOrder;

import kapyrin.myshop.entities.ShopOrder;
import kapyrin.myshop.exception.entities.ProductException;
import kapyrin.myshop.exception.entities.ProductOrderException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum ProductOrderDaoImpl implements RepositoryWithTwoParametersInSomeMethods<ProductOrder> {
    INSTANCE;
    private static final String ADD_PRODUCT_ORDER = "INSERT INTO product_order (order_id, product_id, quantity) VALUES (?, ?, ?)";
    private static final String UPDATE_PRODUCT_ORDER = "UPDATE product_order SET quantity = ? WHERE order_id = ? AND product_id = ?";
    private static final String DELETE_PRODUCT_ORDER = "DELETE FROM product_order WHERE order_id = ? AND product_id = ?";
    private static final String SELECT_ALL_PRODUCT_ORDERS = "SELECT * FROM product_order";
    private static final String SELECT_PRODUCT_ORDER_BY_ID = "SELECT * FROM product_order WHERE order_id = ? AND product_id = ?";
    private static final String UPDATE_PRODUCT_QUANTITY = "UPDATE product SET product_remain = ? WHERE id = ?";

    private static final Logger logger = LogManager.getLogger(ProductOrderDaoImpl.class);

    private static final String DB_ORDER_ID = "order_id";
    private static final String DB_PRODUCT_ID = "product_id";
    private static final String DB_QUANTITY = "quantity";

    //    @Override
//    public void add(ProductOrder productOrder) {
//        logger.debug("Adding product order");
//        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT_ORDER)) {
//            preparedStatement.setLong(1, productOrder.getOrder().getId());
//            preparedStatement.setLong(2, productOrder.getProduct().getId());
//            preparedStatement.setInt(3, productOrder.getQuantity());
//            preparedStatement.executeUpdate();
//            logger.info("Added product order");
//        } catch (SQLException e) {
//            logger.error(e);
//            throw new ProductOrderException("Failed to add product order", e);
//        }
//
//    }
    @Override
    public void add(ProductOrder productOrder) {
        logger.debug("Adding product order");
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection()) {
            Optional<Product> optionalProduct = ProductDAOImpl.INSTANCE.getById(productOrder.getProduct().getId());
            Product product = optionalProduct.orElseThrow(() -> new ProductException("Product not found with id: " + productOrder.getProduct().getId()));

            int availableQuantity = product.getProductRemain();
            int requiredQuantity = productOrder.getQuantity();

            if (availableQuantity < requiredQuantity) {
                throw new ProductOrderException("Not enough products in stock. Available: " + availableQuantity + ", Required: " + requiredQuantity);
            }
            try (PreparedStatement updateProductStatement = connection.prepareStatement(UPDATE_PRODUCT_QUANTITY)) {
                updateProductStatement.setInt(1, availableQuantity - requiredQuantity);
                updateProductStatement.setLong(2, productOrder.getProduct().getId());
                updateProductStatement.executeUpdate();
            }
            logger.info("Product quantity updated");

            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT_ORDER)) {
                preparedStatement.setLong(1, productOrder.getOrder().getId());
                preparedStatement.setLong(2, productOrder.getProduct().getId());
                preparedStatement.setInt(3, requiredQuantity);
                preparedStatement.executeUpdate();
            }
            logger.info("Successfully added product order and updated product quantity");
        } catch (SQLException e) {
            logger.error(e);
            throw new ProductOrderException("Failed to add product order", e);
        }
    }


    @Override
    public void update(ProductOrder productOrder) {
        logger.debug("Updating product order with orderId: {} and productId: {}", productOrder.getOrder().getId(), productOrder.getProduct().getId());
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_ORDER)) {
            preparedStatement.setInt(1, productOrder.getQuantity());
            preparedStatement.setLong(2, productOrder.getOrder().getId());
            preparedStatement.setLong(3, productOrder.getProduct().getId());
            preparedStatement.executeUpdate();
            logger.info("Updated product order with orderId: {} and productId: {}", productOrder.getOrder().getId(), productOrder.getProduct().getId());
        } catch (SQLException e) {
            logger.error(e);
            throw new ProductOrderException("Failed to update product order", e);
        }

    }

    @Override
    public void deleteById(long orderId, long productId) {
        logger.debug("Deleting product order with orderId: {} and productId: {}", orderId, productId);
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_ORDER)) {
            preparedStatement.setLong(1, orderId);
            preparedStatement.setLong(2, productId);
            preparedStatement.executeUpdate();
            logger.info("Deleted product order with orderId: {} and productId: {}", orderId, productId);
        } catch (SQLException e) {
            logger.error(e);
            throw new ProductOrderException("Failed to delete product order", e);
        }

    }

    @Override
    public void deleteByEntity(ProductOrder productOrder) {
        logger.debug("Deleting product order: {} {}",
                (productOrder == null ? "null" : productOrder.getOrder().getId()),
                (productOrder == null ? "null" : productOrder.getProduct().getId()));
        if (productOrder == null || productOrder.getOrder() == null || productOrder.getProduct() == null) {
            throw new ProductOrderException("Failed to delete product order");
        }
        long orderId = productOrder.getOrder().getId();
        long productId = productOrder.getProduct().getId();
        deleteById(orderId, productId);
    }


    @Override
    public List<ProductOrder> getAll() {
        logger.debug("Getting all product orders");
        List<ProductOrder> productOrders = new ArrayList<>();
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCT_ORDERS);
             ResultSet set = preparedStatement.executeQuery()) {
            while (set.next()) {
                productOrders.add(createProductOrderFromResultSet(set));
            }
            logger.info("Getting all product orders");
        } catch (SQLException e) {
            logger.error(e);
            throw new ProductOrderException("Failed to get all product orders", e);
        }
        return productOrders;
    }

    @Override
    public Optional<ProductOrder> getByIds(long orderId, long productId) {
        logger.debug("Getting ProductOrder with orderId: {} and productId: {}", orderId, productId);
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_ORDER_BY_ID)) {
            preparedStatement.setLong(1, orderId);
            preparedStatement.setLong(2, productId);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    ProductOrder productOrder = createProductOrderFromResultSet(set);
                    return Optional.of(productOrder);
                }
                logger.info("Getting product order with orderId: {} and productId: {}", orderId, productId);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new ProductOrderException("Failed to get product order", e);
        }
        return Optional.empty();
    }

    private ProductOrder createProductOrderFromResultSet(ResultSet set) throws SQLException {
        long orderId = set.getLong(DB_ORDER_ID);
        Optional<ShopOrder> optionalShopOrder = ShopOrderDAOImpl.INSTANCE.getById(orderId);
        ShopOrder shopOrder = optionalShopOrder.orElseThrow(() -> new ProductOrderException("Product order not found with id: " + orderId));

        long productId = set.getLong(DB_PRODUCT_ID);
        Optional<Product> optionalProduct = ProductDAOImpl.INSTANCE.getById(productId);
        Product product = optionalProduct.orElseThrow(() -> new ProductException("Product not found with id: " + productId));

        int quantity = set.getInt(DB_QUANTITY);

        return ProductOrder.builder()
                .order(shopOrder)
                .product(product)
                .quantity(quantity)
                .build();
    }
}

