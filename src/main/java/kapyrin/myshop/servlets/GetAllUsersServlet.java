package kapyrin.myshop.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kapyrin.myshop.dao.impl.UserDAOImpl;
import kapyrin.myshop.entities.User;
import kapyrin.myshop.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class GetAllUsersServlet extends HttpServlet {
    private UserServiceImpl userService;

    @Override
    public void init() {
        userService = UserServiceImpl.INSTANCE.initRepository(UserDAOImpl.INSTANCE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("user");

        if (loggedInUser != null && loggedInUser.getRole().getUserRole().equals("admin")) {
            List<User> users = userService.getAll();
            request.setAttribute("users", users);
            request.getRequestDispatcher("jsp/admin.jsp").forward(request, response);
        } else {
            response.sendRedirect("index.html");
        }
    }
}