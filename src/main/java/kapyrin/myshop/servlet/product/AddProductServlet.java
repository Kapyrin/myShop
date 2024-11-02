package kapyrin.myshop.servlet.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kapyrin.myshop.dao.impl.ProductDAOImpl;
import kapyrin.myshop.entity.Product;
import kapyrin.myshop.service.impl.ProductServiceImpl;

import java.io.IOException;
@WebServlet("/addProduct")
public class AddProductServlet extends HttpServlet {
    private ProductServiceImpl productService;

    @Override
    public void init() {
        productService = ProductServiceImpl.INSTANCE.initRepository(ProductDAOImpl.INSTANCE);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("productName");
        String description = req.getParameter("productDescription");
        Double price = Double.parseDouble(req.getParameter("productPrice"));
        int quantity = Integer.parseInt(req.getParameter("productQuantity"));
        productService.add(Product.builder()
                .productName(name)
                .productDescription(description)
                .price(price)
                .productRemain(quantity)
                .build()
        );
        resp.sendRedirect("/products");

    }

}

