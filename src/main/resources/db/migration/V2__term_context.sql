# 1. term
create table term
(
    id         bigint auto_increment not null primary key,
    created_at datetime(6)           not null,
    created_by bigint                null,
    updated_at datetime(6)           not null,
    updated_by bigint                null,
    name       varchar(255)          not null,
    start_date date                  not null,
    end_date   date                  not null,
    is_active  tinyint(1)            not null
) engine = InnoDB;

# 2. team
create table team
(
    id         bigint auto_increment not null primary key,
    created_at datetime(6)           not null,
    created_by bigint                null,
    updated_at datetime(6)           not null,
    updated_by bigint                null,
    name       varchar(255)          not null,
    type       varchar(255)          null,
    leader_id  bigint                not null,
    manager_id bigint                not null,
    term_id    bigint                not null
) engine = InnoDB;

create index fk_team_to_leader on team (leader_id);
create index fk_team_to_manager on team (manager_id);
create index fk_team_to_term on team (term_id);

# 3. team_leader
create table team_leader
(
    id         bigint auto_increment not null primary key,
    created_at datetime(6)           not null,
    created_by bigint                null,
    updated_at datetime(6)           not null,
    updated_by bigint                null,
    team_id    bigint                not null,
    leader_id  bigint                not null,
    duty       varchar(255)          null
) engine = InnoDB;

create unique index uk_team_leader_team_id_and_leader_id on team_leader (team_id, leader_id);
create index fk_team_leader_to_leader on team_leader (leader_id);

# 4. team_member
create table team_member
(
    id         bigint auto_increment not null primary key,
    created_at datetime(6)           not null,
    created_by bigint                null,
    updated_at datetime(6)           not null,
    updated_by bigint                null,
    team_id    bigint                null,
    member_id  bigint                not null,
    duty       varchar(255)          null
) engine = InnoDB;

create unique index uk_team_member_team_id_and_member_id on team_member (team_id, member_id);
create index fk_team_member_to_member on team_member (member_id);

# 5. new_comer_history
create table new_comer_history
(
    id                   bigint auto_increment not null primary key,
    created_at           datetime(6)           not null,
    created_by           bigint                null,
    updated_at           datetime(6)           not null,
    updated_by           bigint                null,
    user_id              bigint                not null,
    line_up_week         date                  null,
    line_out_week        date                  null,
    grade_at_first_visit int                   not null,
    new_comer_team_id    bigint                not null,
    first_plant_team_id  bigint                null
) engine = InnoDB;

create unique index uk_new_comer_history_user_id on new_comer_history (user_id);
