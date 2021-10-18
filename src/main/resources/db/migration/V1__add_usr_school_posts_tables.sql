CREATE TABLE IF NOT EXISTS all_schools(
     school_number int8 PRIMARY KEY,
     address VARCHAR(100) NULL);

CREATE TABLE IF NOT EXISTS usr(
  user_id int8 PRIMARY KEY,
  name VARCHAR(20) NOT NULL,
  lastname VARCHAR(20) NOT NULL,
  age INTEGER,
  school_number int8,

  FOREIGN KEY (school_number) REFERENCES all_schools(school_number));


CREATE TABLE IF NOT EXISTS user_posts(
    id int8 PRIMARY KEY,
    text VARCHAR(2048) NOT NULL,
    user_id int8 NOT NULL,

    FOREIGN KEY (user_id) REFERENCES "usr"(user_id));

CREATE TABLE IF NOT EXISTS friends(
                                      PRIMARY KEY (owner_id, user_id),
                                      owner_id int8,
                                      user_id int8,

                                      are_friends bool,

                                      FOREIGN KEY (owner_id) REFERENCES usr(user_id),
                                      FOREIGN KEY (user_id) REFERENCES usr(user_id));