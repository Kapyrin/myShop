package kapyrin.myshop.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("user");

        if (loggedInUser != null && loggedInUser.getRole().getUserRole().equals("customer")) {
            Long userId = loggedInUser.getId();
            System.out.println(userId);
            List<ShopOrder> orders = shopOrderService.getAllOrdersByUserId(userId);

            request.setAttribute("user", loggedInUser);
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("jsp/userOrders.jsp").forward(request, response);
        } else {
            response.sendRedirect("index.html");
        }
    }

}

