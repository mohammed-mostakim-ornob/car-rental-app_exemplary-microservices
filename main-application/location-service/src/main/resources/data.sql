INSERT INTO location
(
    code,
    name
)
VALUES
    ('BER', 'Berlin'),
    ('FRA', 'Frankfurt'),
    ('MUN', 'München'),
    ('HAM', 'Hamburg'),
    ('STU', 'Stuttgart')
ON CONFLICT(code) DO UPDATE SET name = Excluded.name;

INSERT INTO distance
(
    id,
    from_location,
    to_location,
    distance
)
values
    (1, 'BER', 'FRA', 545.00),
    (2, 'BER', 'MUN', 593.00),
    (3, 'BER', 'HAM', 289.00),
    (4, 'BER', 'STU', 632.00),

    (5, 'FRA', 'BER', 551.00),
    (6, 'FRA', 'MUN', 401.00),
    (7, 'FRA', 'HAM', 498.00),
    (8, 'FRA', 'STU', 206.00),

    (9, 'MUN', 'BER', 585.00),
    (10, 'MUN', 'FRA', 392.00),
    (11, 'MUN', 'HAM', 791.00),
    (12, 'MUN', 'STU', 232.00),

    (13, 'HAM', 'BER', 289.00),
    (14, 'HAM', 'FRA', 492.00),
    (15, 'HAM', 'MUN', 785.00),
    (16, 'HAM', 'STU', 655.00),

    (17, 'STU', 'BER', 633.00),
    (18, 'STU', 'FRA', 206.00),
    (19, 'STU', 'MUN', 233.00),
    (20, 'STU', 'HAM', 656.00)
ON CONFLICT(id) DO UPDATE SET
    from_location = Excluded.from_location,
    to_location = Excluded.to_location,
    distance = Excluded.distance;