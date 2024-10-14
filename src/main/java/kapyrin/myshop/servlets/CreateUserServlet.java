package kapyrin.myshop.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        User userFromDb = UserServiceImpl.INSTANCE.authenticate(user.getEmail(), user.getPassword()).orElse(null);

        HttpSession session = req.getSession();
        session.setAttribute("user", userFromDb);
        String redirectURL = redirectURL(userFromDb.getRole().getUserRole());
        resp.sendRedirect(redirectURL);
    }

    private String redirectURL(String roleName) {
        switch (roleName.toLowerCase()) {
            case "admin":
                return "/users";
            case "manager":
                return "/managers";
            case "customer":
                return "/customerOrders";
            default:
                return "/index.html";
        }
    }
}
