DELETE FROM booking;
DELETE FROM usr;

INSERT INTO usr (user_id, login, name, lastname, pass, role) VALUES
(2147483648, 'test', 'test', 'test', 'test', 'ROLE_USER');

INSERT INTO booking (book_id, booking_time, user_id, status) VALUES
(2147483648, '2021-11-06 9:00:00.000000', 2147483648, 'unconfirmed')
