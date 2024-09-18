INSERT INTO customer (first_name, last_name, email, password, phone_number, address)
VALUES ('Vladimir', 'Kapyrin', 'vladimir.kapyrin@gmail.com', 'password', '+7 999 705 77 96',
        '241050 Bryansk, Fokina 2');

INSERT INTO product (product_name, product_description, price, product_remain)
VALUES ('Unit AMD', 'powered by AMD', 35000, 50),
       ('Unit Intel', 'powered by Intel', 30000.00, 50),
       ('Monitor Acer', 'TFT 27 inch', 26000.00, 20),
       ('Monitor Apple', 'TFT 32 inch', 250000.00, 10),
       ('Mouse Apple', 'apple', 15000, 15),
       ('Mouse Logitech', 'logitech', 3000, 35),
       ('Keyboard Logitech', 'logitech', 5000, 35),
       ('Keyboard Apple', 'apple', 20000, 15);

INSERT INTO order_status(status_name)
VALUES ('Создан'),
       ('В сборке'),
       ('Доставляется'),
       ('Доставлен-закрыт'),
       ('Отменен');

INSERT INTO shop_order(customer_id, order_creation_date, status_id)
VALUES (1, NOW(), 1);

UPDATE shop_order
SET status_id = 3
WHERE id = 1;

INSERT INTO product_order(order_id, product_id, quantity)
VALUES (1, 1, 1),
       (1, 2, 2),
       (1, 6, 1),
       (1, 8, 1);


