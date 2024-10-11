package kapyrin.myshop.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kapyrin.myshop.dao.impl.UserDAOImpl;
import kapyrin.myshop.service.impl.UserServiceImpl;

import java.io.IOException;

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {
    private UserServiceImpl userService;

    @Override
    public void init() {
        userService = UserServiceImpl.INSTANCE.initRepository(UserDAOImpl.INSTANCE);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long userId = Long.parseLong(req.getParameter("userId"));
        userService.deleteById(userId);
        resp.sendRedirect("users");
    }
}
