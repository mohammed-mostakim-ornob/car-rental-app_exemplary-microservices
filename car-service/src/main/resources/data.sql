INSERT INTO manufacturer
(
    name
)
VALUES
    ('Audi'),
    ('BMW'),
    ('Mercedes');

INSERT INTO car
(
    code,
    manufacturer_name,
    model,
    rent_per_kilo
)
VALUES
    ('2021_001', 'Audi', 'A3', 0.1),
    ('2021_002', 'BMW', '3 Series', 0.1),
    ('2021_003', 'Mercedes', 'C Class', 0.1);