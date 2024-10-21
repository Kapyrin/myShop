package kapyrin.myshop.dao.impl;

import kapyrin.myshop.configuration.MyHibernateConfiguration;
import kapyrin.myshop.dao.DAOInterface.RepositoryWithOneParameterInSomeMethods;
import kapyrin.myshop.entity.Role;
import kapyrin.myshop.exception.entity.RoleException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public enum RoleDAOImpl implements RepositoryWithOneParameterInSomeMethods<Role> {
    INSTANCE;

    private static final String GET_ROLE_BY_NAME = "FROM Role WHERE userRole = :roleName";
    private static final Logger logger = LogManager.getLogger(RoleDAOImpl.class);


    @Override
    public void add(Role role) {
        logger.debug("Adding role: " + role.getUserRole());
        Transaction transaction = null;
        try (Session session = MyHibernateConfiguration.INSTANCE.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(role);
            transaction.commit();
            logger.info("Added role: " + role.getUserRole());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e);
            throw new RoleException("Failed to add roll", e);
        }

    }

    @Override
    public void update(Role role) {
        logger.debug("Updating role: " + role);
        Transaction transaction = null;
        try (Session session = MyHibernateConfiguration.INSTANCE.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(role);
            transaction.commit();
            logger.info("Updated role: " + role.getUserRole());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e);
            throw new RoleException("Failed to update roll", e);
        }
    }

    @Override
    public void deleteById(long roleId) {
        logger.debug("Deleting role: " + roleId);
        Transaction transaction = null;
        try (Session session = MyHibernateConfiguration.INSTANCE.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Role role = session.get(Role.class, roleId);
            if (role != null) {
                session.delete(role);
                transaction.commit();
                logger.info("Deleted role: " + roleId);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e);
            throw new RoleException("Failed to delete roll", e);
        }
    }

    @Override
    public void deleteByEntity(Role role) {
        logger.debug("Deleting role: " + role.getUserRole());
        if (role == null || role.getId() == null) {
            logger.error("Role is null");
            throw new RoleException("Role is null");
        }
        long roleId = role.getId();
        deleteById(roleId);
    }

    @Override
    public List<Role> getAll() {
        logger.debug("Getting all roles");
        try (Session session = MyHibernateConfiguration.INSTANCE.getSessionFactory().openSession()) {
            List<Role> roles = session.createQuery("from Role").list();
            logger.info("Retrieved all roles");
            return roles;
        } catch (Exception e) {
            logger.error(e);
            throw new RoleException("Failed to get all roles", e);
        }
    }

    @Override
    public Optional<Role> getById(long roleId) {
        logger.debug("Getting role by id: " + roleId);
        try (Session session = MyHibernateConfiguration.INSTANCE.getSessionFactory().openSession()) {
            Role role = session.get(Role.class, roleId);
            logger.info("Getting role by id: " + roleId);
            return Optional.ofNullable(role);
        } catch (Exception e) {
            logger.error(e);
            throw new RoleException("Failed to get role by id", e);
        }
    }

    public Optional<Role> getByRoleName(String roleName) {
        logger.debug("Getting role by role name: " + roleName);
        try (Session session = MyHibernateConfiguration.INSTANCE.getSessionFactory().openSession()) {
            Role role = session.createQuery(GET_ROLE_BY_NAME, Role.class)
                    .setParameter("roleName", roleName)
                    .uniqueResult();
            logger.info("Getting role by role name: " + roleName);
            return Optional.ofNullable(role);
        } catch (Exception e) {
            logger.error(e);
            throw new RoleException("Failed to get role by role name", e);
        }
    }
}
