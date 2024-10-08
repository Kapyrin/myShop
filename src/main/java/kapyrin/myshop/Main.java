package kapyrin.myshop;

import kapyrin.myshop.dao.impl.*;
import kapyrin.myshop.entities.*;
import kapyrin.myshop.exception.entities.OrderStatusException;
import kapyrin.myshop.exception.entities.ProductException;
import kapyrin.myshop.exception.entities.RoleException;
import kapyrin.myshop.exception.entities.UserException;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final RoleDAOImpl ROLE_DAO_IMPL = RoleDAOImpl.INSTANCE;
    private static final UserDAOImpl USER_DAO_IMPL = UserDAOImpl.INSTANCE;
    private static final ProductDAOImpl PRODUCT_DAO_IMPL = ProductDAOImpl.INSTANCE;
    private static final OrderStatusDAOImp ORDER_STATUS_DAO_IMPL = OrderStatusDAOImp.INSTANCE;
    private static final ShopOrderDAOImpl SHOP_ORDER_DAO_IMPL = ShopOrderDAOImpl.INSTANCE;
    private static final ProductOrderDaoImpl PRODUCT_ORDER_DAO_IMPL = ProductOrderDaoImpl.INSTANCE;

    public static void main(String[] args) {

        try {
            ROLE_DAO_IMPL.add(Role.builder().userRole("admin").build());
            ROLE_DAO_IMPL.add(Role.builder().userRole("manager").build());
            ROLE_DAO_IMPL.add(Role.builder().userRole("customer").build());

            Optional<Role> admin = ROLE_DAO_IMPL.getById(1);
            Optional<Role> manager = ROLE_DAO_IMPL.getById(2);
            Optional<Role> customer = ROLE_DAO_IMPL.getById(3);

            User vladimir = User.builder()
                    .id(0L)
                    .firstName("Vladimir")
                    .lastName("Kapyrin")
                    .email("vladimir.kapyrin@gmail.com")
                    .password("password")
                    .phoneNumber("1234567890")
                    .address("Russia")
                    .role(customer.get())
                    .build();

            User bill = User.builder()
                    .id(0L)
                    .firstName("Bill")
                    .lastName("Gates")
                    .email("bill@microsot.com")
                    .password("password")
                    .phoneNumber("0987654321")
                    .address("Ostin")
                    .role(manager.get())
                    .build();

            User ilon = User.builder()
                    .id(0L)
                    .firstName("Elon")
                    .lastName("Musk")
                    .email("elon@tesla.com")
                    .password("password")
                    .phoneNumber("1234509876")
                    .address("Redmond")
                    .role(manager.get())
                    .build();

            USER_DAO_IMPL.add(vladimir);
            USER_DAO_IMPL.add(bill);
            USER_DAO_IMPL.add(ilon);

            List<User> allUsers = USER_DAO_IMPL.getAll();
            for (User user : allUsers) {
                System.out.println(user);
            }

            Optional<User> updated = USER_DAO_IMPL.getById(1);
            System.out.println(updated);
            if (updated.isPresent()) {
                updated.get().setFirstName("First");
                updated.get().setLastName("Last");
                updated.get().setEmail("update@email.com");
                updated.get().setPhoneNumber("6789012456");
                updated.get().setAddress("Washington");

                USER_DAO_IMPL.update(updated.orElse(null));
            } else throw new UserException("User not found");
            User updatedUser = updated.orElseThrow(() -> new UserException("User not found"));
            Optional<User> deleted = USER_DAO_IMPL.getById(1);
            System.out.println("Deleting user " + deleted.get().getFirstName() + " " + deleted.get().getLastName());
            USER_DAO_IMPL.deleteById(3);

            List<User> afterDeleteUsers = USER_DAO_IMPL.getAll();
            for (User user : afterDeleteUsers) {
                System.out.println(user);
            }

            Product monitorAcer = Product.builder()
                    .productName("Monitor Acer")
                    .productDescription("TFT 27 inch")
                    .price(26000.0)
                    .productRemain(20)
                    .build();
            PRODUCT_DAO_IMPL.add(Product.builder()
                    .productName("Unit AMD")
                    .productDescription("Powered by AMD")
                    .price(35000.0)
                    .productRemain(50)
                    .build());

            PRODUCT_DAO_IMPL.add(Product.builder()
                    .productName("Unit Intel")
                    .productDescription("Powered by Intel")
                    .price(30000.0)
                    .productRemain(50)
                    .build());

            PRODUCT_DAO_IMPL.add(monitorAcer);

            List<Product> allProducts = PRODUCT_DAO_IMPL.getAll();
            for (Product product : allProducts) {
                System.out.println(product);
            }

            Optional<Product> updatedProduct = PRODUCT_DAO_IMPL.getById(2);
            System.out.println(updatedProduct);
            if (updatedProduct.isPresent()) {
                updatedProduct.get().setPrice(45000.0);
                updatedProduct.get().setProductName("Intel");
                updatedProduct.get().setProductDescription("by Intel");
                PRODUCT_DAO_IMPL.update(updatedProduct.get());
                Optional<Product> checkUpdatingProduct = PRODUCT_DAO_IMPL.getById(2);
                checkUpdatingProduct.ifPresent(product -> System.out.println(product.getProductName() + " " + product.getProductDescription()));
            } else throw new ProductException("Product not found");

            Optional<Product> deletedProduct = PRODUCT_DAO_IMPL.getById(2);
            if (deletedProduct.isPresent()) {
                System.out.println("Deleting product " + deletedProduct.get().getProductName());
                PRODUCT_DAO_IMPL.deleteById(2);
            } else throw new ProductException("Product not found");

            List<Product> afterDeleteProducts = PRODUCT_DAO_IMPL.getAll();
            for (Product product : afterDeleteProducts) {
                System.out.println(product);
            }


            ORDER_STATUS_DAO_IMPL.add(OrderStatus.builder().statusName("Created").build());
            ORDER_STATUS_DAO_IMPL.add(OrderStatus.builder().statusName("In build").build());
            ORDER_STATUS_DAO_IMPL.add(OrderStatus.builder().statusName("Delivering").build());
            ORDER_STATUS_DAO_IMPL.add(OrderStatus.builder().statusName("Delivered-closed").build());
            ORDER_STATUS_DAO_IMPL.add(OrderStatus.builder().statusName("Canceled").build());

            OrderStatus orderStatus = ORDER_STATUS_DAO_IMPL.getById(1).orElseThrow(() -> new OrderStatusException("Order status not found"));


            ShopOrder order = ShopOrder.builder()
                    .customer(updatedUser)
                    .orderCreationDate(new Date(Timestamp.valueOf(LocalDateTime.now()).getTime()))
                    .status(orderStatus)
                    .build();
            System.out.println(order);

            SHOP_ORDER_DAO_IMPL.add(order);

            ShopOrder fromDB = SHOP_ORDER_DAO_IMPL.getById(1).orElseThrow(() -> new OrderStatusException("Order  not found"));
            System.out.println(fromDB);
            List<Product> productsFromDB = PRODUCT_DAO_IMPL.getAll();

            for (Product product : productsFromDB) {
                ProductOrder productOrder = ProductOrder.builder()
                        .order(fromDB)
                        .product(product)
                        .quantity(3)
                        .build();
                PRODUCT_ORDER_DAO_IMPL.add(productOrder);
            }

            List<ProductOrder> productOrders = PRODUCT_ORDER_DAO_IMPL.getAll();
            System.out.println("All products in order with:");
            for (ProductOrder productOrder : productOrders) {
                System.out.println("The product: " + productOrder.getProduct().getProductName());
            }

        } catch (RoleException | UserException e) {
            e.printStackTrace();
        }
    }
}

