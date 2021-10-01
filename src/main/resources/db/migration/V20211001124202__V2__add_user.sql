INSERT INTO school(school_number, address)
VALUES(25, 'Конюшенная, 22');

INSERT INTO school(school_number, address)
VALUES(15, 'Конюшенная, 22');

INSERT INTO people (id, name, surname, school_number)
    VALUES (1, 'Леха', 'Иванов', 25);

INSERT INTO people (id, name, surname, school_number)
VALUES (2, 'Саня', 'Михайлов', 25);

INSERT INTO people (id, name, surname, school_number)
VALUES (3, 'Илья', 'Титов', 15);

INSERT INTO people_posts (id, people_id, name, content)
    VALUES(1, 1, 'Лехин первый пост', 'С содержанием');

INSERT INTO people_posts (id, people_id, name, content)
    VALUES(2, 1, 'Лехин второй пост', 'С содержанием')
