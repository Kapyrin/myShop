package kapyrin.myshop.dao.impl;

import kapyrin.myshop.configuration.MyConnectionPool;
import kapyrin.myshop.dao.DAOInterfaces.RepositoryWithOneParameterInSomeMethods;
import kapyrin.myshop.entities.Product;
import kapyrin.myshop.exception.entities.ProductException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum ProductDAOImpl implements RepositoryWithOneParameterInSomeMethods<Product> {
    INSTANCE;
    private static final String ADD_PRODUCT = "INSERT INTO product (product_name, product_description, price, product_remain) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_PRODUCT = "UPDATE product SET product_name = ?, product_description = ?, price = ?, product_remain = ? WHERE id = ?";
    private static final String DELETE_PRODUCT = "DELETE FROM product WHERE id = ?";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM product WHERE id = ?";
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM product";

    private static final Logger logger = LogManager.getLogger(ProductDAOImpl.class);

    private static final String DB_ID = "id";
    private static final String DB_PRODUCT_NAME = "product_name";
    private static final String DB_PRODUCT_DESCRIPTION = "product_description";
    private static final String DB_PRICE = "price";
    private static final String DB_PRODUCT_REMAIN = "product_remain";

    @Override
    public void add(Product product) {
        logger.debug("Adding product " + product.getProductDescription());
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT)) {
            setPreparedStatementFields(preparedStatement, product);
            preparedStatement.executeUpdate();
            logger.info("The product " + product.getProductName() + " has been added successfully");
        } catch (SQLException e) {
            logger.error(e);
            throw new ProductException("Failed to add product: " + product.getProductName(), e);
        }

    }

    @Override
    public void update(Product product) {
        logger.debug("Updating product " + product.getProductDescription());
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT)) {
            setPreparedStatementFields(preparedStatement, product);
            preparedStatement.setLong(5, product.getId());
            preparedStatement.executeUpdate();
            logger.info("The product " + product.getProductDescription() + " has been updated successfully");
        } catch (SQLException e) {
            logger.error(e);
            throw new ProductException("Failed to update product" + product.getProductName(), e);
        }

    }

    @Override
    public void deleteById(long id) {
        logger.debug("Deleting product with id " + id);
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            logger.info("The product with id: " + id + " has been deleted successfully");
        } catch (SQLException e) {
            logger.error(e);
            throw new ProductException("Failed to delete product with id: " + id, e);
        }

    }

    @Override
    public void deleteByEntity(Product product) {
        logger.debug("Deleting product  " + product.getProductName());
        if (product == null || product.getId() == null) {
            throw new ProductException("The product is null");
        }
        long productId = product.getId();
        deleteById(productId);
        logger.info("The product " + product.getProductName() + " has been deleted successfully");
    }

    @Override
    public List<Product> getAll() {
        logger.debug("Getting all products");
        List<Product> products = new ArrayList<>();
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);
             ResultSet set = preparedStatement.executeQuery()) {
            while (set.next()) {
                products.add(createProductFromResultSet(set));
            }
            logger.info("Received all products");
        } catch (SQLException e) {
            logger.error(e);
            throw new ProductException("Failed to retrieve all products", e);
        }
        return products;
    }

    @Override
    public Optional<Product> getById(long id) {
        logger.debug("Getting product with id " + id);
        try (Connection connection = MyConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    logger.info("Received product with id " + id);
                    return Optional.of(createProductFromResultSet(set));
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new ProductException("Failed to retrieve product with id " + id, e);
        }
        return Optional.empty();
    }


    private Product createProductFromResultSet(ResultSet resultSet) throws SQLException {
        return Product.builder()
                .id(resultSet.getLong(DB_ID))
                .productName(resultSet.getString(DB_PRODUCT_NAME))
                .productDescription(resultSet.getString(DB_PRODUCT_DESCRIPTION))
                .price(resultSet.getDouble(DB_PRICE))
                .productRemain(resultSet.getInt(DB_PRODUCT_REMAIN))
                .build();
    }

    private void setPreparedStatementFields(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setString(1, product.getProductName());
        preparedStatement.setString(2, product.getProductDescription());
        preparedStatement.setDouble(3, product.getPrice());
        preparedStatement.setInt(4, product.getProductRemain());

    }
}
