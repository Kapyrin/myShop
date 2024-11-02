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
import java.util.Optional;

@WebServlet("/editProduct")
public class EditProductServlet extends HttpServlet {
    private ProductServiceImpl productService;

    @Override
    public void init() {
        productService = ProductServiceImpl.INSTANCE.initRepository(ProductDAOImpl.INSTANCE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter("id"));
        Optional<Product> product = productService.getById(productId);
        if (product.isPresent()) {
            req.setAttribute("product", product.get());
            req.getRequestDispatcher("jsp/product/editProduct.jsp").forward(req, resp);
        } else
            resp.sendRedirect("/products");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter("productId"));
        String productName = req.getParameter("productName");
        String productDescription = req.getParameter("productDescription");
        Double productPrice = Double.parseDouble(req.getParameter("price"));
        Integer productQuantity = Integer.parseInt(req.getParameter("productRemain"));

        Optional<Product> optionalProduct = productService.getById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setProductName(productName);
            product.setProductDescription(productDescription);
            product.setPrice(productPrice);
            product.setProductRemain(productQuantity);
            productService.update(product);
        }
        resp.sendRedirect("/products");
    }
}

