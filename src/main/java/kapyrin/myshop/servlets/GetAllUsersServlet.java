package kapyrin.myshop.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kapyrin.myshop.dao.impl.UserDAOImpl;
import kapyrin.myshop.entity.User;
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
        List<User> users = userService.getAll();

        HttpSession session = request.getSession();
        session.setAttribute("users", users);

        request.setAttribute("users", users);
        request.getRequestDispatcher("jsp/admin.jsp").forward(request, response);
    }
}