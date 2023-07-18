show variables like '%time_zone%';
create table datedemo
(
 mydatetime datetime,
 mytimestamp timestamp
);
insert into datedemo values (now(), now());
select * from datedemo;
SET TIME_ZONE = "america/new_york";
select * from datedemo;

