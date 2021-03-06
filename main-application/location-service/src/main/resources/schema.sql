CREATE TABLE IF NOT EXISTS location
(
    code VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS distance
(
    id INT NOT NULL PRIMARY KEY,
    distance DOUBLE PRECISION NOT NULL,
    from_location VARCHAR(255) NOT NULL REFERENCES location(code),
    to_location VARCHAR(255) NOT NULL REFERENCES location(code)
);