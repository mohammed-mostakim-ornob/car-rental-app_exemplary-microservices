DROP TABLE IF EXISTS distance;
DROP TABLE IF EXISTS location;

CREATE TABLE location
(
    code VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE distance
(
    id SERIAL NOT NULL PRIMARY KEY,
    distance DOUBLE PRECISION NOT NULL,
    from_location VARCHAR(255) NOT NULL REFERENCES location(code),
    to_location VARCHAR(255) NOT NULL REFERENCES location(code)
);