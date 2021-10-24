CREATE TABLE IF NOT EXISTS usr(
  user_id int8 PRIMARY KEY,
  login VARCHAR(10) UNIQUE NOT NULL,
  name VARCHAR(20) NOT NULL,
  lastname VARCHAR(20) NOT NULL,
  pass VARCHAR NOT NULL,
  role VARCHAR (20) NOT NULL);

CREATE TABLE IF NOT EXISTS booking(
    book_id int8 PRIMARY KEY,
      booking_time timestamp WITHOUT TIME ZONE,
      user_id int8 NOT NULL,
      confirmed bool,

      FOREIGN KEY (user_id) REFERENCES usr(user_id)
);