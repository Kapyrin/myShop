package kapyrin.myshop.servlet.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kapyrin.myshop.dao.impl.ProductOrderDaoImpl;
import kapyrin.myshop.dao.impl.ShopOrderDAOImpl;
import kapyrin.myshop.dao.impl.UserDAOImpl;
import kapyrin.myshop.entity.ProductOrder;
import kapyrin.myshop.entity.ShopOrder;
import kapyrin.myshop.entity.User;
import kapyrin.myshop.service.impl.ProductOrderServiceImpl;
import kapyrin.myshop.service.impl.ShopOrderServiceImpl;
import kapyrin.myshop.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/customerOrders")
public class CustomerOrdersServlet extends HttpServlet {
    private UserServiceImpl userService;
    private ShopOrderServiceImpl shopOrderService;
    private ProductOrderServiceImpl productOrderService;

    @Override
    public void init() throws ServletException {
        userService = UserServiceImpl.INSTANCE.initRepository(UserDAOImpl.INSTANCE);
        shopOrderService = ShopOrderServiceImpl.INSTANCE.initRepository(ShopOrderDAOImpl.INSTANCE);
        productOrderService = ProductOrderServiceImpl.INSTANCE.initRepository(ProductOrderDaoImpl.INSTANCE);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("user");

        Long userId = loggedInUser.getId();
        System.out.println(userId);
        List<ShopOrder> orders = shopOrderService.getAllOrdersByUserId(userId);
        Map<ShopOrder, Double> orderTotalAmounts = calculateTotalAmountForOrders(orders);

        session.setAttribute("orders", orders);
        session.setAttribute("orderTotalAmounts", orderTotalAmounts);

        request.setAttribute("user", loggedInUser);
        request.setAttribute("orders", orders);
        request.setAttribute("orderTotalAmounts", orderTotalAmounts);
        request.getRequestDispatcher("jsp/order/userOrders.jsp").forward(request, response);

    }

    private Map<ShopOrder, Double> calculateTotalAmountForOrders(List<ShopOrder> orders) {
        Map<ShopOrder, Double> orderTotalAmounts = new HashMap<>();

        List<ProductOrder> allProductOrders = productOrderService.getAll();
        for (ShopOrder order : orders) {
            double totalAmount = 0;


            List<ProductOrder> productOrdersForOrder = allProductOrders.stream()
                    .filter(po -> po.getOrder().getId().equals(order.getId()))
                    .collect(Collectors.toList());

            for (ProductOrder productOrder : productOrdersForOrder) {
                totalAmount += productOrder.getProduct().getPrice() * productOrder.getQuantity();
            }

            orderTotalAmounts.put(order, totalAmount);
        }

        return orderTotalAmounts;
    }
}

