package kapyrin.myshop.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kapyrin.myshop.dao.impl.OrderStatusDAOImp;
import kapyrin.myshop.dao.impl.ProductDAOImpl;
import kapyrin.myshop.dao.impl.ProductOrderDaoImpl;
import kapyrin.myshop.dao.impl.ShopOrderDAOImpl;
import kapyrin.myshop.entity.*;
import kapyrin.myshop.exception.entity.OrderStatusException;
import kapyrin.myshop.service.impl.OrderStatusServiceImpl;
import kapyrin.myshop.service.impl.ProductOrderServiceImpl;
import kapyrin.myshop.service.impl.ProductServiceImpl;
import kapyrin.myshop.service.impl.ShopOrderServiceImpl;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/createOrder")
public class CreateOrderServlet extends HttpServlet {
    private ProductServiceImpl productService;
    private ShopOrderServiceImpl shopOrderService;
    private ProductOrderServiceImpl productOrderService;
    private OrderStatusServiceImpl orderStatusService;

    @Override
    public void init() {
        productService = ProductServiceImpl.INSTANCE.initRepository(ProductDAOImpl.INSTANCE);
        shopOrderService = ShopOrderServiceImpl.INSTANCE.initRepository(ShopOrderDAOImpl.INSTANCE);
        productOrderService = ProductOrderServiceImpl.INSTANCE.initRepository(ProductOrderDaoImpl.INSTANCE);
        orderStatusService = OrderStatusServiceImpl.INSTANCE.initRepository(OrderStatusDAOImp.INSTANCE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productService.getAll();
        req.setAttribute("products", products);
        req.getRequestDispatcher("jsp/createOrder.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }

        OrderStatus orderStatus = orderStatusService.getById(1)
                .orElseThrow(() -> new OrderStatusException("Order status not found"));

        ShopOrder order = ShopOrder.builder()
                .customer(user)
                .orderCreationDate(new Date(System.currentTimeMillis()))
                .status(orderStatus)
                .build();
        shopOrderService.add(order);

        List<Product> products = productService.getAll();
        for (Product product : products) {
            String quantityParam = req.getParameter("quantity" + product.getId());
            if (quantityParam != null && !quantityParam.isEmpty()) {
                int quantity = Integer.parseInt(quantityParam);
                if (quantity > 0) {
                    ProductOrderKey productOrderKey = new ProductOrderKey();
                    productOrderKey.setOrderId(order.getId());
                    productOrderKey.setProductId(product.getId());

                    ProductOrder productOrder = ProductOrder.builder()
                            .id(productOrderKey)
                            .order(order)
                            .product(product)
                            .quantity(quantity)
                            .build();

                    productOrderService.add(productOrder);
                }
            }
        }


        resp.sendRedirect("/customerOrders");
    }
}