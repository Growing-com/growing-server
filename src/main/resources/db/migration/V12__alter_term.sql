alter table term add column status varchar(255) not null default 'PENDING';
alter table term add column memo varchar(255) null;
alter table term add column groupings json not null default ('{}');
alter table term add column previous_term_id bigint null;

create unique index uk_term_name on term (name);
