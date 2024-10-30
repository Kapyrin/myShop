package kapyrin.myshop.dao.impl;

import kapyrin.myshop.configuration.MyHibernateConfiguration;
import kapyrin.myshop.dao.DAOInterface.RepositoryWithOneParameterInSomeMethods;
import kapyrin.myshop.entity.Product;
import kapyrin.myshop.exception.entity.ProductException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public enum ProductDAOImpl implements RepositoryWithOneParameterInSomeMethods<Product> {
    INSTANCE;
    private static final String CHECK_USING_PRODUCT_IN_ORDER = "SELECT COUNT(po) FROM ProductOrder po WHERE po.product.id = :productId";

    private static final Logger logger = LogManager.getLogger(ProductDAOImpl.class);


    @Override
    public void add(Product product) {
        logger.debug("Adding product " + product.getProductDescription());
        Transaction transaction = null;
        try (Session session = MyHibernateConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
            logger.info("The product " + product.getProductName() + " has been added successfully");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e);
            throw new ProductException("Failed to add product: " + product.getProductName(), e);
        }

    }

    @Override
    public void update(Product product) {
        logger.debug("Updating product " + product.getProductDescription());
        Transaction transaction = null;
        try (Session session = MyHibernateConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(product);
            transaction.commit();
            logger.info("The product " + product.getProductDescription() + " has been updated successfully");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e);
            throw new ProductException("Failed to update product" + product.getProductName(), e);
        }

    }

    @Override
    public void deleteById(long id) {
        logger.debug("Attempting to delete product with id " + id);
        if (existProductInAnyOrder(id)) {
            logger.warn("Cannot delete product with id " + id + "because it is associated with existing order");
            throw new ProductException("Cannot delete product because it is associated with existing order");
        }

        Transaction transaction = null;
        logger.debug("Deleting product with id " + id);
        try (Session session = MyHibernateConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product != null) {
                session.delete(product);
                logger.info("The product with id: " + id + " has been deleted successfully");
                transaction.commit();
            } else
                logger.warn("The product with id: " + id + " does not exist");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
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
        try (Session session = MyHibernateConfiguration.getSessionFactory().openSession()) {
            List<Product> products = session.createQuery("from Product", Product.class).list();
            logger.info("Received all products");
            return products;
        } catch (Exception e) {
            logger.error(e);
            throw new ProductException("Failed to retrieve all products", e);
        }
    }

    @Override
    public Optional<Product> getById(long id) {
        logger.debug("Getting product with id " + id);
        try (Session session = MyHibernateConfiguration.getSessionFactory().openSession()) {
            Product product = session.get(Product.class, id);
            if (product != null) {
                logger.info("The product with id: " + id + " has been found successfully");
                return Optional.ofNullable(product);
            }
        } catch (Exception e) {
            logger.error(e);
            throw new ProductException("Failed to retrieve product with id " + id, e);
        }
        return Optional.empty();
    }

    private boolean existProductInAnyOrder(long productId) {
        logger.debug("Checkin existing product in any order");
        try (Session session = MyHibernateConfiguration.INSTANCE.getSessionFactory().openSession()) {
            Long count = session.createQuery(CHECK_USING_PRODUCT_IN_ORDER, Long.class)
                    .setParameter("productId", productId)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            logger.error("Error checking if product is in order", e);
            throw new ProductException("Error checking if product is in order", e);
        }
    }
}