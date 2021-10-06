INSERT INTO subject(title) VALUES ('Russkiy');
INSERT INTO subject(title) VALUES ('Angliiskiy');

INSERT INTO school(id, name, address) VALUES (1, 'Simple school', 'Kutuzova str.');

INSERT INTO student(id, school_id, firstname, second_name, patronymic, age, gender) VALUES
            (1, 1, 'Иванов', 'Иван', 'Иваныч', 10, 'male');

INSERT INTO student_subject(student_id, subject_title) VALUES (1, 'Russkiy')