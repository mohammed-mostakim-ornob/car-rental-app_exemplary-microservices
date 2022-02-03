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

CREATE TABLE IF NOT EXISTS log
(
    id              SERIAL PRIMARY KEY NOT NULL,
    server_id       VARCHAR(100)       NOT NULL,
    client_id       VARCHAR(100)       NOT NULL,
    method_id       VARCHAR(100)       NOT NULL,
    response_code   VARCHAR(100)       NOT NULL,
    invocation_time BIGINT             NOT NULL,
    response_time   BIGINT             NOT NULL
);