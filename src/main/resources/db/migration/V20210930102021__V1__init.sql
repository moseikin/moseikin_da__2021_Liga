CREATE TABLE people (
      id INTEGER PRIMARY KEY,
      name VARCHAR(20) NOT NULL,
      surname VARCHAR(20) NOT NULL ,
      school VARCHAR(20) NULL);

CREATE TABLE School (
    id INTEGER PRIMARY KEY,
    name VARCHAR(30) NOT NULL ,
    address VARCHAR (100) NOT NULL,

    FOREIGN KEY (name) REFERENCES people(school));


CREATE ЕФ
