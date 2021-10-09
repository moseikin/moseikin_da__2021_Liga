CREATE TABLE IF NOT EXISTS friends(
    PRIMARY KEY (owner_id, usr_id),
     owner_id int8,
     usr_id int8,

     are_friends bool,

     FOREIGN KEY (owner_id) REFERENCES usr(usr_id),
     FOREIGN KEY (usr_id) REFERENCES usr(usr_id));