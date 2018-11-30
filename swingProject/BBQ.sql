create database BBQ;

<<<<<<< HEAD
유통기한..고민..

=가맹점 발주내역=
create table bodyorder(
num int auto_increment primary key,
id varchar(10)  not null,
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
id varchar(10) not null ,
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
create table headFranchise(
id varchar(10) primary key not null,
pw varchar(10) not null,
ownername varchar(10) not null,
tel varchar(15) not null,
comnum varchar(15) not null,
addr varchar(50) unique not null,
alias varchar(20) not null
);
-----------상단은 이미 함-----------
=업체 관리=
create table headvender(
id varchar(10) primary key not null,
name varchar(10) not null,
comnum varchar(15) not null,  이거 중복안되게 해야하는거 아니냐 ..
tel varchar(15) not null
);
-----------상단은 이미 함-----------
=제품 관리=
create table headvenderp(
id varchar(10) not null,
num int auto_increment unique,
name varchar(10) not null,  
money int,
foreign key (id) references headvender(id) on delete cascade
);

=발주 관리=
create table headOrder(
id varchar(10) not null,
num int auto_increment unique,
name varchar(10) not null,  
money int,
date TIMESTAMP DEFAULT NOW(),
confirm varchar(10)
);



------------------------------구분선---------------------------------

foreign key (A) references ????(B) on delete cascade


------------------------------구분선---------------------------------
desc bodyorder;
------컬럼 설정보기--------
show tables;
----- 전체 테이블 보기--------
drop table headvenderp;
drop table headvender;
drop table headFranchise;
drop table bodyorder;
drop table bodysales;
drop table bodystock;

show tables;
----- 전체 테이블 삭제--------
select * from bodyorder;
select * from headvender;
select * from headvenderp;
//거래업체확인 sql문
select * from headFranchise;
select * from bodyorder ORDER BY DATE DESC;
날짜로 솔트~
select name from bodyorder where date like '%2018-11-28%';
특정 날짜로 검색하기!!!
SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) and (hconfirm='') ORDER BY DATE DESC;
위에는 가맹점의 발주 중에 확인안한 리스트 갖고오기! / 담당자 : 유주빈
SELECT alias,tel FROM headFranchise ORDER BY alias;
SELECT * FROM headFranchise ORDER BY alias;
SELECT * from bodyorder WHERE num=10;
위에는 가맹점의 이름과 연락처 갖고오기! / 담당자 : 유주빈

;


update bodyorder set hconfirm = 'ck_1' where num=10;
update 테이블명 set 컬럼명 = '변경값' where 컬럼명='해당값'
----- 테이블 선택--------
drop table bodyorder;

create table bodyorder(
num int auto_increment primary key,
id varchar(10)  not null,
name varchar(15) not null,
quantity int not null,
date TIMESTAMP DEFAULT NOW(),
hconfirm varchar(10),
bconfirm varchar(10)
);

insert into bodyorder values (default,'daum','d-1',1,default,'1','');
insert into bodyorder values (default,'daum','d-2',12,default,'','');
insert into bodyorder values (default,'daum','d-1',13,default,'','');
insert into bodyorder values (default,'daum','d-1',14,default,'','');
insert into bodyorder values (default,'daum','d-2',15,default,'','');
insert into bodyorder values (default,'naver','n-1',16,default,'','');
insert into bodyorder values (default,'naver','n-1',17,default,'','');
insert into bodyorder values (default,'naver','n-1',18,default,'','');
insert into bodyorder values (default,'naver','n-2',19,default,'','');
insert into bodyorder values (default,'naver','n-2',20,default,'','');
insert into bodyorder values (default,'google','g-1',21,default,'','');
insert into bodyorder values (default,'google','g-1',22,default,'','');
insert into bodyorder values (default,'google','g-1',23,default,'','');
insert into bodyorder values (default,'google','g-2',24,default,'','');
insert into bodyorder values (default,'google','g-2',25,default,'','');
insert into bodyorder values (고유번호,아이디,재료명,수량,발주일,본사확인,가맹점 수령시 확인);
//가맹점의 발주기록을 넣는 sql문

insert into headFranchise values ('daum','123','AAA','010-xxxx-xxxx','408-81-123123','Seoul Geumcheon','bbq-d');
insert into headFranchise values ('naver','123','BBB','010-xxxx-xxxx','408-81-456456','Seoul Geumcheon-1','bbq-n');
insert into headFranchise values ('google','123','CCC','010-xxxx-xxxx','408-81-789789','Seoul Geumcheon-2','bbq-g');
insert into headFranchise values (아이디,비밀번호,가맹점 대표,전화번호,사업자번호,주소,가맹점 이름);
//가맹점의 등록 테이블에 넣는 sql문





insert into headvenderp values ('com-AAA',default,'melon',3000);
insert into headvenderp values ('com-AAA',default,'apple',700);
insert into headvenderp values ('com-BBB',default,'orange',1500);
insert into headvenderp values ('com-BBB',default,'banana',1000);
insert into headvenderp values (아이디(업체 아이디 외부키),고유번호(자동값),제품명,가격);
//업체의 제품등록 테이블


insert into headvender values ('com-BBB','whrhkdwo','002','02-xxx-xxx');
insert into headvender values ('com-AAA','dbwnqls','001','02-xxx-xxx');
insert into headvender values (아이디,회사명,사업자 번호,전화번호);
//업체 등록 테이블

create table headvenderp(
id varchar(10) not null,
num int auto_increment unique,
name varchar(10) not null,
money int,
foreign key (id) references headvender(id) on delete cascade
);

create table headvender(
id varchar(10) primary key not null,
name varchar(10) not null,
comnum varchar(15) not null,  
tel varchar(15) not null
);




insert into bodyorder values (자동입력 수,품명,수량,자동날짜,보점 확인,가맹점 확인);

