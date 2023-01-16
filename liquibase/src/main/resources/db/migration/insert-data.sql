--liquibase formatted sql

--changeset ivaylo_ivanov:1
INSERT INTO university_user (email, password, role, created_date, password_updated_date)
VALUES ('admin@abv.bg', '$2a$10$6S.HoSbYYkrk4mtFYbb1yuolGbupLBEVDcbFTwZR6MRe4xt1/UxZO', 'ADMIN', '2021-11-24 13:00', '2022-12-13 15:30'),
       ('diyan@abv.bg', '$2a$10$6S.HoSbYYkrk4mtFYbb1yuolGbupLBEVDcbFTwZR6MRe4xt1/UxZO', 'TEACHER', '2021-11-24 13:00', '2022-11-25 11:15'),
       ('violeta@abv.bg', '$2a$10$6S.HoSbYYkrk4mtFYbb1yuolGbupLBEVDcbFTwZR6MRe4xt1/UxZO', 'TEACHER', '2021-11-24 13:00', '2022-11-24 12:20'),
       ('ivvo98@gmail.com', '$2a$10$6S.HoSbYYkrk4mtFYbb1yuolGbupLBEVDcbFTwZR6MRe4xt1/UxZO', 'TEACHER', '2021-11-24 13:00', '2022-12-16 09:14'),
       ('dimitrichka@abv.bg', '$2a$10$6S.HoSbYYkrk4mtFYbb1yuolGbupLBEVDcbFTwZR6MRe4xt1/UxZO', 'TEACHER', '2021-11-24 13:00', '2022-12-13 08:15'),
       ('maya@abv.bg', '$2a$10$6S.HoSbYYkrk4mtFYbb1yuolGbupLBEVDcbFTwZR6MRe4xt1/UxZO', 'TEACHER', '2021-11-24 13:00', '2022-11-17 19:00'),
       ('valdimir@abv.bg', '$2a$10$6S.HoSbYYkrk4mtFYbb1yuolGbupLBEVDcbFTwZR6MRe4xt1/UxZO', 'TEACHER', '2021-11-24 13:00', '2022-11-30 22:15'),
       ('neli@abv.bg', '$2a$10$6S.HoSbYYkrk4mtFYbb1yuolGbupLBEVDcbFTwZR6MRe4xt1/UxZO', 'TEACHER', '2021-11-24 13:00', '2022-11-28 23:11'),
       ('rosen@abv.bg', '$2a$10$6S.HoSbYYkrk4mtFYbb1yuolGbupLBEVDcbFTwZR6MRe4xt1/UxZO', 'TEACHER', '2021-11-24 13:00', '2022-12-14 16:45'),
       ('ivelin@abv.bg', '$2a$10$6S.HoSbYYkrk4mtFYbb1yuolGbupLBEVDcbFTwZR6MRe4xt1/UxZO', 'TEACHER', '2021-11-24 13:00', '2022-11-29 17:11'),
       ('martin@abv.bg', '$2a$10$6S.HoSbYYkrk4mtFYbb1yuolGbupLBEVDcbFTwZR6MRe4xt1/UxZO', 'TEACHER', '2021-11-24 13:00', null);

INSERT INTO teacher (academic_rank_bg, academic_rank_en, first_name_bg, first_name_en, last_name_bg, last_name_en, ucn, archived, user_id)
VALUES ('гл. ас.', 'assist.', 'Диян', 'Diyan', 'Иванов', 'Ivanov', '9502031453', false, 2),
       ('доц.', 'assoc. prof.', 'Виолета', 'Violeta', 'Тодорова', 'Todorova', '9210111342', false, 3),
       ('инж.', 'eng.', 'Христо', 'Hristo', 'Петров', 'Petrov', '9010021347', false, 4),
       ('гл. ас.', 'assist.', 'Димитричка', 'Dimitrichka', 'Иванова', 'Ivanova', '9401042452', false, 5),
       ('проф.', 'prof.', 'Мая', 'Maya', 'Тодорова', 'Todorova', '9902041455', false, 6),
       ('ас.', 'assist.', 'Владимир', 'Vladimir', 'Иванова', 'Ivanova', '8911129142', false, 7),
       ('инж.', 'eng.', 'Нели', 'Neli', 'Росева', 'Roseva', '9210101253', false, 8),
       ('проф.', 'prof.', 'Росен', 'Rosen', 'Тодорова', 'Todorova', '7510110102', false, 9),
       ('гл. ас.', 'assist.', 'Ивелин', 'Ivelin', 'Георгиев', 'Georgiev', '6610023452', false, 10),
       ('доц.', 'assoc. prof.', 'Мартин', 'Martin', 'Василев', 'Vasilev', '6310013357', false, 11);

INSERT INTO faculty (name_bg, name_en)
VALUES ('МТФ', 'MTF'),
       ('КФ', 'KF'),
       ('EФ', 'EF'),
       ('ФИТА', 'FITA');

INSERT INTO department (name_bg, name_en, faculty_id)
VALUES ('СТ', 'ST', 1),
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
       ('СИТ', 'SIT', 4),
       ('ИМ', 'IM', 1);

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
VALUES ('СИ', 'SI', '2022-12-15 13:15', false, 12),
       ('КСТ', 'KST', '2022-11-10 12:00', false, 12),
       ('СМ', 'SM', '2022-12-18 16:20', false, 7),
       ('СП', 'SP', '2022-12-15 16:10', false, 2),
       ('ИМ', 'IM', '2022-11-13 12:13', false, 13),
       ('К', 'C', '2022-12-10 11:10', false, 12),
       ('САС', 'SAB', '2022-12-12 14:11', false, 10);

INSERT INTO subject (name_bg, name_en, type, active, archived, created_date)
VALUES ('УИС', 'UIS', 'LECTURE', true, false, '2022-11-11 15:15'),
       ('УИС', 'UIS', 'EXERCISE', true, false, '2022-12-01 13:10'),
       ('УИС', 'UIS', 'COURSEWORK', true, false, '2022-12-13 14:16'),
       ('ППC#', 'PPC#', 'LECTURE', true, false, '2022-12-12 11:23'),
       ('ППC#', 'PPC#', 'EXERCISE', true, false, '2022-11-02 17:21'),
       ('ППC#', 'PPC#', 'COURSEWORK', true, false, '2022-11-24 14:15'),
       ('ПМС', 'PMS', 'LECTURE', true, false, '2022-11-03 16:14'),
       ('ПМС', 'PMS', 'EXERCISE', true, false, '2023-01-10 17:11'),
       ('Андроид БТМУ', 'Android BTMU', 'LECTURE', true, false, '2022-11-07 12:20'),
       ('Андроид БТМУ', 'Android BTMU', 'EXERCISE', true, false, '2022-11-01 13:15'),
       ('УРК', 'URK', 'LECTURE', true, false, '2022-11-09 18:30'),
       ('УРК', 'URK', 'EXERCISE', true, false, '2022-11-10 09:11'),
       ('СКМ', 'SKM', 'LECTURE', true, false, '2022-11-15 10:12'),
       ('СКМ', 'SKM', 'EXERCISE', true, false, '2022-12-14 14:17'),
       ('ВК', 'VK', 'LECTURE', true, false, '2022-12-19 13:16'),
       ('ВК', 'VK', 'EXERCISE', true, false, '2022-11-14 18:11'),
       ('ВК', 'VK', 'COURSEWORK', true, false, '2022-12-03 11:17'),
       ('КА', 'KA', 'LECTURE', true, false, '2022-11-13 14:30'),
       ('КА', 'KA', 'EXERCISE', true, false, '2022-10-15 14:15'),
       ('МК', 'QM', 'LECTURE', true, false, '2022-11-17 11:30'),
       ('МК', 'QM', 'EXERCISE', true, false, '2022-11-13 11:45'),
       ('МК', 'QM', 'COURSEWORK', true, false, '2022-11-16 11:50'),
       ('ПМ', 'PM', 'LECTURE', true, false, '2022-10-15 10:15'),
       ('ПМ', 'PM', 'EXERCISE', true, false, '2022-12-17 09:15'),
       ('ПТ', 'PT', 'LECTURE', true, false, '2022-11-16 10:55'),
       ('ПТ', 'PT', 'EXERCISE', true, false, '2022-11-12 10:11'),
       ('ИП', 'ЕЕ', 'LECTURE', false, false, '2022-12-03 13:30');

INSERT INTO course (degree, specialty_id, class_year, mode, week, start_week, end_week, hours_per_week, meetings_per_week, teacher_id, room_id, subject_id, active, archived)
VALUES ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'EVEN', null, null, 3, 1, 1, 1, 3, true, false),
       ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'ALL', null, null, 3, 1, 2, 2, 4, true, false),
       ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'ALL', null, null, 3, 1, 3, 3, 7, true, false),
       ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'ALL', 1, 10, 3, 1, 3, 3, 8, true, false),
       ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'ODD', 7, 15, 3, 1, 4, 4, 6, true, false),
       ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'ALL', null, null, 3, 1, 1, 5, 2, true, false),
       ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'ALL', null, null, 3, 1, 6, 6, 10, true, false),
       ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'ALL', null, null, 3, 1, 3, 6, 9, true, false),
       ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'ALL', null, null, 3, 1, 4, 1, 5, true, false),
       ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'ALL', null, null, 3, 1, 5, 2, 1, true, false),
       ('MASTER_BG_2_5', 6, 'II', 'PART_TIME', 'ALL', null, null, 3, 1, 7, 8, 11, true, false),
       ('MASTER_BG_2_5', 6, 'II', 'PART_TIME', 'ALL', null, null, 3, 1, 8, 2, 13, true, false),
       ('MASTER_BG_2_5', 6, 'II', 'PART_TIME', 'ALL', null, null, 3, 1, 9, 6, 15, true, false),
       ('MASTER_BG_2_5', 6, 'II', 'PART_TIME', 'ALL', null, null, 3, 1, 7, 1, 12, true, false),
       ('MASTER_BG_2_5', 6, 'II', 'PART_TIME', 'ALL', null, null, 3, 1, 9, 4, 16, true, false),
       ('MASTER_BG_2_5', 6, 'II', 'PART_TIME', 'ALL', null, null, 3, 1, 10, 8, 14, true, false),
       ('MASTER_BG_2_5', 6, 'II', 'PART_TIME', 'ALL', null, null, 3, 1, 1, 2, 18, true, false),
       ('MASTER_BG_2_5', 6, 'II', 'PART_TIME', 'ALL', null, null, 3, 1, 1, 1, 19, true, false),
       ('BACHELOR_EN', 5, 'III', 'FULL_TIME', 'ALL', null, null, 3, 1, 3, 8, 20, true, false),
       ('BACHELOR_EN', 5, 'III', 'FULL_TIME', 'ALL', null, null, 3, 1, 3, 8, 21, true, false),
       ('BACHELOR_EN', 5, 'III', 'FULL_TIME', 'EVEN', 7, 15, 3, 1, 3, 7, 22, true, false),
       ('BACHELOR_EN', 5, 'III', 'FULL_TIME', 'ALL', null, null, 3, 1, 5, 1, 23, true, false),
       ('BACHELOR_EN', 5, 'III', 'FULL_TIME', 'ALL', null, null, 3, 1, 1, 6, 24, true, false),
       ('BACHELOR_EN', 5, 'III', 'FULL_TIME', 'ALL', null, null, 3, 1, 8, 5, 25, true, false),
       ('BACHELOR_EN', 5, 'III', 'FULL_TIME', 'ALL', null, null, 3, 1, 8, 2, 26, true, false),
       ('BACHELOR_EN', 5, 'III', 'FULL_TIME', 'ALL', null, null, 3, 1, 2, 2, 27, true, false),
       ('MASTER_BG_2_5', 6, 'II', 'PART_TIME', 'ALL', null, null, 3, 1, 2, 2, 27, true, false),
       ('MASTER_BG_1_5', 1, 'I', 'FULL_TIME', 'ODD', 1, 10, 3, 1, 5, 1, 27, false, false);

INSERT INTO course_time (workday, start_time, end_time, course_id)
VALUES ('MONDAY', '08:45', '11:30', 1),
       ('MONDAY', '11:30', '14:15', 2),
       ('TUESDAY', '09:00', '11:45', 3),
       ('TUESDAY', '11:45', '14:30', 4),
       ('THURSDAY', '08:00', '10:45', 4),
       ('WEDNESDAY', '08:30', '11:15', 5),
       ('WEDNESDAY', '11:15', '14:00', 6),
       ('WEDNESDAY', '14:00', '16:45', 7),
       ('THURSDAY', '10:45', '13:30', 8),
       ('FRIDAY', '12:00', '14:45', 9),
       ('FRIDAY', '14:45', '17:30', 10),
       ('TUESDAY', '11:00', '13:45', 11),
       ('TUESDAY', '13:45', '16:30', 12),
       ('WEDNESDAY', '09:00', '11:45', 13),
       ('WEDNESDAY', '11:45', '14:30', 14),
       ('WEDNESDAY', '14:30', '17:15', 15),
       ('THURSDAY', '10:00', '12:45', 16),
       ('THURSDAY', '12:45', '15:30', 17),
       ('THURSDAY', '15:30', '18:15', 18),
       ('WEDNESDAY', '09:00', '11:45', 19),
       ('WEDNESDAY', '11:45', '14:30', 20),
       ('WEDNESDAY', '14:30', '17:15', 25),
       ('THURSDAY', '10:45', '13:30', 22),
       ('THURSDAY', '13:30', '16:15', 21),
       ('FRIDAY', '11:15', '14:00', 24),
       ('FRIDAY', '14:00', '16:45', 23);

INSERT INTO university_group (name, created_date)
values ('I', '2022-11-14 14:13'),
       ('II', '2022-11-13 18:16');

INSERT INTO course_group (course_id, group_id)
VALUES (4, 1),
       (4, 2),
       (28, 1),
       (28, 2);

INSERT INTO academic_year (study_year, semester)
VALUES (2022, 'WINTER'),
       (2023, 'SUMMER');
