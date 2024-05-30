# 1. users
create table users
(
    id           bigint auto_increment not null primary key,
    created_at   datetime(6)           not null,
    created_by   bigint                null,
    updated_at   datetime(6)           not null,
    updated_by   bigint                null,
    username     varchar(255)          not null,
    password     varchar(255)          not null,
    role         varchar(255)          not null,
    name         varchar(255)          not null,
    phone_number varchar(255)          null,
    sex          varchar(255)          not null,
    birth        date                  null,
    grade        int                   not null,
    is_active    tinyint(1)            not null,
    visit_date   date                  null,
    etc          text                  null
) engine = InnoDB;

create unique index uk_users_username on users (username);
