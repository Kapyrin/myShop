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

@WebServlet("/editUser")
public class EditUserServlet extends HttpServlet {
    private UserServiceImpl userService;
    private RoleServiceImpl roleService;

    @Override
    public void init() {
        userService = UserServiceImpl.INSTANCE.initRepository(UserDAOImpl.INSTANCE);
        roleService = RoleServiceImpl.INSTANCE.initRepository(RoleDAOImpl.INSTANCE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdParam = request.getParameter("userId");

        if (userIdParam != null && !userIdParam.isEmpty()) {
            long userId = Long.parseLong(userIdParam);
            Optional<User> user = userService.getById(userId);

            if (user.isPresent()) {
                request.setAttribute("user", user.get());
                request.getRequestDispatcher("jsp/editUser.jsp").forward(request, response);
            } else {
                response.sendRedirect("users");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userIdParam = request.getParameter("userId");

        if (userIdParam != null) {
            long userId = Long.parseLong(userIdParam);
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String phoneNumber = request.getParameter("phoneNumber");
            String address = request.getParameter("address");
            String roleParam = request.getParameter("role");
            Role role = roleService.getByRoleName(roleParam).get();

            userService.update(User.builder()
                    .id(userId)
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .password(password)
                    .phoneNumber(phoneNumber)
                    .address(address)
                    .role(role)
                    .build());

            response.sendRedirect("users");
        } else {
            response.sendRedirect("users");
        }
    }


}
