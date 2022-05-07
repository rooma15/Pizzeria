INSERT INTO users(id, active, password, username)
VALUES (4, true, '123', 'admin');
INSERT INTO personal_info(id, email, first_name, last_name, phone_number, user_id)
VALUES (4, 'admin@.gmail.com', 'Neo', 'Anderson', '+555-555', 4);
INSERT INTO orders(id, date, user_id)
VALUES (4, '2020-07-20 18:30:00', 4);
INSERT INTO user_role(user_id, roles)
VALUES (4, 'USER'),
       (4, 'ADMIN');
