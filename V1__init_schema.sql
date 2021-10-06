CREATE TABLE School (
	id INTEGER PRIMARY KEY,
	name VARCHAR(50) NULL,
	address VARCHAR (100) NULL);
	
CREATE TABLE Teacher (
	id INTEGER PRIMARY KEY,
	school_id INTEGER,
	firstname VARCHAR(50) NULL,
	second_name VARCHAR(50) NULL,
	patronymic VARCHAR(50) NULL,
	age INTEGER,
	gender VARCHAR(15) NULL,
	
	FOREIGN KEY (school_id) REFERENCES School (id));

CREATE TABLE Student (
     id INTEGER PRIMARY KEY,
     school_id INTEGER,
     firstname VARCHAR(50) NULL,
     second_name VARCHAR(50) NULL,
     patronymic VARCHAR(50) NULL,
     age INTEGER,
     gender VARCHAR(15) NULL,

     FOREIGN KEY (school_id) REFERENCES School(id));
	
CREATE TABLE Subject (
	title VARCHAR(100) PRIMARY KEY);


CREATE TABLE Student_Subject (
     PRIMARY KEY (student_id, subject_title),
     student_id INTEGER,
     subject_title VARCHAR(100),

     FOREIGN KEY (student_id) REFERENCES Student(id),
     FOREIGN KEY (subject_title) REFERENCES Subject(title));

CREATE TABLE Teacher_Subject (
     PRIMARY KEY (teacher_id, subject_title),
     teacher_id INTEGER,
     subject_title VARCHAR(100),

     FOREIGN KEY (teacher_id) REFERENCES Teacher(id),
     FOREIGN KEY (subject_title) REFERENCES Subject(title));

