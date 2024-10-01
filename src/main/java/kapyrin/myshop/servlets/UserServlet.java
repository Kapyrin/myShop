package kapyrin.myshop.servlets;

import kapyrin.myshop.dao.UserDao;
import kapyrin.myshop.entities.User;
import kapyrin.myshop.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService(new UserDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html;charset=UTF-8");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Users</title>");
        out.println("</head>");
        out.println("<body>");
        String userId = req.getParameter("id");
        if (userId == null || userId.isEmpty()) {
            List<User> users = userService.getAllUsers();
            out.println("<h1>All users:</h1>");
            out.println("<ul>");
            for (User user : users) {
                out.println("<li>" + user.getId() + " " + user.getFirstName() + " " + user.getLastName() + "</li>");
            }
            out.println("</ul>");
        } else {
            Long id = Long.parseLong(userId);
            Optional<User> user = userService.getUserById(id);
            if (user.isPresent()) {
                User u = user.get();
                out.println("<h1>You chose a user:</h1>");
                out.println("<li>" + u.getId() + " " + u.getFirstName() + " " + u.getLastName() + "</li>");
            } else {
                out.println("<h1>User is invalid</h1>");
            }
        }
        out.println("</body>");
        out.println("</html>");
    }
}
