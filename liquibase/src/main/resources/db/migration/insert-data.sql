--liquibase formatted sql

--changeset ivaylo_ivanov:1
INSERT INTO university_user (email, password, role, created_date, password_updated_date)
VALUES ('admin@abv.bg', '$2a$10$6S.HoSbYYkrk4mtFYbb1yuolGbupLBEVDcbFTwZR6MRe4xt1/UxZO', 'ADMIN', '2022-11-24 13:00', '2022-11-24 13:01'),
       ('diqn@abv.bg', '$2a$10$6S.HoSbYYkrk4mtFYbb1yuolGbupLBEVDcbFTwZR6MRe4xt1/UxZO', 'TEACHER', '2022-11-24 13:00', '2022-11-24 13:01'),
       ('violeta@abv.bg', '$2a$10$6S.HoSbYYkrk4mtFYbb1yuolGbupLBEVDcbFTwZR6MRe4xt1/UxZO', 'TEACHER', '2022-11-24 13:00', '2022-11-24 13:01'),
       ('hristo@abv.bg', '$2a$10$6S.HoSbYYkrk4mtFYbb1yuolGbupLBEVDcbFTwZR6MRe4xt1/UxZO', 'TEACHER', '2022-11-24 13:00', '2022-11-24 13:01'),
       ('dimitrichka@abv.bg', '$2a$10$6S.HoSbYYkrk4mtFYbb1yuolGbupLBEVDcbFTwZR6MRe4xt1/UxZO', 'TEACHER', '2022-11-24 13:00', '2022-11-24 13:01'),
       ('maq@abv.bg', '$2a$10$6S.HoSbYYkrk4mtFYbb1yuolGbupLBEVDcbFTwZR6MRe4xt1/UxZO', 'TEACHER', '2022-11-24 13:00', '2022-11-24 13:01');

INSERT INTO teacher (academic_rank_bg, academic_rank_en, first_name_bg, first_name_en, last_name_bg, last_name_en, ucn, archived, user_id)
VALUES ('гл. ас.', 'assist.', 'Диян', 'Diqn', 'Иванов', 'Ivanov', '1111111111', false, 2),
       ('доц.', 'assoc. prof.', 'Виолета', 'Violeta', 'Тодорова', 'Todorova', '1111111112', false, 3),
       ('инж.', 'eng.', 'Христо', 'Hristo', 'Ненов', 'Nenov', '1111111113', false, 4),
       ('гл. ас.', 'assist.', 'Димитричка', 'Dimitrichka', 'Иванова', 'Iwanowa', '1111111114', false, 5),
       ('проф.', 'prof.', 'Мая', 'Maq', 'Тодорова', 'Todorova', '1111111115', false, 6);

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

INSERT INTO specialty (name_bg, name_en, created_date, archived, department_id)
VALUES ('СИ', 'SI', '2023-11-24 13:00', false, 12),
       ('КСТ', 'KST', '2023-11-24 13:00', false, 12),
       ('СМ', 'SM', '2023-11-24 13:00', false, 7),
       ('СП', 'SP', '2023-11-24 13:00', false, 2);

INSERT INTO subject (name_bg, name_en, type, active, archived, created_date)
VALUES ('УИС', 'UIS', 'LECTURE', true, false, '2023-11-24 13:00'),
       ('УИС', 'UIS', 'EXERCISE', true, false, '2023-11-24 13:00'),
       ('УИС', 'UIS', 'COURSEWORK', true, false, '2023-11-24 13:00'),
       ('ППC#', 'PPC#', 'LECTURE', true, false, '2023-11-24 13:00'),
       ('ППC#', 'PPC#', 'EXERCISE', true, false, '2023-11-24 13:00'),
       ('ППC#', 'PPC#', 'COURSEWORK', true, false, '2023-11-24 13:00'),
       ('ПМС', 'PMS', 'LECTURE', true, false, '2023-11-24 13:00'),
       ('ПМС', 'PMS', 'EXERCISE', true, false, '2023-11-24 13:00'),
       ('Андроид БТМУ', 'Android BTMU', 'LECTURE', true, false, '2023-11-24 13:00'),
       ('Андроид БТМУ', 'Android BTMU', 'EXERCISE', true, false, '2023-11-24 13:00'),
       ('ИМУ', 'IMU', 'LECTURE', true, false, '2023-11-24 13:00'),
       ('ИМУ', 'IMU', 'EXERCISE', true, false, '2023-11-24 13:00'),
       ('СКМ', 'SKM', 'LECTURE', true, false, '2023-11-24 13:00'),
       ('СКМ', 'SKM', 'EXERCISE', true, false, '2023-11-24 13:00'),
       ('ВК', 'VK', 'LECTURE', true, false, '2023-11-24 13:00'),
       ('ВК', 'VK', 'EXERCISE', true, false, '2023-11-24 13:00'),
       ('ВК', 'VK', 'COURSEWORK', true, false, '2023-11-24 13:00'),
       ('КА', 'KA', 'LECTURE', true, false, '2023-11-24 13:00');

INSERT INTO course (degree, specialty_id, year, mode, week, start_week, end_week, hours_per_week, meetings_per_week, teacher_id, room_id, subject_id, active, archived)
VALUES ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'EVEN', null, null, 4, 2, 1, 1, 3, true, false),
       ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'ODD', 1, 10, 4, 2, 1, 2, 1, true, false),
       ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'ALL', null, null, 4, 2, 1, 3, 2, true, false),
       ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'ALL', null, null, 4, 2, 2, 4, 4, true, false),
       ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'ALL', null, null, 4, 2, 2, 4, 5, true, false),
       ('MASTER_BG_1_5', 1, 'II', 'FULL_TIME', 'ALL', null, null, 4, 2, 5, 6, 10, true, false),
       ('MASTER_BG_1_5', 2, 'II', 'PART_TIME', 'ALL', null, null, 4, 2, 5, 6, 10, true, false),
       ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'EVEN', 2, 7, 4, 2, 2, 4, 6, true, false),
       ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'ALL', null, null, 4, 2, 3, 8, 7, true, false),
       ('MASTER_EN_1_5', 4, 'II', 'PART_TIME', 'ALL', null, null, 4, 2, 5, 6, 10, true, false),
       ('MASTER_EN_1_5', 4, 'II', 'PART_TIME', 'ALL', null, null, 4, 2, 5, 6, 1, true, false),
       ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'ALL', 1, 4, 4, 2, 3, 7, 8, true, false),
       ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'ALL', null, null, 4, 2, 4, 5, 9, true, false),
       ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'ALL', null, null, 4, 2, 5, 6, 10, true, false),
       ('MASTER_BG_1_5', 2, 'II', 'PART_TIME', 'EVEN', null, null, 4, 2, 5, 6, 3, true, false),
       ('MASTER_BG_1_5', 2, 'II', 'PART_TIME', 'ALL', null, null, 4, 2, 4, 5, 5, true, false),
       ('MASTER_BG_1_5', 2, 'II', 'PART_TIME', 'ALL', 1, 10, 4, 2, 3, 5, 7, true, false);

INSERT INTO university_group (name, created_date)
values ('I', '2023-11-24 13:00'),
       ('II', '2023-11-24 13:00');

INSERT INTO course_group (course_id, group_id)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (4, 1),
       (7, 1),
       (10, 1),
       (11, 2);

INSERT INTO academic_year (year, semester)
VALUES (2022, 'WINTER'),
       (2023, 'SUMMER');

INSERT INTO schedule (day, start_time, end_time, hex_color, status, course_id, group_id, academic_year_id)
VALUES ('MONDAY', '08:45', '11:00', '#6E9EF7', 'PENDING', 1, 1, 1),
       ('WEDNESDAY', '17:15', '19:15', '#D884F7', 'PENDING', 8, null, 1),
       ('MONDAY', '13:45', '15:00', '#C496F1', 'PENDING', 2, 1, 1),
       ('TUESDAY', '07:30', '10:00', '#7EFCCB', 'PENDING', 4, 1, 1),
       ('THURSDAY', '12:00', '13:15', '#F7EE84', 'PENDING', 11, 2, 1),
       ('TUESDAY', '12:30', '14:30', '#7EFCEB', 'PENDING', 6, null, 1),
       ('WEDNESDAY', '15:00', '17:00', '#84AFF7', 'PENDING', 7, 1, 1),
       ('FRIDAY', '09:15', '11:30', '#8494F7', 'PENDING', 13, null, 1),
       ('WEDNESDAY', '09:15', '11:15', '#BAF784', 'PENDING', 9, null, 1),
       ('THURSDAY', '09:00', '11:15', '#84F7EE', 'PENDING', 10, 1, 1),
       ('MONDAY', '16:00', '18:00', '#F196B4', 'PENDING', 3, 2, 1),
       ('THURSDAY', '16:00', '18:15', '#F484F7', 'PENDING', 12, null, 1),
       ('TUESDAY', '11:30', '13:30', '#FCDF7E', 'PENDING', 5, null, 1),
       ('FRIDAY', '14:00', '16:15', '#84F7A5', 'PENDING', 14, null, 1);