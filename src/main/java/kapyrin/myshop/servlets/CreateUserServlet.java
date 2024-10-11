package kapyrin.myshop.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kapyrin.myshop.dao.impl.RoleDAOImpl;
import kapyrin.myshop.dao.impl.UserDAOImpl;
import kapyrin.myshop.entities.Role;
import kapyrin.myshop.entities.User;
import kapyrin.myshop.service.impl.RoleServiceImpl;
import kapyrin.myshop.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/createUser")
public class CreateUserServlet extends HttpServlet {
    private UserServiceImpl userService;
    private RoleServiceImpl roleService;

    @Override
    public void init() {
        userService = UserServiceImpl.INSTANCE.initRepository(UserDAOImpl.INSTANCE);
        roleService = RoleServiceImpl.INSTANCE.initRepository(RoleDAOImpl.INSTANCE);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phoneNumber = req.getParameter("phoneNumber");
        String address = req.getParameter("address");
        String role = req.getParameter("role");

        Optional<Role> userRole = roleService.getByRoleName(role);
        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .address(address)
                .role(userRole.get())
                .build();
        userService.add(user);

        String redirectURL;
        switch (role.toLowerCase()) {
            case "admin":
                redirectURL = "/admin";
                break;
            case "manager":
                redirectURL = "/managers";
                break;
            case "customer":
                redirectURL = "/userOrders";
                break;
            default:
                redirectURL = "/index.html";
                break;
        }
        resp.sendRedirect(redirectURL);
    }
}
