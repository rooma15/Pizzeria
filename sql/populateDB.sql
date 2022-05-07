INSERT INTO users(id, active, password, username)
VALUES (1, true, '123', 'Bob'),
       (2, true, '123', 'Eva'),
       (3, true, '123', 'Groot');

INSERT INTO personal_info(id, email, first_name, last_name, phone_number, user_id)
VALUES (1, 'bob@.gmail.com', 'Bob', 'Marley', '+555-555', 1),
       (2, 'eva@.gmail.com', 'Eva', 'Green', '+555-666', 2),
       (3, 'groot@.gmail.com', 'Groot', 'Groot', '+555-777', 3);

INSERT INTO addresses(id, city, entrance_number, flat_number, house_number, stage_number, street, personal_info_id)
VALUES (1, 'Minsk', '1', '1', '1', '1', 'Masherova', 1),
       (2, 'Moscow', '2', '2', '2', '2', 'Lomonosova', 2),
       (3, 'Kiev', '3', '3', '3', '3', 'Svetlaya', 3);

INSERT INTO credit_cards(id, date, number, cvv, personal_info_id)
VALUES (1, '07/22', '1111111111111111', '123', 1),
       (2, '07/22', '2222222222222222', '123', 2),
       (3, '07/22', '3333333333333333', '123', 3);

INSERT INTO orders(id, date, user_id)
VALUES (1, '2020-07-20 18:30:00', 1),
       (2, '2020-07-20 19:00:00', 2),
       (3, '2020-07-20 23:50:50', 3);

INSERT INTO dishes(id, category, description, sale, price, title)
VALUES (1, 'PIZZA', '', false, 12.05, 'Italian'),
       (2, 'PIZZA', '', false, 15.05, 'Chicago'),
       (3, 'SUSHI', '', false, 10.05, 'Philadelphia'),
       (4, 'SUSHI', '', false, 12.05, 'Black dragon'),
       (5, 'VEGETARIAN', '', false, 22.05, 'Seeds'),
       (6, 'VEGETARIAN', '', false, 22.05, 'Grass'),
       (7, 'VEGETARIAN', '', false, 22.05, 'Some without taste');

INSERT INTO user_role(user_id, roles)
VALUES (1, 'USER'),
       (2, 'USER'),
       (3, 'USER');

INSERT INTO order_dish(order_id, dish_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (2, 3),
       (3, 1),
       (3, 2),
       (3, 3);