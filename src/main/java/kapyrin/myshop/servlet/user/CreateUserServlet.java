package kapyrin.myshop.servlet.user;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kapyrin.myshop.dao.impl.RoleDAOImpl;
import kapyrin.myshop.dao.impl.UserDAOImpl;
import kapyrin.myshop.entity.User;
import kapyrin.myshop.service.impl.RoleServiceImpl;
import kapyrin.myshop.service.impl.UserServiceImpl;
import kapyrin.myshop.servlet.util.UserRequestMapper;

import java.io.IOException;

@WebServlet("/createUser")
public class CreateUserServlet extends HttpServlet {
    private UserServiceImpl userService;
    private RoleServiceImpl roleService;
    private UserRequestMapper userRequestMapper;

    @Override
    public void init() {
        userService = UserServiceImpl.INSTANCE.initRepository(UserDAOImpl.INSTANCE);
        roleService = RoleServiceImpl.INSTANCE.initRepository(RoleDAOImpl.INSTANCE);
        userRequestMapper = UserRequestMapper.INSTANCE.init(roleService);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = userRequestMapper.extractUserFromRequest(req, false);
        userService.add(user);
        User userFromDb = userService.authenticate(user.getEmail(), user.getPassword()).orElse(null);

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
                return "/index.jsp";
        }
    }
}
