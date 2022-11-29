--liquibase formatted sql

--changeset ivaylo_ivanov:1
INSERT INTO university_user (email, password, role, created_date, password_updated_date)
VALUES ('admin@abv.bg', '$2a$10$6S.HoSbYYkrk4mtFYbb1yuolGbupLBEVDcbFTwZR6MRe4xt1/UxZO', 'ADMIN', '2022-11-24 13:00', '2022-11-24 13:00');

INSERT INTO faculty (name_bg, name_en)
VALUES ('МТФ', 'MTF'),
       ('КФ', 'KF'),
       ('EФ', 'EF'),
       ('ФИТА', 'FITA');

INSERT INTO department (name_bg, name_en, faculty_id)
VALUES ('ИМ', 'IM', 1),
       ('MTM', 'MTM', 1),
       ('ММЕ', 'MME', 1),
       ('ЕООС', 'EOOS', 2),
       ('ИД', 'ID', 2),
       ('Т', 'T', 2),
       ('EСEO', 'ESEO', 3),
       ('ЕТЕТ', 'ETET', 3),
       ('ЕЕ', 'EE', 3),
       ('AП', 'AP', 4),
       ('ЕТМЕ', 'ETME', 4),
       ('СИТ', 'SIT', 4);

INSERT INTO room (number_bg, number_en)
VALUES ('НУК 325', 'NUK 325'),
       ('НУК 307', 'NUK 307'),
       ('827М', '827M'),
       ('216М', '216M'),
       ('212Е', '212Е'),
       ('606Е', '606E'),
       ('307ТВ', '307TB'),
       ('202ТВ ', '202TB');