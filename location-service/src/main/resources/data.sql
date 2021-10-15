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
    ('STU', 'Stuttgart');

INSERT INTO distance
(
    from_location,
    to_location,
    distance
)
values
    ('BER', 'FRA', 545.00),
    ('BER', 'MUN', 593.00),
    ('BER', 'HAM', 289.00),
    ('BER', 'STU', 632.00),

    ('FRA', 'BER', 551.00),
    ('FRA', 'MUN', 401.00),
    ('FRA', 'HAM', 498.00),
    ('FRA', 'STU', 206.00),

    ('MUN', 'BER', 585.00),
    ('MUN', 'FRA', 392.00),
    ('MUN', 'HAM', 791.00),
    ('MUN', 'STU', 232.00),

    ('HAM', 'BER', 289.00),
    ('HAM', 'FRA', 492.00),
    ('HAM', 'MUN', 785.00),
    ('HAM', 'STU', 655.00),

    ('STU', 'BER', 633.00),
    ('STU', 'FRA', 206.00),
    ('STU', 'MUN', 233.00),
    ('STU', 'HAM', 656.00);