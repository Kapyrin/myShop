package kapyrin.myshop.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kapyrin.myshop.dao.impl.ProductDAOImpl;
import kapyrin.myshop.entity.Product;
import kapyrin.myshop.service.impl.ProductServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private ProductServiceImpl productService;

    @Override
    public void init() throws ServletException {
        productService = ProductServiceImpl.INSTANCE.initRepository(ProductDAOImpl.INSTANCE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productService.getAll();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/jsp/product.jsp").forward(req, resp);
    }
}
