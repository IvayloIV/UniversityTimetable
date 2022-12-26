--liquibase formatted sql

--changeset ivaylo_ivanov:1
CREATE TABLE university_user (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password CHAR(60) NOT NULL,
    role VARCHAR(31) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    password_updated_date TIMESTAMP
);

ALTER TABLE university_user ADD CONSTRAINT email_length_check CHECK (length(email) > 4);
ALTER TABLE university_user ADD CONSTRAINT password_length_check CHECK (length(password) = 60);
ALTER TABLE university_user ADD CONSTRAINT password_updated_date_check CHECK (password_updated_date > created_date);
ALTER TABLE university_user ADD CONSTRAINT role_check CHECK (role in ('ADMIN', 'TEACHER'));

CREATE TABLE teacher (
    id BIGSERIAL PRIMARY KEY,
    academic_rank_bg VARCHAR(63) NOT NULL,
    academic_rank_en VARCHAR(63) NOT NULL,
    first_name_bg VARCHAR(63) NOT NULL,
    first_name_en VARCHAR(63) NOT NULL,
    last_name_bg VARCHAR(63) NOT NULL,
    last_name_en VARCHAR(63) NOT NULL,
    ucn CHAR(10) NOT NULL,
    archived BOOLEAN NOT NULL DEFAULT FALSE,
    user_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (user_id) REFERENCES university_user (id)
);

ALTER TABLE teacher ADD CONSTRAINT academic_rank_bg_length_check CHECK (length(academic_rank_bg) > 2);
ALTER TABLE teacher ADD CONSTRAINT academic_rank_en_length_check CHECK (length(academic_rank_en) > 2);
ALTER TABLE teacher ADD CONSTRAINT first_name_bg_length_check CHECK (length(first_name_bg) > 1);
ALTER TABLE teacher ADD CONSTRAINT first_name_en_length_check CHECK (length(first_name_en) > 1);
ALTER TABLE teacher ADD CONSTRAINT last_name_bg_length_check CHECK (length(last_name_bg) > 1);
ALTER TABLE teacher ADD CONSTRAINT last_name_en_length_check CHECK (length(last_name_en) > 1);
ALTER TABLE teacher ADD CONSTRAINT ucn_length_check CHECK (length(ucn) = 10);

CREATE TABLE teacher_free_time (
    id BIGSERIAL PRIMARY KEY,
    day VARCHAR(15) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    teacher_id BIGINT NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES teacher (id)
);

ALTER TABLE teacher_free_time ADD CONSTRAINT end_time_check CHECK (end_time > start_time);
ALTER TABLE teacher_free_time ADD CONSTRAINT day_check CHECK (day in ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'));

CREATE TABLE faculty (
    id BIGSERIAL PRIMARY KEY,
    name_bg VARCHAR(255) NOT NULL UNIQUE,
    name_en VARCHAR(255) NOT NULL UNIQUE
);

ALTER TABLE faculty ADD CONSTRAINT name_bg_length_check CHECK (length(name_bg) > 1);
ALTER TABLE faculty ADD CONSTRAINT name_en_length_check CHECK (length(name_en) > 1);

CREATE TABLE department (
    id BIGSERIAL PRIMARY KEY,
    name_bg VARCHAR(255) NOT NULL UNIQUE,
    name_en VARCHAR(255) NOT NULL UNIQUE,
    faculty_id BIGINT NOT NULL,
    FOREIGN KEY (faculty_id) REFERENCES faculty (id)
);

ALTER TABLE department ADD CONSTRAINT name_bg_length_check CHECK (length(name_bg) >= 1);
ALTER TABLE department ADD CONSTRAINT name_en_length_check CHECK (length(name_en) >= 1);

CREATE TABLE room (
    id BIGSERIAL PRIMARY KEY,
    number_bg VARCHAR(15) NOT NULL UNIQUE,
    number_en VARCHAR(15) NOT NULL UNIQUE
);

ALTER TABLE room ADD CONSTRAINT number_bg_length_check CHECK (length(number_bg) > 2);
ALTER TABLE room ADD CONSTRAINT number_en_length_check CHECK (length(number_en) > 2);

CREATE TABLE specialty (
    id BIGSERIAL PRIMARY KEY,
    name_bg VARCHAR(255) NOT NULL,
    name_en VARCHAR(255) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    archived BOOLEAN NOT NULL DEFAULT FALSE,
    department_id BIGINT NOT NULL,
    FOREIGN KEY (department_id) REFERENCES department (id)
);

ALTER TABLE specialty ADD CONSTRAINT name_bg_length_check CHECK (length(name_bg) >= 1);
ALTER TABLE specialty ADD CONSTRAINT name_en_length_check CHECK (length(name_en) >= 1);

CREATE TABLE subject (
    id BIGSERIAL PRIMARY KEY,
    name_bg VARCHAR(63) NOT NULL,
    name_en VARCHAR(63) NOT NULL,
    type VARCHAR(31) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    archived BOOLEAN NOT NULL DEFAULT FALSE,
    created_date TIMESTAMP NOT NULL
);

ALTER TABLE subject ADD CONSTRAINT name_bg_length_check CHECK (length(name_bg) >= 1);
ALTER TABLE subject ADD CONSTRAINT name_en_length_check CHECK (length(name_en) >= 1);
ALTER TABLE subject ADD CONSTRAINT type_check CHECK (type in ('LECTURE', 'EXERCISE', 'COURSEWORK'));

CREATE TABLE course (
    id BIGSERIAL PRIMARY KEY,
    degree VARCHAR(31) NOT NULL,
    specialty_id BIGINT NOT NULL,
    year VARCHAR(7) NOT NULL,
    mode VARCHAR(15) NOT NULL,
    week VARCHAR(15) NOT NULL,
    start_week SMALLINT,
    end_week SMALLINT,
    hours_per_week SMALLINT NOT NULL,
    meetings_per_week SMALLINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    room_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    archived BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (specialty_id) REFERENCES specialty (id),
    FOREIGN KEY (teacher_id) REFERENCES teacher (id),
    FOREIGN KEY (room_id) REFERENCES room (id),
    FOREIGN KEY (subject_id) REFERENCES subject (id)
);

ALTER TABLE course ADD CONSTRAINT start_week_check CHECK (start_week >= 1);
ALTER TABLE course ADD CONSTRAINT end_week_check CHECK (end_week = null or end_week > start_week);
ALTER TABLE course ADD CONSTRAINT hours_per_week_check CHECK (hours_per_week >= 1);
ALTER TABLE course ADD CONSTRAINT meetings_per_week_check CHECK (meetings_per_week >= 1);
ALTER TABLE course ADD CONSTRAINT degree_check CHECK (degree in ('BACHELOR_BG', 'BACHELOR_EN', 'MASTER_BG_1_5', 'MASTER_EN_1_5', 'MASTER_BG_2_5', 'MASTER_EN_2_5'));
ALTER TABLE course ADD CONSTRAINT year_check CHECK (year in ('I', 'II', 'III', 'IV'));
ALTER TABLE course ADD CONSTRAINT mode_check CHECK (mode in ('FULL_TIME', 'PART_TIME'));
ALTER TABLE course ADD CONSTRAINT week_check CHECK (week in ('ALL', 'EVEN', 'ODD'));

CREATE TABLE course_time (
    id BIGSERIAL PRIMARY KEY,
    day VARCHAR(15) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    course_id BIGINT NOT NULL,
    FOREIGN KEY (course_id) REFERENCES course (id)
);

ALTER TABLE course_time ADD CONSTRAINT end_time_check CHECK (end_time > start_time);
ALTER TABLE course_time ADD CONSTRAINT day_check CHECK (day in ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'));

CREATE TABLE university_group (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(15) NOT NULL UNIQUE,
    created_date TIMESTAMP NOT NULL
);

ALTER TABLE university_group ADD CONSTRAINT name_length_check CHECK (length(name) >= 1);

CREATE TABLE course_group (
    course_id BIGSERIAL,
    group_id BIGSERIAL,
    PRIMARY KEY (course_id, group_id),
    FOREIGN KEY (course_id) REFERENCES course (id),
    FOREIGN KEY (group_id) REFERENCES university_group (id)
);

CREATE TABLE academic_year (
    id BIGSERIAL PRIMARY KEY,
    year SMALLINT NOT NULL,
    semester VARCHAR(15) NOT NULL,
    UNIQUE (year, semester)
);

ALTER TABLE academic_year ADD CONSTRAINT semester_check CHECK (semester in ('WINTER', 'SUMMER'));

CREATE TABLE schedule (
    id BIGSERIAL PRIMARY KEY,
    day VARCHAR(15) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    hex_color VARCHAR(15) NOT NULL,
    status VARCHAR(15) NOT NULL,
    course_id BIGINT NOT NULL,
    group_id BIGINT,
    academic_year_id BIGINT NOT NULL,
    FOREIGN KEY (course_id) REFERENCES course (id),
    FOREIGN KEY (academic_year_id) REFERENCES academic_year (id),
    FOREIGN KEY (group_id) REFERENCES university_group (id)
);

ALTER TABLE schedule ADD CONSTRAINT end_time_check CHECK (end_time > start_time);
ALTER TABLE schedule ADD CONSTRAINT hex_color_length_check CHECK (length(hex_color) > 3);
ALTER TABLE schedule ADD CONSTRAINT day_check CHECK (day in ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'));
ALTER TABLE schedule ADD CONSTRAINT status_check CHECK (status in ('PENDING', 'ACTIVE', 'INACTIVE'));