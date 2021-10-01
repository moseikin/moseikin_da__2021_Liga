CREATE TABLE School (
	id INTEGER PRIMARY KEY,
	name VARCHAR(50) NULL,
	address VARCHAR (100) NULL);
	
CREATE TABLE Teacher (
	id INTEGER PRIMARY KEY,
	school_id INTEGER,
	firstname VARCHAR(50) NULL,
	secondname VARCHAR(50) NULL,
	patronymic VARCHAR(50) NULL,
	age INTEGER,
	gender VARCHAR(15) NULL,
	
	FOREIGN KEY (school_id) REFERENCES School (id));

CREATE TABLE Student (
     id INTEGER PRIMARY KEY,
     school_id INTEGER,
     firstname VARCHAR(50) NULL,
     secondname VARCHAR(50) NULL,
     patronymic VARCHAR(50) NULL,
     age INTEGER,
     gender VARCHAR(15) NULL,

     FOREIGN KEY (school_id) REFERENCES School(id));
	
CREATE TABLE Subject (
	id INTEGER PRIMARY KEY,
	title VARCHAR(100) NULL);


CREATE TABLE Student_Subject (
     id INTEGER PRIMARY KEY,
     student_id INTEGER,
     subject_id INTEGER,
     title VARCHAR(100) NULL,

     FOREIGN KEY (student_id) REFERENCES Student(id),
     FOREIGN KEY (subject_id) REFERENCES Subject(id));

CREATE TABLE Teacher_Subject (
     id INTEGER PRIMARY KEY,
     teacher_id INTEGER,
     subject_id INTEGER,
     title VARCHAR(100) NULL,

     FOREIGN KEY (teacher_id) REFERENCES Teacher(id),
     FOREIGN KEY (subject_id) REFERENCES Subject(id));

