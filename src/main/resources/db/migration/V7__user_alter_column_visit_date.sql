update users set visit_date = '1970-01-01' where visit_date is null;

alter table users modify column visit_date date not null;