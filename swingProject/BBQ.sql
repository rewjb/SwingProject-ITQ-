create database BBQ;

<<<<<<< HEAD
유통기한..고민..

=가맹점 발주내역=
create table bodyorder(
num int auto_increment primary key,
name varchar(15) not null,
quantity int not null,
date TIMESTAMP DEFAULT NOW(),
hconfirm varchar(10),
bconfirm varchar(10)
);
-----------상단은 이미 함-----------
=가맹점 매출내역=
create table bodysales(
num int auto_increment primary key,
id varchar(10) not null unique,
date TIMESTAMP DEFAULT NOW(),
money int not null,
chickenF int,
chickenH int,
chickenS int,
side int
);
-----------상단은 이미 함-----------
=가맹점 재고관리=
create table bodystock(
id varchar(10) not null unique,
name varchar(15) not null,
quantity int not null
);
-----------상단은 이미 함-----------
=가맹점 관리=
create table headmember(
id varchar(10) primary key not null,
pw varchar(10) not null,
ownername varchar(10) not null,
tel varchar(15) not null,
comnum varchar(15) not null,
addr varchar(50) unique not null,
alias varchar(20) unique not null
);
-----------상단은 이미 함-----------
=업체 관리=
create table headvender(
id varchar(10) primary key not null,
name varchar(10) not null,
comnum varchar(15) not null,
tel varchar(15) not null
);
-----------상단은 이미 함-----------
=제품 관리=
create table headvenderp(
id varchar(10) primary key not null,
num int auto_increment unique,
name varchar(10) not null,
venderid varchar(10) not null,
money int,
foreign key (id) references headvender(id) on delete cascade
);

foreign key (A) references ????(B) on delete cascade


------------------------------구분선---------------------------------
desc bodyorder;
------컬럼 설정보기--------
show tables;
----- 전체 테이블 보기--------
drop table bodyvenderp;
drop table headvender;
drop table headmember;
drop table bodyorder;
drop table bodysales;
drop table bodystock;


----- 전체 테이블 삭제--------
select * from bodyorder;
select * from bodyorder ORDER BY DATE DESC;
select name from bodyorder where date like '%2018-11-27%';
특정 날짜로 검색하기!!!
----- 테이블 선택--------

insert into bodyorder values (default,'a',10,default,'','');
insert into bodyorder values (자동입력 수,품명,수량,자동날짜,보점 확인,가맹점 확인);
