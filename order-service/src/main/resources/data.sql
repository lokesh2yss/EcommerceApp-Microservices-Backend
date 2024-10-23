INSERT INTO orders (total_price, order_status) VALUES
(100.50, 'PENDING'),
(150.75, 'CONFIRMED'),
(200.00, 'DELIVERED'),
(250.20, 'CANCELLED'),
(300.80, 'PENDING'),
(120.00, 'CONFIRMED'),
(350.50, 'DELIVERED'),
(400.30, 'CANCELLED'),
(180.40, 'PENDING'),
(500.90, 'CONFIRMED');

INSERT INTO order_items (order_id, product_id, quantity) VALUES
(1, 1, 2),
(1, 3, 1),
(2, 2, 4),
(3, 5, 3),
(3, 7, 2),
(4, 4, 1),
(5, 6, 5),
(6, 9, 3),
(6, 10, 2),
(7, 8, 4),
(8, 2, 2),
(9, 1, 3),
(10, 5, 4);

