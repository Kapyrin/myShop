package kapyrin.myshop;


import kapyrin.myshop.exception.RoleException;
import kapyrin.myshop.exception.UserException;
import kapyrin.myshop.dao.RoleDao;
import kapyrin.myshop.dao.UserDao;
import kapyrin.myshop.entities.Role;
import kapyrin.myshop.entities.User;

import java.util.List;
import java.util.Optional;

public class Main {
    private static final RoleDao roleDao = new RoleDao();
    private static final UserDao userDao = new UserDao();

    public static void main(String[] args) {

        try {
            roleDao.add(Role.builder().userRole("admin").build());
            roleDao.add(Role.builder().userRole("manager").build());
            roleDao.add(Role.builder().userRole("customer").build());

            Optional<Role> admin = roleDao.getById(1);
            Optional<Role> manager = roleDao.getById(2);
            Optional<Role> customer = roleDao.getById(3);

            User vladimir = User.builder()
                    .id(0L)
                    .firstName("Vladimir")
                    .lastName("Kapyrin")
                    .email("vladimir.kapyrin@gmail.com")
                    .password("password")
                    .phoneNumber("1234567890")
                    .address("Russia")
                    .roleId(admin.get().getId())
                    .build();

            User bill = User.builder()
                    .id(0L)
                    .firstName("Bill")
                    .lastName("Gates")
                    .email("bill@microsot.com")
                    .password("password")
                    .phoneNumber("0987654321")
                    .address("Ostin")
                    .roleId(manager.get().getId())
                    .build();

            User ilon = User.builder()
                    .id(0L)
                    .firstName("Elon")
                    .lastName("Musk")
                    .email("elon@tesla.com")
                    .password("password")
                    .phoneNumber("1234509876")
                    .address("Redmond")
                    .roleId(customer.get().getId())
                    .build();

            userDao.add(vladimir);
            userDao.add(bill);
            userDao.add(ilon);

            List<User> allUsers = userDao.getAll();
            for (User user : allUsers) {
                System.out.println(user);
            }

            Optional<User> updated = userDao.getById(2);
            System.out.println(updated);
            if (updated.isPresent()) {
                updated.get().setFirstName("First");
                updated.get().setLastName("Last");
                updated.get().setEmail("update@email.com");
                updated.get().setPhoneNumber("6789012456");
                updated.get().setAddress("Washington");

                userDao.update(updated.orElse(null));
            } else throw new UserException("User not found");

            Optional<User> deleted = userDao.getById(3);
            System.out.println("Deleting user " + deleted.get().getFirstName() + " " + deleted.get().getLastName());
            userDao.deleteById(3);

            List<User> afterDeleteUsers = userDao.getAll();
            for (User user : afterDeleteUsers) {
                System.out.println(user);
            }

        } catch (RoleException | UserException e) {
            e.printStackTrace();
        }
    }
}

