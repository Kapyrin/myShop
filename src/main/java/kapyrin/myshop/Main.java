package kapyrin.myshop;


import kapyrin.myshop.exception.RoleException;
import kapyrin.myshop.exception.UserException;
import kapyrin.myshop.dao.impl.RoleDaoImpl;
import kapyrin.myshop.dao.impl.UserDaoImpl;
import kapyrin.myshop.entities.Role;
import kapyrin.myshop.entities.User;

import java.util.List;
import java.util.Optional;

public class Main {
    private static final RoleDaoImpl ROLE_DAO_IMPL = RoleDaoImpl.INSTANCE;
    private static final UserDaoImpl USER_DAO_IMPL = UserDaoImpl.INSTANCE;

    public static void main(String[] args) {

        try {
            ROLE_DAO_IMPL.add(Role.builder().userRole("admin").build());
            ROLE_DAO_IMPL.add(Role.builder().userRole("manager").build());
            ROLE_DAO_IMPL.add(Role.builder().userRole("customer").build());

            Optional<Role> admin = ROLE_DAO_IMPL.getById(1);
            Optional<Role> manager = ROLE_DAO_IMPL.getById(2);
            Optional<Role> customer = ROLE_DAO_IMPL.getById(3);

            User vladimir = User.builder()
                    .id(0L)
                    .firstName("Vladimir")
                    .lastName("Kapyrin")
                    .email("vladimir.kapyrin@gmail.com")
                    .password("password")
                    .phoneNumber("1234567890")
                    .address("Russia")
                    .role(admin.get())
                    .build();

            User bill = User.builder()
                    .id(0L)
                    .firstName("Bill")
                    .lastName("Gates")
                    .email("bill@microsot.com")
                    .password("password")
                    .phoneNumber("0987654321")
                    .address("Ostin")
                    .role(manager.get())
                    .build();

            User ilon = User.builder()
                    .id(0L)
                    .firstName("Elon")
                    .lastName("Musk")
                    .email("elon@tesla.com")
                    .password("password")
                    .phoneNumber("1234509876")
                    .address("Redmond")
                    .role(customer.get())
                    .build();

            USER_DAO_IMPL.add(vladimir);
            USER_DAO_IMPL.add(bill);
            USER_DAO_IMPL.add(ilon);

            List<User> allUsers = USER_DAO_IMPL.getAll();
            for (User user : allUsers) {
                System.out.println(user);
            }

            Optional<User> updated = USER_DAO_IMPL.getById(2);
            System.out.println(updated);
            if (updated.isPresent()) {
                updated.get().setFirstName("First");
                updated.get().setLastName("Last");
                updated.get().setEmail("update@email.com");
                updated.get().setPhoneNumber("6789012456");
                updated.get().setAddress("Washington");

                USER_DAO_IMPL.update(updated.orElse(null));
            } else throw new UserException("User not found");

            Optional<User> deleted = USER_DAO_IMPL.getById(3);
            System.out.println("Deleting user " + deleted.get().getFirstName() + " " + deleted.get().getLastName());
            USER_DAO_IMPL.deleteById(3);

            List<User> afterDeleteUsers = USER_DAO_IMPL.getAll();
            for (User user : afterDeleteUsers) {
                System.out.println(user);
            }

        } catch (RoleException | UserException e) {
            e.printStackTrace();
        }
    }
}

