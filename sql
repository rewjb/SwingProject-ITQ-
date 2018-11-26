
create table member
(
 name varchar(10),
 port int
);

----------------------------------------------------------
create table test4
(
 refid varchar(20),
 foreign key (refid) references test3(id) on delete cascade
);
----------------------------------------------------------------

insert into member values ('100',30000);

desc member;

drop table member;fgrnfnhfsndhfnhfshnnhsfsdf