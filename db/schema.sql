CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name TEXT,
  email TEXT UNIQUE,
  password TEXT
);

CREATE TABLE tasks (
   id SERIAL PRIMARY KEY,
   describe TEXT,
   created TIMESTAMP,
   done BOOLEAN,
   user_id int not null references users(id)
);