--liquibase formatted sql

--changeset ivaylo_ivanov:1
CREATE TABLE course_unf (
    id BIGINT,
    degree VARCHAR(31) NOT NULL,
    class_year VARCHAR(7) NOT NULL,
    group_name VARCHAR(15) NOT NULL,
    group_created_date VARCHAR(255) NOT NULL,
    teacher_ucn CHAR(10) NOT NULL,
    first_name VARCHAR(63) NOT NULL,
    last_name VARCHAR(63) NOT NULL
);

CREATE TABLE course_1nf (
    id BIGINT,
    degree VARCHAR(31) NOT NULL,
    class_year VARCHAR(7) NOT NULL,
    group_name VARCHAR(15) NOT NULL,
    group_created_date TIMESTAMP NOT NULL,
    teacher_ucn CHAR(10) NOT NULL,
    first_name VARCHAR(63) NOT NULL,
    last_name VARCHAR(63) NOT NULL,
    PRIMARY KEY (id, group_name)
);

CREATE TABLE course_2nf (
    id BIGINT PRIMARY KEY,
    degree VARCHAR(31) NOT NULL,
    class_year VARCHAR(7) NOT NULL,
    teacher_ucn CHAR(10) NOT NULL,
    first_name VARCHAR(63) NOT NULL,
    last_name VARCHAR(63) NOT NULL
);

CREATE TABLE university_group_2nf (
    id BIGINT PRIMARY KEY,
    name VARCHAR(15) NOT NULL,
    created_date TIMESTAMP NOT NULL
);

CREATE TABLE course_group_2nf (
    course_id BIGINT,
    group_id BIGINT,
    PRIMARY KEY (course_id, group_id),
    FOREIGN KEY (course_id) REFERENCES course_2nf (id),
    FOREIGN KEY (group_id) REFERENCES university_group_2nf (id)
);

CREATE TABLE teacher_3nf (
    id BIGINT PRIMARY KEY,
    teacher_ucn CHAR(10) NOT NULL,
    first_name VARCHAR(63) NOT NULL,
    last_name VARCHAR(63) NOT NULL
);

CREATE TABLE course_3nf (
    id BIGINT PRIMARY KEY,
    degree VARCHAR(31) NOT NULL,
    class_year VARCHAR(7) NOT NULL,
    teacher_id BIGINT NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES teacher_3nf (id)
);

INSERT INTO course_unf (id, degree, class_year, group_name, group_created_date, teacher_ucn, first_name, last_name)
VALUES (1, 'MASTER_BG_1_5', 'I', 'I, II', '2022-11-24 15:30, 2022-11-25 16:00', '9011213451', 'Иван', 'Тодоров'),
       (2, 'MASTER_BG_2_5', 'II', 'I', '2022-11-24 15:30', '9011213451', 'Иван', 'Тодоров'),
       (3, 'BACHELOR_EN', 'IV', 'I, II, III', '2022-11-24 15:30, 2022-11-25 16:00, 2022-11-26 17:15', '8901052511', 'Петър', 'Георгиев');

INSERT INTO course_1nf (id, degree, class_year, group_name, group_created_date, teacher_ucn, first_name, last_name)
VALUES (1, 'MASTER_BG_1_5', 'I', 'I', '2022-11-24 15:30', '9011213451', 'Иван', 'Тодоров'),
       (1, 'MASTER_BG_1_5', 'I', 'II', '2022-11-25 16:00', '9011213451', 'Иван', 'Тодоров'),
       (2, 'MASTER_BG_2_5', 'II', 'I', '2022-11-24 15:30', '9011213451', 'Иван', 'Тодоров'),
       (3, 'BACHELOR_EN', 'IV', 'I', '2022-11-24 15:30', '8901052511', 'Петър', 'Георгиев'),
       (3, 'BACHELOR_EN', 'IV', 'II', '2022-11-25 16:00', '8901052511', 'Петър', 'Георгиев'),
       (3, 'BACHELOR_EN', 'IV', 'III', '2022-11-26 17:15', '8901052511', 'Петър', 'Георгиев');

INSERT INTO course_2nf (id, degree, class_year, teacher_ucn, first_name, last_name)
VALUES (1, 'MASTER_BG_1_5', 'I', '9011213451', 'Иван', 'Тодоров'),
       (2, 'MASTER_BG_2_5', 'II', '9011213451', 'Иван', 'Тодоров'),
       (3, 'BACHELOR_EN', 'IV', '8901052511', 'Петър', 'Георгиев');

INSERT INTO university_group_2nf (id, name, created_date)
VALUES (1, 'I', '2022-11-24 15:30'),
       (2, 'II', '2022-11-25 16:00'),
       (3, 'III', '2022-11-26 17:15');

INSERT INTO course_group_2nf (course_id, group_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (3, 1),
       (3, 2),
       (3, 3);

INSERT INTO teacher_3nf (id, teacher_ucn, first_name, last_name)
VALUES (1, '9011213451', 'Иван', 'Тодоров'),
       (2, '8901052511', 'Петър', 'Георгиев');

INSERT INTO course_3nf (id, degree, class_year, teacher_id)
VALUES (1, 'MASTER_BG_1_5', 'I', 1),
       (2, 'MASTER_BG_2_5', 'II', 1),
       (3, 'BACHELOR_EN', 'IV', 2);