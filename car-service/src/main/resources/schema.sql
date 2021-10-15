DROP TABLE IF EXISTS manufacturer;
CREATE TABLE manufacturer
(
    name VARCHAR(255) NOT NULL PRIMARY KEY
);

DROP TABLE IF EXISTS car;
CREATE TABLE car
(
    code VARCHAR(255) NOT NULL PRIMARY KEY,
    model VARCHAR(255) NOT NULL,
    rent_per_kilo DOUBLE NOT NULL,
    manufacturer_name VARCHAR(255) NOT NULL REFERENCES manufacturer(name)
);