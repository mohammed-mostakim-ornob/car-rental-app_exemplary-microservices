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