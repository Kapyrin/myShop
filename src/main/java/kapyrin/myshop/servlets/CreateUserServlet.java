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
import kapyrin.myshop.servlets.util.UserRequestMapper;

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
        UserRequestMapper.INSTANCE.init(roleService);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = UserRequestMapper.INSTANCE.extractUserFromRequest(req, false);
        userService.add(user);
        String redirectURL = redirectURL(user.getRole().getUserRole());
        resp.sendRedirect(redirectURL);
    }

    private String redirectURL(String roleName) {
        switch (roleName.toLowerCase()) {
            case "admin":
                return "/admin";
            case "manager":
                return "/managers";
            case "customer":
                return "/userOrders";
            default:
                return "/index.html";
        }
    }
}
