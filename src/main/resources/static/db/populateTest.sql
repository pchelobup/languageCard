DELETE
FROM user_roles;
DELETE
FROM roles;
DELETE
FROM card;
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

INSERT INTO card(id, user_id, word, translation, level)
VALUES (1, 1, 'name', 'имя', 'ONE'),
       (2, 1, 'value', 'значение','TWO'),
       (3, 1, 'instance', 'экземпляр объекта','SIX'),
       (4, 1, 'identifier', 'идентификатор','THREE'),
       (5, 1, 'digital', 'цифровой','THREE'),
       (6, 1, 'nomand', 'кочевник','THREE'),
       (7, 1, 'template', 'шаблон','FIVE'),
       (8, 1, 'timely', 'своевременный','ONE'),
       (9, 1, 'manage', 'управлять','SIX'),
       (10, 1, 'precede', 'предшествовать','TWO'),
       (11, 1, 'destruction', 'уничтожение','FOUR'),
       (12, 2, 'I', 'я', 'FOUR'),
       (13, 2, 'You', 'ты', 'FIVE'),
       (14, 2, 'we', 'мы', 'ONE');
