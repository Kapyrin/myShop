package kapyrin.myshop.dao;

import kapyrin.myshop.entities.Role;
import org.hibernate.Session;

public class RoleDAO extends BaseDAO<Role> {
    public RoleDAO() {
        super(Role.class);
    }

    @Override
    public void update(Role entity, long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Role role = session.get(Role.class, id);
            if (role != null) {
                role.setUserRole(entity.getUserRole());
                session.update(role);
                session.getTransaction().commit();
                System.out.println("Role updated");
            } else {
                System.out.println("Role with current id not find");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }


}
