package kapyrin.myshop.dao;

import kapyrin.myshop.entities.Role;
import kapyrin.myshop.entities.User;
import org.hibernate.Session;


public class UserDAO extends BaseDAO<User> {
    public UserDAO() {
        super(User.class, Role.class);
    }

    public void update(User entity, long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User existingUser = session.get(User.class, id);
            if (existingUser != null) {
                existingUser.setFirstName(entity.getFirstName());
                existingUser.setLastName(entity.getLastName());
                existingUser.setEmail(entity.getEmail());
                existingUser.setPassword(entity.getPassword());
                existingUser.setAddress(entity.getAddress());
                existingUser.setPhoneNumber(entity.getPhoneNumber());
                session.update(existingUser);
                session.getTransaction().commit();
                System.out.println("User " + entity.getFirstName() + " " + entity.getLastName() + " has been saved");
            } else {
                System.out.println("User with ID " + id + " does not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

}
