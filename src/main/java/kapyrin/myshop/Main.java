package kapyrin.myshop;


import configuration.MyConnectionPool;
import exception.RoleException;
import exception.UserException;
import kapyrin.myshop.dao.RoleDao;
import kapyrin.myshop.dao.UserDAO;
import kapyrin.myshop.entities.Role;
import kapyrin.myshop.entities.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        try (Connection connection = MyConnectionPool.INSTANCE.getConnection()) {
//            System.out.println(connection.getTransactionIsolation());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        try {
            RoleDao roleDao = new RoleDao();
            UserDAO userDao = new UserDAO();

            roleDao.addRole(new Role("admin"));
            roleDao.addRole(new Role("manager"));
            roleDao.addRole(new Role("customer"));

            Role admin = roleDao.getRoleById(1);
            Role manager = roleDao.getRoleById(2);
            Role customer = roleDao.getRoleById(3);

            User vladimir = new User(0L, "Vladimir", "Kapyrin", "vladimir.kapyrin@gmail.com", "password", "1234567890", "Russia", admin.getId());
            User bill = new User(0L, "Bill", "Gates", "bill@microsot.com", "password", "0987654321", "Ostin", manager.getId());
            User ilon = new User(0L, "Elon", "Musk", "elon@tesla.com", "password", "1234509876", "Redmond", customer.getId());

            userDao.addUser(vladimir);
            userDao.addUser(bill);
            userDao.addUser(ilon);

            List<User> allUsers = userDao.getAllUsers();
            for (User user : allUsers) {
                System.out.println(user);
            }

            User updated = userDao.getUserById(2);
            System.out.println(updated);
            if (updated != null) {
                updated.setFirstName("First");
                updated.setLastName("Last");
                updated.setEmail("update@email.com");
                updated.setPhoneNumber("6789012456");
                updated.setAddress("Washington");

                userDao.updateUser(updated);
            } else throw new UserException("User not found");

            userDao.updateUser(updated);
            System.out.println(updated);

            User deleted = userDao.getUserById(3);
            System.out.println("Deleting user " + deleted.getFirstName() + " " + deleted.getLastName());
            userDao.deleteUserById(3);

            List<User> afterDeleteUsers = userDao.getAllUsers();
            for (User user : afterDeleteUsers) {
                System.out.println(user);
            }


        } catch (RoleException | UserException e) {
            e.printStackTrace();
        }
    }
}

