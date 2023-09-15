DELETE
FROM user_roles;
DELETE
FROM roles;
DELETE
FROM card;
DELETE
FROM refresh_token;
DELETE
FROM users;



INSERT INTO users(id, user_name, password, registered, status)
VALUES (1, 'Masha', '$2a$12$ZpXbje4hlSVkb/9raerot.ud0Odzjt9dQS7j6x/nISd2deiHc9cvi',  '2023-09-30 10:00:00', 'ACTIVE'),
       (2, 'Glasha', '$2a$12$ZpXbje4hlSVkb/9raerot.ud0Odzjt9dQS7j6x/nISd2deiHc9cvi',  '2023-01-30 10:00:00', 'ACTIVE');


INSERT INTO roles(id, name)
VALUES (200, 'USER'),
       (201, 'ADMIN');

INSERT INTO user_roles(user_id, role_id)
VALUES (1, 200),
       (1, 201),
       (2, 200);

INSERT INTO card(id, user_id, word, translation, state, last_taught)
VALUES (1, 1, 'name', 'имя', 'LEVEL_ONE', '2023-09-30'),
       (2, 1, 'value', 'значение','LEVEL_TWO', '2023-09-30'),
       (3, 1, 'instance', 'экземпляр объекта','LEVEL_SIX', '2023-09-30'),
       (4, 1, 'identifier', 'идентификатор','LEVEL_THREE', '2023-09-30'),
       (5, 1, 'digital', 'цифровой','LEVEL_THREE', '2023-09-30'),
       (6, 1, 'nomand', 'кочевник','LEVEL_THREE', '2023-09-30'),
       (7, 1, 'template', 'шаблон','LEVEL_FIVE', '2023-09-30'),
       (8, 1, 'timely', 'своевременный','LEVEL_ONE', '2023-09-30'),
       (9, 1, 'manage', 'управлять','LEVEL_SIX', '2023-09-30'),
       (10, 1, 'precede', 'предшествовать','LEVEL_TWO', '2023-09-30'),
       (11, 1, 'destruction', 'уничтожение','LEVEL_FOUR', '2023-09-30'),
       (12, 2, 'I', 'я', 'LEVEL_FOUR', '2023-09-30'),
       (13, 2, 'You', 'ты', 'LEVEL_FIVE', '2023-09-30'),
       (14, 2, 'we', 'мы', 'LEVEL_ONE', '2023-09-30');

INSERT INTO refresh_token(id, user_id, token, expiry_date)
VALUES (1, 2, '282f77f2-1bcb-4ea2-ae91-d1a28c30e9fc', '2023-08-04');
