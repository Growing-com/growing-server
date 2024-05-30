# 1. attendance
create table attendance
(
    id             bigint auto_increment not null primary key,
    created_at     datetime(6)           not null,
    created_by     bigint                null,
    updated_at     datetime(6)           not null,
    updated_by     bigint                null,
    team_member_id bigint                not null,
    week           date                  not null,
    status         varchar(255)          not null,
    etc            varchar(255)          null
) engine = InnoDB;

create unique index uk_attendance_team_member_id_and_week on attendance (team_member_id, week);

# 2. weekly_personal_attendance
CREATE TABLE weekly_personal_attendance
(
    id            bigint auto_increment not null primary key,
    created_at    datetime(6)           not null,
    created_by    bigint                null,
    updated_at    datetime(6)           not null,
    updated_by    bigint                null,
    user_id       bigint                not null,
    user_name     varchar(255)          not null,
    user_grade    int                   not null,
    user_phone    varchar(255)          not null,
    user_sex      varchar(255)          not null,
    duty          varchar(255)          not null,
    term_id       bigint                not null,
    team_id       bigint                not null,
    manager_id    bigint                not null,
    manager_name  varchar(255)          not null,
    leader_id     bigint                not null,
    leader_name   varchar(255)          not null,
    attendance_id bigint                not null,
    week          date                  not null,
    status        varchar(255)          not null,
    etc           varchar(255)          null
);

create unique index uk_weekly_personal_attendance_user_id_and_week on weekly_personal_attendance (user_id, week);

# 3. weekly_attendance
create table weekly_attendance
(
    id                   bigint auto_increment not null primary key,
    created_at           datetime(6)           not null,
    created_by           bigint                null,
    updated_at           datetime(6)           not null,
    updated_by           bigint                null,
    week                 date                  not null,
    total_registered     bigint                not null,
    total_attendance     bigint                not null,
    total_online         bigint                not null,
    total_absent         bigint                not null,
    male_registered      bigint                not null,
    male_attendance      bigint                not null,
    female_registered    bigint                not null,
    female_attendance    bigint                not null,
    new_comer_registered bigint                not null,
    new_comer_attendance bigint                not null,
    new_visited          bigint                not null
) engine = InnoDB;

create unique index uk_weekly_attendance_week on weekly_attendance (week);

# 4. weekly_manager_attendance
create table weekly_manager_attendance
(
    id                   bigint auto_increment not null primary key,
    created_at           datetime(6)           not null,
    created_by           bigint                null,
    updated_at           datetime(6)           not null,
    updated_by           bigint                null,
    weekly_attendance_id bigint                null,
    manager_id           bigint                not null,
    manager_name         varchar(255)          not null,
    total_registered     bigint                not null,
    total_attendance     bigint                not null
) engine = InnoDB;

create unique index uk_weekly_manager_attendance_weekly_attendance_id_and_manager_id
    on weekly_manager_attendance (weekly_attendance_id, manager_id);

# 5. weekly_leader_attendance
create table weekly_leader_attendance
(
    id                   bigint auto_increment not null primary key,
    created_at           datetime(6)           not null,
    created_by           bigint                null,
    updated_at           datetime(6)           not null,
    updated_by           bigint                null,
    weekly_attendance_id bigint                null,
    manager_id           bigint                not null,
    manager_name         varchar(255)          not null,
    leader_id            bigint                not null,
    leader_name          varchar(255)          not null,
    leader_phone         varchar(255)          not null,
    total_registered     bigint                not null,
    total_attendance     bigint                not null
) engine = InnoDB;

create unique index uk_weekly_leader_attendance_weekly_attendance_id_and_leader_id
    on weekly_leader_attendance (weekly_attendance_id, leader_id);

# 6. weekly_grade_attendance
create table weekly_grade_attendance
(
    id                   bigint auto_increment not null primary key,
    created_at           datetime(6)           not null,
    created_by           bigint                null,
    updated_at           datetime(6)           not null,
    updated_by           bigint                null,
    grade                int                   not null,
    weekly_attendance_id bigint                null,
    total_registered     bigint                not null,
    total_attendance     bigint                not null
) engine = InnoDB;

create unique index uk_weekly_grade_attendance_weekly_attendance_and_grade
    on weekly_grade_attendance (weekly_attendance_id, grade);

# 7. term_attendance
create table term_attendance
(
    id         bigint auto_increment not null primary key,
    created_at datetime(6)           not null,
    created_by bigint                null,
    updated_at datetime(6)           not null,
    updated_by bigint                null,
    term_id    bigint                not null
) engine = InnoDB;

create unique index uk_term_attendance_term on term_attendance (term_id);

# 8. term_personal_attendance
create table term_personal_attendance
(
    id                 bigint auto_increment not null primary key,
    created_at         datetime(6)           not null,
    created_by         bigint                null,
    updated_at         datetime(6)           not null,
    updated_by         bigint                null,
    term_attendance_id bigint                null,
    user_id            bigint                not null,
    total_week_passed  bigint                not null,
    total_attend       bigint                not null,
    total_online       bigint                not null,
    total_absent       bigint                not null
) engine = InnoDB;

create index fk_term_personal_attendance_to_term_attendance
    on term_personal_attendance (term_attendance_id);

create index fk_term_personal_attendance_to_user
    on term_personal_attendance (user_id);
