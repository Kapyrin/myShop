package kapyrin.myshop.dao.impl;


import kapyrin.myshop.configuration.MyHibernateConfiguration;
import kapyrin.myshop.dao.DAOInterface.AuthenticateUser;
import kapyrin.myshop.entity.User;
import kapyrin.myshop.exception.entity.UserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public enum UserDAOImpl implements AuthenticateUser<User> {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(UserDAOImpl.class);

    @Override
    public void add(User user) {

        logger.debug("Adding user: " + user.getLastName() + " " + user.getFirstName());
        Transaction transaction = null;
        try (Session session = MyHibernateConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            logger.info("User " + user.getLastName() + " " + user.getFirstName() + " added successfully");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e);
            throw new UserException("Failed to add user: " + user.getLastName() + " " + user.getFirstName(), e);
        }
    }


    @Override
    public void update(User user) {
        Transaction transaction = null;
        logger.debug("Updating user: " + user.getLastName() + " " + user.getFirstName());
        try (Session session = MyHibernateConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            logger.info("User" + user.getLastName() + " " + user.getFirstName() + " updated successfully");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e);
            throw new UserException("Failed to update user: " + user.getLastName() + " " + user.getFirstName(), e);
        }
    }

    @Override
    public void deleteById(long id) {
        Transaction transaction = null;
        logger.debug("Deleting user with id : " + id);
        try (Session session = MyHibernateConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                logger.info("User " + id + " deleted successfully");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e);
            throw new UserException("Failed to delete user: " + id, e);
        }
    }


    @Override
    public void deleteByEntity(User user) {
        logger.debug("Deleting user: " + user.getLastName() + " " + user.getFirstName());
        if (user == null || user.getId() == null) {
            throw new UserException("User is null");
        }
        long userId = user.getId();
        deleteById(userId);
    }

    @Override
    public List<User> getAll() {
        logger.debug("Getting all users");
        List<User> users;
        try (Session session = MyHibernateConfiguration.getSessionFactory().openSession()) {
            users = session.createQuery("FROM User", User.class).list();
            logger.info("Retrieved all users");
        } catch (Exception e) {
            logger.error(e);
            throw new UserException("Failed to retrieve all users", e);
        }
        return users;
    }

    @Override
    public Optional<User> getById(long userId) {
        logger.debug("Getting user with id: " + userId);
        try (Session session = MyHibernateConfiguration.getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);
            if (user != null) {
                return Optional.of(user);
            }
            logger.info("User with id " + userId + " not found");
        } catch (Exception e) {
            logger.error(e);
            throw new UserException("Failed to retrieve user with id: " + userId, e);
        }
        return Optional.empty();
    }
    @Override
    public Optional<User> authenticate(String email, String password) {
        logger.debug("Authenticating user with email: " + email);
        try (Session session = MyHibernateConfiguration.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery("FROM User WHERE email = :email AND password = :password", User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult());
        } catch (Exception e) {
            logger.info("Authentication failed for email: " + email);
            return Optional.empty();
        }
    }

}

