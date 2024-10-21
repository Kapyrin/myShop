package kapyrin.myshop.dao.impl;

import kapyrin.myshop.configuration.MyHibernateConfiguration;
import kapyrin.myshop.dao.DAOInterface.RepositoryWithTwoParametersInSomeMethods;
import kapyrin.myshop.entity.Product;
import kapyrin.myshop.entity.ProductOrder;
import kapyrin.myshop.entity.ProductOrderKey;
import kapyrin.myshop.exception.entity.ProductException;
import kapyrin.myshop.exception.entity.ProductOrderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public enum ProductOrderDaoImpl implements RepositoryWithTwoParametersInSomeMethods<ProductOrder> {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(ProductOrderDaoImpl.class);


    @Override
    public void add(ProductOrder productOrder) {
        Transaction transaction = null;
        logger.debug("Adding product order");
        try (Session session = MyHibernateConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Optional<Product> optionalProduct = Optional.ofNullable(session.get(Product.class, productOrder.getProduct().getId()));
            Product product = optionalProduct.orElseThrow(() -> new ProductException("Product not found with id: " + productOrder.getProduct().getId()));

            int availableQuantity = product.getProductRemain();
            int requiredQuantity = productOrder.getQuantity();

            if (availableQuantity < requiredQuantity) {
                throw new ProductOrderException("Not enough products in stock. Available: " + availableQuantity + ", Required: " + requiredQuantity);
            }
            product.setProductRemain(availableQuantity - requiredQuantity);
            session.update(product);
            session.save(productOrder);
            transaction.commit();
            logger.info("Successfully added product order and updated product quantity");
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback());
                logger.error(e);
            throw new ProductOrderException("Failed to add product order", e);
        }
    }


    @Override
    public void update(ProductOrder productOrder) {
        Transaction transaction = null;
        logger.debug("Updating product order with orderId: {} and productId: {}", productOrder.getOrder().getId(), productOrder.getProduct().getId());
        try (Session session = MyHibernateConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(productOrder);
            transaction.commit();
            logger.info("Updated product order with orderId: {} and productId: {}", productOrder.getOrder().getId(), productOrder.getProduct().getId());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e);
            throw new ProductOrderException("Failed to update product order", e);
        }

    }

    @Override
    public void deleteById(long orderId, long productId) {
        Transaction transaction = null;
        logger.debug("Deleting product order with orderId: {} and productId: {}", orderId, productId);
        try (Session session = MyHibernateConfiguration.getSessionFactory().openSession()) {
            ProductOrder productOrder = session.get(ProductOrder.class, new ProductOrderKey(orderId, productId));
            if (productOrder == null) {
                throw new ProductException("Product not found with id: " + productId);
            }
            transaction = session.beginTransaction();
            session.delete(productOrder);
            transaction.commit();
            logger.info("Deleted product order with orderId: {} and productId: {}", orderId, productId);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
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
        try (Session session = MyHibernateConfiguration.INSTANCE.getSessionFactory().openSession()) {
            List<ProductOrder> productOrderList = session.createQuery("from ProductOrder").list();
            logger.info("Getting all product orders");
            return productOrderList;
        } catch (Exception e) {
            logger.error(e);
            throw new ProductOrderException("Failed to get all product orders", e);
        }
    }

    @Override
    public Optional<ProductOrder> getByIds(long orderId, long productId) {
        logger.debug("Getting ProductOrder with orderId: {} and productId: {}", orderId, productId);
        try (Session session = MyHibernateConfiguration.INSTANCE.getSessionFactory().openSession()) {
            ProductOrder productOrder = session.get(ProductOrder.class, new ProductOrderKey(orderId, productId));
            logger.info("Getting product order with orderId: {} and productId: {}", orderId, productId);
            return Optional.ofNullable(productOrder);
        } catch (Exception e) {
            logger.error(e);
            throw new ProductOrderException("Failed to get product order", e);
        }
    }
}

