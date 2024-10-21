package kapyrin.myshop.dao.impl;

import kapyrin.myshop.configuration.MyHibernateConfiguration;
import kapyrin.myshop.dao.DAOInterface.RepositoryGetOrdersByUserId;
import kapyrin.myshop.entity.ShopOrder;
import kapyrin.myshop.exception.entity.OrderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public enum ShopOrderDAOImpl implements RepositoryGetOrdersByUserId<ShopOrder> {
    INSTANCE;

    private static final String GET_ORDER_BY_USER_ID = "from ShopOrder where customer.id = :userId";
    private static final Logger logger = LogManager.getLogger(ShopOrderDAOImpl.class);


    @Override
    public void add(ShopOrder order) {
        logger.info("Adding order from user: {} {}", order.getCustomer().getFirstName(), order.getCustomer().getLastName());
        Transaction transaction = null;
        try (Session session = MyHibernateConfiguration.INSTANCE.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
            logger.info("Added order with ID: {}", order.getId());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e);
            throw new OrderException("Error adding order: " + order.getId(), e);
        }

    }

    @Override
    public void update(ShopOrder order) {
        logger.debug("Updating order: " + order.getId());
        Transaction transaction = null;
        try (Session session = MyHibernateConfiguration.INSTANCE.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(order);
            transaction.commit();
            logger.info("Updated order: " + order.getId());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e);
            throw new OrderException("Error updating order: " + order.getId(), e);
        }
    }

    @Override
    public void deleteById(long id) {
        logger.debug("Deleting order: " + id);
        Transaction transaction = null;
        try (Session session = MyHibernateConfiguration.INSTANCE.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            ShopOrder order = session.get(ShopOrder.class, id);
            if (order != null) {
                session.delete(order);
                transaction.commit();
                logger.info("Deleted order with ID: " + id);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
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
        try (Session session = MyHibernateConfiguration.INSTANCE.getSessionFactory().openSession()) {
            List<ShopOrder> orders = session.createQuery("from ShopOrder order").list();
            logger.info("Retrieved all orders");
            return orders;
        } catch (Exception e) {
            logger.error(e);
            throw new OrderException("Error retrieving orders", e);
        }
    }

    @Override
    public Optional<ShopOrder> getById(long id) {
        logger.debug("Getting order by id: " + id);
        try (Session session = MyHibernateConfiguration.INSTANCE.getSessionFactory().openSession()) {
            ShopOrder order = session.get(ShopOrder.class, id);
            logger.info("Retrieved order by id: " + id);
            return Optional.ofNullable(order);
        } catch (Exception e) {
            logger.error(e);
            throw new OrderException("Error retrieving order with id " + id, e);
        }
    }

    @Override
    public List<ShopOrder> getAllOrdersByUserId(long userId) {
        logger.debug("Getting all orders by userId: " + userId);
        try (Session session = MyHibernateConfiguration.INSTANCE.getSessionFactory().openSession()) {
            List<ShopOrder> ordersFromUserId = session.createQuery(GET_ORDER_BY_USER_ID, ShopOrder.class)
                    .setParameter("userId", userId)
                    .list();
                 logger.info("Retrieved orders by customer id: " + userId);
            return ordersFromUserId;
        } catch (Exception e) {
            logger.error(e);
            throw new OrderException("Error retrieving orders by customer id: " + userId, e);
        }
    }
}
