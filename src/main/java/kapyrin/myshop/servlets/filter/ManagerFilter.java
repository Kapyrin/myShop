package kapyrin.myshop.servlets.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kapyrin.myshop.entity.User;

import java.io.IOException;

@WebFilter(urlPatterns = {"/editProduct", "/managers", "/addProduct", "/products"})
public class ManagerFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !"manager".equals(user.getRole().getUserRole())) {
            session.setAttribute("errorMessage", "You do not have permission to access this resource, try to login again: ");
            res.sendRedirect("jsp/login.jsp");
            return;
        }
        chain.doFilter(req, res);
    }
}

