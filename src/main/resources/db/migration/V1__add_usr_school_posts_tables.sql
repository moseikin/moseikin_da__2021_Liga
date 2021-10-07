CREATE TABLE IF NOT EXISTS all_schools(
     school_number int8 PRIMARY KEY,
     address VARCHAR(100) NULL);

CREATE TABLE IF NOT EXISTS usr(
  usr_id int8 PRIMARY KEY,
  name VARCHAR(20) NOT NULL,
  lastname VARCHAR(20) NOT NULL,
  age INTEGER,
  school_number int8,

  FOREIGN KEY (school_number) REFERENCES all_schools(school_number));

-- CREATE TABLE IF NOT EXISTS usr_school(
--     PRIMARY KEY (school_number, usr_id),
--      school_number int8,
--      usr_id int8,
--
--      FOREIGN KEY (school_number) REFERENCES all_schools(school_number),
--      FOREIGN KEY (usr_id) REFERENCES usr(usr_id));





CREATE TABLE IF NOT EXISTS user_posts(
    id int8 PRIMARY KEY,
    text VARCHAR(2048) NOT NULL,
    usr_id int8,

    FOREIGN KEY (usr_id) REFERENCES usr(usr_id));