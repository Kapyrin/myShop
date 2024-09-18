USE shop;

SELECT *
FROM customer;

SELECT *
FROM shop_order;

SELECT *
FROM product_order;

SELECT shop_order.id AS order_id,
       shop_order.order_creation_date,
       customer.first_name,
       customer.last_name,
       product.product_name,
       product_order.quantity
FROM shop_order
         JOIN
     product_order ON shop_order.id = product_order.order_id
         JOIN
     product ON product_order.product_id = product.id
         JOIN
     customer ON shop_order.customer_id = customer.id
WHERE shop_order.customer_id = 1;
