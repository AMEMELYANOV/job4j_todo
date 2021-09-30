CREATE TABLE tasks (
   id SERIAL PRIMARY KEY,
   describe TEXT,
   created TIMESTAMP,
   done BOOLEAN
);