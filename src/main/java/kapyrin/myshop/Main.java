package kapyrin.myshop;

import kapyrin.myshop.dao.RoleDAO;
import kapyrin.myshop.dao.UserDAO;
import kapyrin.myshop.entities.Role;
import kapyrin.myshop.entities.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        RoleDAO roleDAO = new RoleDAO();

        Role adminRole = new Role();
        adminRole.setUserRole("admin");
        roleDAO.create(adminRole);
        System.out.println(adminRole.getUserRole());
        Role managerRole = new Role();
        managerRole.setUserRole("manager");
        roleDAO.create(managerRole);

        Role customerRole = new Role();
        customerRole.setUserRole("customer");
        roleDAO.create(customerRole);


        UserDAO userDAO = new UserDAO();

        User elonUser = new User();
        elonUser.setFirstName("Elon");
        elonUser.setLastName("Mask");
        elonUser.setEmail("elon.mask@gmail.com");
        elonUser.setPhoneNumber("+176534556519");
        elonUser.setPassword("password");
        elonUser.setAddress("USA");
        elonUser.setRole(customerRole);
        userDAO.create(elonUser);


        User billGates = new User();
        billGates.setFirstName("Bill");
        billGates.setLastName("Gates");
        billGates.setEmail("bill.gates@gmail.com");
        billGates.setPhoneNumber("+16735664495");
        billGates.setPassword("password");
        billGates.setAddress("USA");
        billGates.setRole(managerRole);
        userDAO.create(billGates);

        List<User> userList = userDAO.getAll();
        System.out.println(userList);


        User billCheckingUser = userDAO.get(2);
        System.out.println(billCheckingUser);

        User vladimirUser = new User();
        vladimirUser.setFirstName("Vladimir");
        vladimirUser.setLastName("Kapyrin");
        vladimirUser.setEmail("vladimir.kapyrin@gmail.com");
        vladimirUser.setPhoneNumber("+7999 7057796");
        vladimirUser.setPassword("password");
        vladimirUser.setAddress("Russia");
        vladimirUser.setRole(adminRole);
        userDAO.update(vladimirUser, 2);
        User updatedUser = userDAO.get(2);
        System.out.println(updatedUser);

        userDAO.delete(updatedUser);

    }
}
