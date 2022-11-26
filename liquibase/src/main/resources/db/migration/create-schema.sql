--liquibase formatted sql

--changeset ivaylo_ivanov:1
CREATE TABLE university_user (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password CHAR(60) NOT NULL,
    role VARCHAR(31) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    password_updated_date TIMESTAMP
);

CREATE TABLE teacher (
    id BIGSERIAL PRIMARY KEY,
    academic_rank_bg VARCHAR(63) NOT NULL,
    academic_rank_en VARCHAR(63) NOT NULL,
    first_name_bg VARCHAR(63) NOT NULL,
    first_name_en VARCHAR(63) NOT NULL,
    last_name_bg VARCHAR(63) NOT NULL,
    last_name_en VARCHAR(63) NOT NULL,
    ucn CHAR(10) NOT NULL UNIQUE,
    archived BOOLEAN NOT NULL DEFAULT FALSE,
    user_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (user_id) REFERENCES university_user (id)
);

CREATE TABLE teacher_free_time (
    id BIGSERIAL PRIMARY KEY,
    day VARCHAR(15) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    teacher_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (teacher_id) REFERENCES teacher (id)
);

CREATE TABLE faculty (
    id BIGSERIAL PRIMARY KEY,
    name_bg VARCHAR(255) NOT NULL UNIQUE,
    name_en VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE department (
    id BIGSERIAL PRIMARY KEY,
    name_bg VARCHAR(255) NOT NULL UNIQUE,
    name_en VARCHAR(255) NOT NULL UNIQUE,
    faculty_id BIGINT NOT NULL,
    FOREIGN KEY (faculty_id) REFERENCES faculty (id)
);

CREATE TABLE room (
    id BIGSERIAL PRIMARY KEY,
    number_bg VARCHAR(15) NOT NULL UNIQUE,
    number_en VARCHAR(15) NOT NULL UNIQUE
);

CREATE TABLE specialty (
    id BIGSERIAL PRIMARY KEY,
    name_bg VARCHAR(255) NOT NULL UNIQUE,
    name_en VARCHAR(255) NOT NULL UNIQUE,
    created_date TIMESTAMP NOT NULL,
    archived BOOLEAN NOT NULL DEFAULT FALSE,
    department_id BIGINT NOT NULL,
    FOREIGN KEY (department_id) REFERENCES department (id)
);

CREATE TABLE subject (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    abbreviation_bg VARCHAR(63) NOT NULL UNIQUE,
    abbreviation_en VARCHAR(63) NOT NULL UNIQUE,
    type VARCHAR(31) NOT NULL,
    week VARCHAR(15) NOT NULL,
    start_week SMALLINT,
    end_week SMALLINT,
    hours_per_week SMALLINT NOT NULL,
    meetings_per_week SMALLINT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    archived BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE course (
    id BIGSERIAL PRIMARY KEY,
    degree VARCHAR(63) NOT NULL,
    specialty_id BIGINT NOT NULL,
    year VARCHAR(63) NOT NULL,
    mode VARCHAR(31) NOT NULL,
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

CREATE TABLE course_time (
    id BIGSERIAL PRIMARY KEY,
    day VARCHAR(15) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    course_id BIGINT NOT NULL,
    FOREIGN KEY (course_id) REFERENCES course (id)
);

CREATE TABLE university_group (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(15) NOT NULL,
    created_date TIMESTAMP NOT NULL
);

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

CREATE TABLE schedule (
    id BIGSERIAL PRIMARY KEY,
    day VARCHAR(15) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    course_id BIGINT NOT NULL,
    academic_year_id BIGINT NOT NULL,
    FOREIGN KEY (course_id) REFERENCES course (id),
    FOREIGN KEY (academic_year_id) REFERENCES academic_year (id)
);
