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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet("/managers")
public class ManagerServlet extends HttpServlet {
    private UserServiceImpl userService;
    private ShopOrderServiceImpl shopOrderService;

    @Override
    public void init() throws ServletException {
        userService = UserServiceImpl.INSTANCE.initRepository(UserDAOImpl.INSTANCE);
        shopOrderService = ShopOrderServiceImpl.INSTANCE.initRepository(ShopOrderDAOImpl.INSTANCE);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userService.getAll();
        List<User> onlyCustomers = users.stream()
                .filter(user -> user.getRole().getUserRole().equals("customer"))
                .collect(Collectors.toList());
        Map<Long, List<ShopOrder>> userOrders = new HashMap<>();

        for (User user : onlyCustomers) {
            List<ShopOrder> orders = shopOrderService.getAllOrdersByUserId(user.getId());
            userOrders.put(user.getId(), (orders != null) ? orders : new ArrayList<>());
        }

        request.setAttribute("users", onlyCustomers);
        request.setAttribute("userOrders", userOrders);
        request.getRequestDispatcher("jsp/manager.jsp").forward(request, response);
    }

}
