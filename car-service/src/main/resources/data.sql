insert into manufacturer
    (name)
values
    ('Audi'),
    ('BMW'),
    ('Mercedes');

insert into car
    (code, manufacturer_name, model, rent_per_kilo)
values
    ('2021_001', 'Audi', 'A3', 0.1),
    ('2021_002', 'BMW', '3 Series', 0.1),
    ('2021_003', 'Mercedes', 'C Class', 0.1);
