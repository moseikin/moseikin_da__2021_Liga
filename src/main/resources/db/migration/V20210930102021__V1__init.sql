

CREATE TABLE school (
    school_number INTEGER PRIMARY KEY,
    address VARCHAR (100) NOT NULL);

CREATE TABLE people (
        id INTEGER PRIMARY KEY,
        name VARCHAR(20) NOT NULL,
        surname VARCHAR(20) NOT NULL ,
        school_number INTEGER,

        FOREIGN KEY (school_number) REFERENCES school(school_number)
            ON DELETE SET NULL );

CREATE TABLE people_posts (
      id INTEGER PRIMARY KEY,
      people_id INTEGER,
      name VARCHAR(100),
      content VARCHAR(1024),

      FOREIGN KEY (people_id) REFERENCES  people(id) ON DELETE CASCADE);

CREATE TABLE dialogs (
         id INTEGER PRIMARY KEY,
         create_data DATE NOT NULL,
         people_first_id INTEGER,
         people_second_id INTEGER,
         content VARCHAR(1024),

         FOREIGN KEY (people_first_id) REFERENCES  people(id),
         FOREIGN KEY (people_second_id) REFERENCES  people(id));

CREATE TABLE friends (
    owner_id INTEGER,
    friend_id INTEGER,

    FOREIGN KEY (owner_id) REFERENCES people(id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES people(id));

