package kapyrin.myshop.configuration;

import kapyrin.myshop.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public enum  MyHibernateConfiguration {
    INSTANCE;
    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(OrderStatus.class);
            configuration.addAnnotatedClass(Product.class);
            configuration.addAnnotatedClass(ProductOrder.class);
            configuration.addAnnotatedClass(Role.class);
            configuration.addAnnotatedClass(ShopOrder.class);
            configuration.addAnnotatedClass(User.class);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}