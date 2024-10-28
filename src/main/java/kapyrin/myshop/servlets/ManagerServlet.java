package kapyrin.myshop.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kapyrin.myshop.dao.impl.*;
import kapyrin.myshop.entity.OrderStatus;
import kapyrin.myshop.entity.Product;
import kapyrin.myshop.entity.ShopOrder;
import kapyrin.myshop.entity.User;
import kapyrin.myshop.service.impl.*;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/managers")
public class ManagerServlet extends HttpServlet {
    private UserServiceImpl userService;
    private ShopOrderServiceImpl shopOrderService;
    private ProductServiceImpl productService;
    private OrderStatusServiceImpl orderStatusService;
    private ProductOrderServiceImpl productOrderService;


    @Override
    public void init() throws ServletException {
        userService = UserServiceImpl.INSTANCE.initRepository(UserDAOImpl.INSTANCE);
        shopOrderService = ShopOrderServiceImpl.INSTANCE.initRepository(ShopOrderOrderDAOImpl.INSTANCE);
        productService = ProductServiceImpl.INSTANCE.initRepository(ProductDAOImpl.INSTANCE);
        orderStatusService = OrderStatusServiceImpl.INSTANCE.initRepository(OrderStatusDAOImp.INSTANCE);
        productOrderService = ProductOrderServiceImpl.INSTANCE.initRepository(ProductOrderDaoImpl.INSTANCE);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        List<User> onlyCustomers = userService.getAll().stream()
                .filter(user -> "customer".equals(user.getRole().getUserRole()))
                .collect(Collectors.toList());

        Map<Long, List<ShopOrder>> userOrders = new HashMap<>();
        Map<Long, List<Product>> orderProducts = new HashMap<>();

        for (User user : onlyCustomers) {
            List<ShopOrder> orders = shopOrderService.getAllOrdersByUserId(user.getId());
            userOrders.put(user.getId(), orders != null ? orders : new ArrayList<>());
        }

        productOrderService.getAll().forEach(productOrder -> {
            orderProducts
                    .computeIfAbsent(productOrder.getOrder().getId(), k -> new ArrayList<>())
                    .add(productOrder.getProduct());
        });

        List<Product> products = productService.getAll();
        List<OrderStatus> statuses = orderStatusService.getAll();

        if ("filterByProduct".equals(action)) {
            Long productId = Long.parseLong(request.getParameter("productId"));
            List<ShopOrder> filteredOrders = shopOrderService.getOrdersByProductId(productId);
            request.setAttribute("filteredOrders", filteredOrders);
        } else {
            request.setAttribute("allOrders", shopOrderService.getAll());
        }

        request.setAttribute("users", onlyCustomers);
        request.setAttribute("userOrders", userOrders);
        request.setAttribute("orderProducts", orderProducts);
        request.setAttribute("products", products);
        request.setAttribute("statuses", statuses);

        request.getRequestDispatcher("jsp/manager.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "deleteBeforeDate":
                deleteBeforeDate(req, resp);
                break;
            case "updateStatus":
                updateStatus(req, resp);
                break;
            case "closeOrder":
                closeOrder(req, resp);
                break;
            default:
                resp.sendRedirect("/managers");
                break;
        }
    }

    private void closeOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long orderId = Long.parseLong(req.getParameter("orderId"));

        shopOrderService.closeOrder(orderId);
        resp.sendRedirect("/managers");
    }

    private void updateStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long orderId = Long.parseLong(req.getParameter("orderId"));
        int statusId = Integer.parseInt(req.getParameter("statusId"));

        shopOrderService.updateOrderStatus(orderId, statusId);
        resp.sendRedirect("/managers");
    }

    private void deleteBeforeDate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Date deleteBeforeDate = Date.valueOf(req.getParameter("deleteBeforeDate"));
        shopOrderService.deleteOrdersBeforeDate(deleteBeforeDate);
        resp.sendRedirect("/managers");
    }

    private void filterByProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter("productId"));
        List<ShopOrder> filterOrders = shopOrderService.getOrdersByProductId(productId);
        req.setAttribute("filterOrders", filterOrders);
        doGet(req, resp);
    }

}
