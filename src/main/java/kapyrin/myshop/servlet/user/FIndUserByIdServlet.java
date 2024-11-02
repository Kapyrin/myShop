package kapyrin.myshop.servlet.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kapyrin.myshop.dao.impl.UserDAOImpl;
import kapyrin.myshop.entity.User;
import kapyrin.myshop.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/findUserById")
public class FIndUserByIdServlet extends HttpServlet {
    private UserServiceImpl userService;

    @Override
    public void init() {
        userService = UserServiceImpl.INSTANCE.initRepository(UserDAOImpl.INSTANCE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long userId = Long.parseLong(req.getParameter("userId"));
        Optional<User> foundUser = userService.getById(userId);

        if (foundUser.isPresent()) {
            req.setAttribute("foundUser", foundUser.get());
        } else {
            req.setAttribute("errorMessage", "User not found");

        }

        List<User> users = userService.getAll();
        req.setAttribute("users", users);

        req.getRequestDispatcher("/jsp/user/admin.jsp").forward(req, resp);

    }
}

