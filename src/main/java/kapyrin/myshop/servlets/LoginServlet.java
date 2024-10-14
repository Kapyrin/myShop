package kapyrin.myshop.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kapyrin.myshop.dao.impl.UserDAOImpl;
import kapyrin.myshop.entities.User;
import kapyrin.myshop.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserServiceImpl userService;

    @Override
    public void init() {
        userService = UserServiceImpl.INSTANCE.initRepository(UserDAOImpl.INSTANCE);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Optional<User> user = userService.authenticate(email, password);
        if (user.isPresent()) {
            User userDB = user.get();
            String userRole = user.get().getRole().getUserRole();
            HttpSession session = request.getSession();
            session.setAttribute("user", userDB);

            switch (userRole) {
                case "admin":
                    response.sendRedirect("users");
                    break;
                case "manager":
                    response.sendRedirect("managers");
                    break;
                case "customer":
                    response.sendRedirect("customerOrders");
                    break;
                default:
                    response.sendRedirect("index.html");
                    break;
            }
        } else {
            response.getWriter().println("Invalid email or password.");
        }
    }
}
