INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Third', 'third@th.rd', '{noop}the_third');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER',3);

INSERT INTO USER_SUBSCRIPTIONS (user_id, subscriber_id)
VALUES (1,2),
       (2,3),
       (3,2),
       (1,3),
       (3,1);

INSERT INTO FRIENDSHIP_REQUEST (request_status, recipient_id, sender_id)
VALUES  ('SENT', 1, 2);

INSERT INTO USER_FRIENDS (user_id, friend_id)
VALUES (2,3),
       (3,2);

INSERT INTO POSTS (name, description, photo_path, user_id)
VALUES ('umnaya misl', 'na samom dele net',null,2),
       ('ne umnaya misl', null,null,2),
       ('a','b',null,1);