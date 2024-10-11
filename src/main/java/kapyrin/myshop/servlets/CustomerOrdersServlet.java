package kapyrin.myshop.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kapyrin.myshop.dao.impl.ShopOrderDAOImpl;
import kapyrin.myshop.dao.impl.UserDAOImpl;
import kapyrin.myshop.entities.ShopOrder;
import kapyrin.myshop.entities.User;
import kapyrin.myshop.service.impl.ShopOrderServiceImpl;
import kapyrin.myshop.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.*;

@WebServlet("/customerOrders")
public class CustomerOrdersServlet extends HttpServlet {
    private UserServiceImpl userService;
    private ShopOrderServiceImpl shopOrderService;

    @Override
    public void init() throws ServletException {
        userService = UserServiceImpl.INSTANCE.initRepository(UserDAOImpl.INSTANCE);
        shopOrderService = ShopOrderServiceImpl.INSTANCE.initRepository(ShopOrderDAOImpl.INSTANCE);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        System.out.println(user);

        if (user != null) {
            Long userId = user.getId();
            List<ShopOrder> orders = shopOrderService.getAllOrdersByUserId(userId);

            request.setAttribute("user", user);
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("jsp/userOrders.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User must be logged in.");
        }
    }

}

