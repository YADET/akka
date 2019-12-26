

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users
(
    user_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    user_code VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL
);

INSERT INTO users VALUES (uuid_generate_v4(), 'stest', '$2a$10$niF.amAexQMHaevqlkganeSjvMHfTq/OdISyj8/5BQy1FHvlbi3Ne');

--DROP TABLE users;

SELECT user_code,password FROM users;