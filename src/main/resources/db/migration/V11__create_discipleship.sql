create table discipleship
(
    id         bigint auto_increment not null primary key,
    created_at datetime(6)           not null,
    created_by varchar(255)          null,
    updated_at datetime(6)           not null,
    updated_by varchar(255)          null,
    name       varchar(255)          not null,
    start_date date                  null,
    end_date   date                  null,
    etc        varchar(255)          null
) engine = InnoDB;

create unique index uk_discipleship_name on discipleship (name);

create table discipleship_member
(
    id              bigint auto_increment not null primary key,
    created_at      datetime(6)           not null,
    created_by      varchar(255)          null,
    updated_at      datetime(6)           not null,
    updated_by      varchar(255)          null,
    discipleship_id bigint                null,
    user_id         bigint                not null
) engine = InnoDB;
