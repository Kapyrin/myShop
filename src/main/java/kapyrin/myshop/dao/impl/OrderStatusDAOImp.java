package kapyrin.myshop.dao.impl;

import kapyrin.myshop.configuration.MyHibernateConfiguration;
import kapyrin.myshop.dao.DAOInterface.RepositoryWithOneParameterInSomeMethods;
import kapyrin.myshop.entity.OrderStatus;
import kapyrin.myshop.exception.entity.OrderStatusException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public enum OrderStatusDAOImp implements RepositoryWithOneParameterInSomeMethods<OrderStatus> {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(OrderStatusDAOImp.class);


    @Override
    public void add(OrderStatus status) {
        Transaction transaction = null;
        logger.debug("Adding order status " + status.getStatusName());
        try (Session session = MyHibernateConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(status);
            transaction.commit();
            logger.debug("Order status added");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e);
            throw new OrderStatusException("Failed to add order status " + status.getStatusName(), e);
        }
    }

    @Override
    public void update(OrderStatus status) {
        Transaction transaction = null;
        logger.debug("Updating order status " + status.getStatusName());
        try (Session session = MyHibernateConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(status);
            transaction.commit();
            logger.info("Order status " + status.getStatusName() + " updated");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e);
            throw new OrderStatusException("Failed to update order status " + status.getStatusName(), e);
        }
    }

    @Override
    public void deleteById(long id) {
        Transaction transaction = null;
        logger.debug("Deleting order status " + id);
        try (Session session = MyHibernateConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            OrderStatus status = session.get(OrderStatus.class, id);
            if (status != null) {
                session.delete(status);
                logger.info("Order status " + id + " has been deleted");
            } else {
                logger.warn("Order status with id " + id + " not found");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
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
        try (Session session = MyHibernateConfiguration.getSessionFactory().openSession()) {

            List<OrderStatus> orderStatuses = session.createQuery("from OrderStatus", OrderStatus.class).list();

            logger.info("Retrieved all order statuses");
            return orderStatuses;
        } catch (Exception e) {
            logger.error(e);
            throw new OrderStatusException("Failed to retrieve order statuses", e);
        }
    }

    @Override
    public Optional<OrderStatus> getById(long id) {
        logger.debug("Getting order status with id " + id);
        try (Session session = MyHibernateConfiguration.getSessionFactory().openSession()) {
            OrderStatus status = session.get(OrderStatus.class, id);
            if (status != null) {
                logger.info("Retrieved order status with id " + id);
                return Optional.of(status);

            }
        } catch (Exception e) {
            logger.error(e);
            throw new OrderStatusException("Failed to retrieve order status with id " + id, e);
        }
        return Optional.empty();
    }
}

