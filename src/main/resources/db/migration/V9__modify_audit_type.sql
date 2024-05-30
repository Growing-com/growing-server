alter table attendance drop column created_by;
alter table attendance add column created_by varchar(255) null;
alter table attendance drop column updated_by;
alter table attendance add column updated_by varchar(255) null;

alter table new_comer_history drop column created_by;
alter table new_comer_history add column created_by varchar(255) null;
alter table new_comer_history drop column updated_by;
alter table new_comer_history add column updated_by varchar(255) null;

alter table team drop column created_by;
alter table team add column created_by varchar(255) null;
alter table team drop column updated_by;
alter table team add column updated_by varchar(255) null;

alter table team_leader drop column created_by;
alter table team_leader add column created_by varchar(255) null;
alter table team_leader drop column updated_by;
alter table team_leader add column updated_by varchar(255) null;

alter table team_member drop column created_by;
alter table team_member add column created_by varchar(255) null;
alter table team_member drop column updated_by;
alter table team_member add column updated_by varchar(255) null;

alter table term drop column created_by;
alter table term add column created_by varchar(255) null;
alter table term drop column updated_by;
alter table term add column updated_by varchar(255) null;

alter table term_attendance drop column created_by;
alter table term_attendance add column created_by varchar(255) null;
alter table term_attendance drop column updated_by;
alter table term_attendance add column updated_by varchar(255) null;

alter table term_personal_attendance drop column created_by;
alter table term_personal_attendance add column created_by varchar(255) null;
alter table term_personal_attendance drop column updated_by;
alter table term_personal_attendance add column updated_by varchar(255) null;

alter table users drop column created_by;
alter table users add column created_by varchar(255) null;
alter table users drop column updated_by;
alter table users add column updated_by varchar(255) null;

alter table weekly_attendance drop column created_by;
alter table weekly_attendance add column created_by varchar(255) null;
alter table weekly_attendance drop column updated_by;
alter table weekly_attendance add column updated_by varchar(255) null;

alter table weekly_grade_attendance drop column created_by;
alter table weekly_grade_attendance add column created_by varchar(255) null;
alter table weekly_grade_attendance drop column updated_by;
alter table weekly_grade_attendance add column updated_by varchar(255) null;

alter table weekly_leader_attendance drop column created_by;
alter table weekly_leader_attendance add column created_by varchar(255) null;
alter table weekly_leader_attendance drop column updated_by;
alter table weekly_leader_attendance add column updated_by varchar(255) null;

alter table weekly_manager_attendance drop column created_by;
alter table weekly_manager_attendance add column created_by varchar(255) null;
alter table weekly_manager_attendance drop column updated_by;
alter table weekly_manager_attendance add column updated_by varchar(255) null;

alter table weekly_personal_attendance drop column created_by;
alter table weekly_personal_attendance add column created_by varchar(255) null;
alter table weekly_personal_attendance drop column updated_by;
alter table weekly_personal_attendance add column updated_by varchar(255) null;
