package kapyrin.myshop.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Properties;

public abstract class BaseDAO<T> implements Repository<T> {
    private Class<T> entityClass;
    protected SessionFactory sessionFactory;


    public BaseDAO(Class<?>... classes) {
        this.entityClass = entityClass;
        try {
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("hibernate.properties"));

            Configuration configuration = new Configuration();
            for (Class<?> clazz : classes) {
                configuration.addAnnotatedClass(clazz);
            }

            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from " + getEntityClass().getSimpleName(), getEntityClass()).list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public T get(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(getEntityClass(), id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void create(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            System.out.println(getEntityClass().getSimpleName() + " has been created");
        }
    }

    public abstract void update(T entity, long id);

    @Override
    public void delete(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            System.out.println(getEntityClass().getSimpleName() + " has been deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract Class<T> getEntityClass();
}
