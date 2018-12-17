<<<<<<< HEAD
create database BBQ;

유통기한..고민..

select * from bodysales where id = 'ror' and date like '% 2018-0-0 %' order by date desc;


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
id varchar(10) not null,
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
id varchar(10) not null,
name varchar(15) not null,
quantity int not null,
date TIMESTAMP DEFAULT NOW()
);
-----------상단은 이미 함-----------
=본사 재고관리=
create table headstock(
point varchar(5) not null,
IO varchar(5) not null,
name varchar(15) not null,
quantity int not null,
place  varchar(15) not null,
date TIMESTAMP DEFAULT NOW()
);
drop table headstock;
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
name varchar(10) unique not null,
tel varchar(15) not null,
comNum varchar(15) not null
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
vendername varchar(10) not null,
num int auto_increment unique,
name varchar(10) not null,  
quantity int,
money int,
date TIMESTAMP DEFAULT NOW(),
confirm varchar(10)
);

drop database bbq;
------------------------------구분선---------------------------------

foreign key (A) references ????(B) on delete cascade

------------------------------구분선---------------------------------
desc bodyorder;
desc headstock;
------컬럼 설정보기--------
show tables;
----- 전체 테이블 보기--------
drop table headvenderp;
drop table headvender;
drop table headFranchise;
drop table bodyorder;
drop table bodysales;
drop table bodystock;
drop table headOrder;
drop table headstock;

----- 전체 테이블 삭제--------
select * from bodyorder;
select * from bodystock;
select * from bodysales;
select * from bodysales  order by date desc; 
특정 날짜 + 날짜를 내림차순으로 전체 가져오기
SELECT id,name FROM headvender;
select * from headvender;
select * from headvenderp;
select * from  headOrder;

select * from  headstock;
SELECT * from bodyorder where id = 'AAA' order by hconfirm = '';


select * from  headstock ORDER BY date;
select name,SUM(quantity) as quantity  from headstock  where point=16  group by name;

select * from headvenderp;
SELECT DISTINCT name FROM headvenderp ORDER BY name;
select * from 
//거래업체확인 sql문
select * from headFranchise;
select * from bodyorder ORDER BY DATE DESC;
날짜로 솔트~
select name,date from bodyorder where date like '%2018-12-05%' or date like '%2018-12-06%';
특정 날짜로 검색하기!!!
SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) and (hconfirm='') ORDER BY DATE DESC;
위에는 가맹점의 발주 중에 확인안한 리스트 갖고오기! / 담당자 : 유주빈
SELECT headvenderp.num,headvenderp.name,headvender.name,headvenderp.money FROM headvenderp,headvender WHERE (headvender.id=headvenderp.id) ORDER BY headvender.name;
SELECT alias,tel FROM headFranchise ORDER BY alias;
SELECT * FROM headFranchise ORDER BY alias;
SELECT * from bodyorder WHERE num=10;
SELECT * FROM headorder ORDER BY  headorder.date DESC;
위에는 가맹점의 이름과 연락처 갖고오기! / 담당자 : 유주빈
select id,name,sum(quantity) as quantity from bodystock where id ='조광재' group by name ;
;
SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY DATE DESC;


SELECT name,SUM(quan) FROM headorder WHERE date LIKE '%2018-12%' GROUP BY name;
SELECT name,SUM(quantity) FROM headorder WHERE date LIKE '%2018-12%' GROUP BY name;
update bodyorder set hconfirm = 'ck_1' where num=10;
update 테이블명 set 컬럼명 = '변경값' where 컬럼명='해당값'
----- 테이블 선택--------
drop table bodyorder;

create table bodyorder(
num int auto_increment primary key,
id varchar(10)  not null,
name varchar(15) not null,
quantity int not null,
totalmoney int not null,
date TIMESTAMP DEFAULT NOW(),
hconfirm varchar(10),
bconfirm varchar(10)
);

create table bodysales(
num int auto_increment primary key,
id varchar(10) not null,
date TIMESTAMP DEFAULT NOW(),
money int not null,
chickenF int,
chickenH int,
chickenS int,
side int
);

select * from s

insert into bodysales values (default,'조광재','2018-11-01','60000','20000','20000','20000','0');

insert into bodysales values (default,'조광재','2018-11-01','60000','20000','20000','20000','0');
insert into bodysales values (default,'조광재','2018-10-01','60000','20000','20000','20000','0');
insert into bodysales values (default,'조광재','2018-09-01','60000','20000','20000','20000','0');
insert into bodysales values (default,'조광재','2018-08-01','60000','20000','20000','20000','0');
insert into bodysales values (default,'조광재','2018-07-01','60000','20000','20000','20000','0');
insert into bodysales values (default,'조광재','2018-06-01','60000','20000','20000','20000','0');
insert into bodysales values (default,'조광재','2018-05-01','60000','20000','20000','20000','0');
insert into bodysales values (default,'조광재','2018-04-01','60000','20000','20000','20000','0');
insert into bodysales values (default,'조광재','2018-03-01','60000','20000','20000','20000','0');
insert into bodysales values (default,'조광재','2018-02-01','60000','20000','20000','20000','0');
insert into bodysales values (default,'조광재','2018-01-01','60000','20000','20000','20000','0');


insert into bodyorder values (default,'닭','닭',10,default,'1','');
insert into bodyorder values (default,'콜라','d-1',1,default,'1','');
insert into bodyorder values (default,'사이다','d-1',1,default,'1','');
insert into bodyorder values (default,'','d-1',1,default,'1','');
insert into bodyorder values (default,'콜라','d-1',1,default,'1','');
insert into bodyorder values (default,'콜라','d-1',1,default,'1','');
insert into bodyorder values (default,'daum','d-1',1,default,'1','');
insert into bodyorder values (default,'daum','d-2',12,default,'1','');
insert into bodyorder values (default,'daum','d-1',13,default,'1','');
insert into bodyorder values (default,'daum','d-1',14,default,'1','');
insert into bodyorder values (default,'daum','d-2',15,default,'1','');
insert into bodyorder values (default,'naver','n-1',16,default,'1','');
insert into bodyorder values (default,'naver','n-1',17,default,'1','');
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

insert into headFranchise values ('root','123','AAA','010-xxxx-xxxx','408-81-123123','Seoul Geumcheon','bbq-d');
insert into headFranchise values ('naver','123','BBB','010-xxxx-xxxx','408-81-456456','Seoul Geumcheon-1','bbq-n');
insert into headFranchise values ('google','123','CCC','010-xxxx-xxxx','408-81-789789','Seoul Geumcheon-2','bbq-g');
insert into headFranchise values (아이디,비밀번호,가맹점 대표,전화번호,사업자번호,주소,가맹점 이름);
//가맹점의 등록 테이블에 넣는 sql문


insert into headvenderp values ('com-AAA',default,'melon',3000);
insert into headvenderp values ('com-AAA',default,'apple',700);
insert into headvenderp values ('com-BBB',default,'apple',1500);
insert into headvenderp values ('com-BBB',default,'orange',1500);
insert into headvenderp values ('com-BBB',default,'banana',1000);
insert into headvenderp values (아이디(업체 아이디 외부키),고유번호(자동값),제품명,가격);
//업체의 제품등록 테이블


insert into headvender values ('com-BBB','whrhkdwo','002','02-xxx-xxx');
insert into headvender values ('com-AAA','dbwnqls','001','02-xxx-xxx');
insert into headvender values (아이디,회사명,사업자 번호,전화번호);
//업체 등록 테이블

SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY bodyorder.hconfirm ASC, bodyorder.DATE  DESC;

SELECT  *  from  bodyorder;
                                 1     2      3  4  5     6     7
insert into headorder values (업체명,default,발주품명,발주수량,가격,default,'');

select id, name,quantity, sum(quantity) from bodystock group by name;//특정 조건에 따른 값 더하기
name에 따른 quantity값을 더하는데 from(bodystock테이블로 부터) group by(name이라는 그룹으로 묶는다.)


select * from bodysales where date like '%%';

SELECT * FROM headorder ORDER BY  headorder.date, headorder.confirm DESC;

SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY  bodyorder.hconfirm, bodyorder.DATE  DESC ;

SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY  bodyorder.hconfirm DESC, bodyorder.DATE   ;
SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY  bodyorder.hconfirm, bodyorder.DATE  DESC ;
SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY  bodyorder.DATE  DESC,bodyorder.hconfirm DESC  ;
SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY bodyorder.hconfirm ASC, bodyorder.DATE  DESC  ;
UPDATE bodyorder SET hconfirm = 'ck_1' WHERE num= 41 ;
insert into bodyorder values (자동입력 수,품명,수량,자동날짜,보점 확인,가맹점 확인);

SELECT * FROM headorder ORDER BY  headorder.confirm , headorder.date DESC;

SELECT *  FROM headstock where IO='in' ORDER BY date DESC;
=본사 재고관리=
insert into headstock values ('10','in','apple',10,"asdasd",default);
insert into headstock values ('11','in','apple',11,"asdasddsd",default);
insert into headstock values ('20','out','apple',20,"sadasd",default);
insert into headstock values ('21','out','apple',21,"asdasd122",default);
select * from headstock;

SELECT headvenderp.name,headvender.name FROM headvenderp,headvender WHERE (headvender.id=headvenderp.id) and headvenderp.name='apple'  ORDER BY headvender.name;




create database BBQ;

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
id varchar(10) not null,
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
id varchar(10) not null,
name varchar(15) not null,
quantity int not null,
date TIMESTAMP DEFAULT NOW()
);
-----------상단은 이미 함-----------
=본사 재고관리=
create table headstock(
point varchar(5) not null,
IO varchar(5) not null,
name varchar(15) not null,
quantity int not null,
place  varchar(15) not null,
date TIMESTAMP DEFAULT NOW()
);
drop table headstock;
-----------상단은 이미 함-----------
=가맹점 관리=
create table headFranchise(
id varchar(10) primary key not null,
pw varchar(10) not null,
ownername varchar(10) not null,
tel varchar(15) not null,
comnum varchar(15) not null,
addr varchar(50) not null,
alias varchar(20) not null
);
-----------상단은 이미 함-----------
=업체 관리=
create table headvender(
id varchar(10) primary key not null,
name varchar(10) unique not null,
tel varchar(15) not null,
comNum varchar(15) not null
);
-----------상단은 이미 함-----------
=제품 관리=
create table headvenderp(
id varchar(10) not null,
num int auto_increment unique,
name varchar(10) not null,  
money int,
이익률 
foreign key (id) references headvender(id) on delete cascade
);

=발주 관리=
create table headOrder(
vendername varchar(10) not null,
num int auto_increment unique,
name varchar(10) not null,  
quantity int,
money int,
date TIMESTAMP DEFAULT NOW(),
confirm varchar(10)
);


------------------------------구분선---------------------------------

foreign key (A) references ????(B) on delete cascade


------------------------------구분선---------------------------------
desc bodyorder;
desc headstock;
------컬럼 설정보기--------
show tables;
----- 전체 테이블 보기--------
drop table headvenderp;
drop table headvender;
drop table headFranchise;
drop table bodyorder;
drop table bodysales;
drop table bodystock;
drop table headOrder;
drop table headstock;

show tables;
----- 전체 테이블 삭제--------
select * from bodyorder;
select * from bodystock;
select * from bodysales  order by date desc; 
특정 날짜 + 날짜를 내림차순으로 전체 가져오기
SELECT id,name FROM headvender;
select * from headvender;
select * from  headOrder;

select * from  headstock;

select * from  headstock ORDER BY date;
select name,SUM(quantity) as quantity  from headstock  where point=16  group by name;

select * from headvenderp;
SELECT DISTINCT name FROM headvenderp ORDER BY name;
select * from 
//거래업체확인 sql문
select * from headFranchise;
select * from bodyorder ORDER BY DATE DESC;
날짜로 솔트~
select name from bodyorder where date like '%2018-11-28%';
특정 날짜로 검색하기!!!
SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) and (hconfirm='') ORDER BY DATE DESC;
위에는 가맹점의 발주 중에 확인안한 리스트 갖고오기! / 담당자 : 유주빈
SELECT headvenderp.num,headvenderp.name,headvender.name,headvenderp.money FROM headvenderp,headvender WHERE (headvender.id=headvenderp.id) ORDER BY headvender.name;
SELECT alias,tel FROM headFranchise ORDER BY alias;
SELECT * FROM headFranchise ORDER BY alias;
SELECT * from bodyorder WHERE num=10;
SELECT * FROM headorder ORDER BY  headorder.date DESC;
위에는 가맹점의 이름과 연락처 갖고오기! / 담당자 : 유주빈

;
SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY DATE DESC;

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
insert into bodyorder values (default,'daum','d-2',12,default,'1','');
insert into bodyorder values (default,'daum','d-1',13,default,'1','');
insert into bodyorder values (default,'daum','d-1',14,default,'1','');
insert into bodyorder values (default,'daum','d-2',15,default,'1','');
insert into bodyorder values (default,'naver','n-1',16,default,'1','');
insert into bodyorder values (default,'naver','n-1',17,default,'1','');
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
insert into headvenderp values ('com-BBB',default,'apple',1500);
insert into headvenderp values ('com-BBB',default,'orange',1500);
insert into headvenderp values ('com-BBB',default,'banana',1000);
insert into headvenderp values (아이디(업체 아이디 외부키),고유번호(자동값),제품명,가격);
//업체의 제품등록 테이블


insert into headvender values ('com-BBB','whrhkdwo','002','02-xxx-xxx');
insert into headvender values ('com-AAA','dbwnqls','001','02-xxx-xxx');
insert into headvender values (아이디,회사명,사업자 번호,전화번호);
//업체 등록 테이블

                                 1     2      3  4  5     6     7
insert into headorder values (업체명,default,발주품명,발주수량,가격,default,'');

select id, name,quantity, sum(quantity) from bodystock group by name;//특정 조건에 따른 값 더하기
name에 따른 quantity값을 더하는데 from(bodystock테이블로 부터) group by(name이라는 그룹으로 묶는다.)


select * from bodysales where date like '%%';

SELECT * FROM headorder ORDER BY  headorder.date, headorder.confirm DESC;

SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY  bodyorder.hconfirm, bodyorder.DATE  DESC ;

SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY  bodyorder.hconfirm DESC, bodyorder.DATE   ;
SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY  bodyorder.hconfirm, bodyorder.DATE  DESC ;
SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY  bodyorder.DATE  DESC,bodyorder.hconfirm DESC  ;
SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY bodyorder.hconfirm ASC, bodyorder.DATE  DESC  ;
UPDATE bodyorder SET hconfirm = 'ck_1' WHERE num= 41 ;
insert into bodyorder values (자동입력 수,품명,수량,자동날짜,보점 확인,가맹점 확인);

SELECT * FROM headorder ORDER BY  headorder.confirm , headorder.date DESC;

SELECT *  FROM headstock where IO='in' ORDER BY date DESC;
=본사 재고관리=
insert into headstock values ('10','in','apple',10,"asdasd",default);
insert into headstock values ('11','in','apple',11,"asdasddsd",default);
insert into headstock values ('20','out','apple',20,"sadasd",default);
insert into headstock values ('21','out','apple',21,"asdasd122",default);
select * from headstock;

SELECT headvenderp.name,headvender.name FROM headvenderp,headvender WHERE (headvender.id=headvenderp.id) and headvenderp.name='apple'  ORDER BY headvender.name;

=======
create database BBQ;

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
id varchar(10) not null,
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
id varchar(10) not null,
name varchar(15) not null,
quantity int not null,
date TIMESTAMP DEFAULT NOW()
);
-----------상단은 이미 함-----------
=본사 재고관리=
create table headstock(
point varchar(5) not null,
IO varchar(5) not null,
name varchar(15) not null,
quantity int not null,
place  varchar(15) not null,
date TIMESTAMP DEFAULT NOW()
);
drop table headstock;
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
name varchar(10) unique not null,
tel varchar(15) not null,
comNum varchar(15) not null
);
-----------상단은 이미 함-----------
=제품 관리=
create table headvenderp(
id varchar(10) not null,
num int auto_increment unique,
name varchar(10) not null,  
money int,
이익률 
foreign key (id) references headvender(id) on delete cascade
);

=발주 관리=
create table headOrder(
vendername varchar(10) not null,
num int auto_increment unique,
name varchar(10) not null,  
quantity int,
money int,
date TIMESTAMP DEFAULT NOW(),
confirm varchar(10)
);


------------------------------구분선---------------------------------

foreign key (A) references ????(B) on delete cascade


------------------------------구분선---------------------------------
desc bodyorder;
desc headstock;
------컬럼 설정보기--------
show tables;
----- 전체 테이블 보기--------
drop table headvenderp;
drop table headvender;
drop table headFranchise;
drop table bodyorder;
drop table bodysales;
drop table bodystock;
drop table headOrder;
drop table headstock;

show tables;
----- 전체 테이블 삭제--------
select * from bodyorder;
select * from bodystock;
select * from bodysales  order by date desc; 
특정 날짜 + 날짜를 내림차순으로 전체 가져오기
SELECT id,name FROM headvender;
select * from headvender;
select * from  headOrder;

select * from  headstock;

select * from  headstock ORDER BY date;
select name,SUM(quantity) as quantity  from headstock  where point=16  group by name;

select * from headvenderp;
SELECT DISTINCT name FROM headvenderp ORDER BY name;
select * from 
//거래업체확인 sql문
select * from headFranchise;
select * from bodyorder ORDER BY DATE DESC;
날짜로 솔트~
select name from bodyorder where date like '%2018-11-28%';
특정 날짜로 검색하기!!!
SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) and (hconfirm='') ORDER BY DATE DESC;
위에는 가맹점의 발주 중에 확인안한 리스트 갖고오기! / 담당자 : 유주빈
SELECT headvenderp.num,headvenderp.name,headvender.name,headvenderp.money FROM headvenderp,headvender WHERE (headvender.id=headvenderp.id) ORDER BY headvender.name;
SELECT alias,tel FROM headFranchise ORDER BY alias;
SELECT * FROM headFranchise ORDER BY alias;
SELECT * from bodyorder WHERE num=10;
SELECT * FROM headorder ORDER BY  headorder.date DESC;
위에는 가맹점의 이름과 연락처 갖고오기! / 담당자 : 유주빈

;
SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY DATE DESC;

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

insert into bodyorder values (default,'닭','d-1',1,default,'ck_1','');
insert into bodyorder values (default,'콜라','d-1',1,default,'ck_1','');
insert into bodyorder values (default,'사이다','d-1',1,default,'ck_1','');
insert into bodyorder values (default,'','d-1',1,default,'1','');
insert into bodyorder values (default,'콜라','d-1',1,default,'ck_1','');
insert into bodyorder values (default,'콜라','d-1',1,default,'1','');
insert into bodyorder values (default,'daum','d-1',1,default,'1','');
insert into bodyorder values (default,'daum','d-2',12,default,'1','');
insert into bodyorder values (default,'daum','d-1',13,default,'1','');
insert into bodyorder values (default,'daum','d-1',14,default,'1','');
insert into bodyorder values (default,'daum','d-2',15,default,'1','');
insert into bodyorder values (default,'naver','n-1',16,default,'1','');
insert into bodyorder values (default,'naver','n-1',17,default,'1','');
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
insert into headvenderp values ('com-BBB',default,'apple',1500);
insert into headvenderp values ('com-BBB',default,'orange',1500);
insert into headvenderp values ('com-BBB',default,'banana',1000);
insert into headvenderp values (아이디(업체 아이디 외부키),고유번호(자동값),제품명,가격);
//업체의 제품등록 테이블


insert into headvender values ('com-BBB','whrhkdwo','002','02-xxx-xxx');
insert into headvender values ('com-AAA','dbwnqls','001','02-xxx-xxx');
insert into headvender values (아이디,회사명,사업자 번호,전화번호);
//업체 등록 테이블

                                 1     2      3  4  5     6     7
insert into headorder values (업체명,default,발주품명,발주수량,가격,default,'');

select id, name,quantity, sum(quantity) from bodystock group by name;//특정 조건에 따른 값 더하기
name에 따른 quantity값을 더하는데 from(bodystock테이블로 부터) group by(name이라는 그룹으로 묶는다.)


select * from bodysales where date like '%%';

SELECT * FROM headorder ORDER BY  headorder.date, headorder.confirm DESC;

SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY  bodyorder.hconfirm, bodyorder.DATE  DESC ;

SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY  bodyorder.hconfirm DESC, bodyorder.DATE   ;
SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY  bodyorder.hconfirm, bodyorder.DATE  DESC ;
SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY  bodyorder.DATE  DESC,bodyorder.hconfirm DESC  ;
SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY bodyorder.hconfirm ASC, bodyorder.DATE  DESC  ;
UPDATE bodyorder SET hconfirm = 'ck_1' WHERE num= 41 ;
insert into bodyorder values (자동입력 수,품명,수량,자동날짜,보점 확인,가맹점 확인);

SELECT * FROM headorder ORDER BY  headorder.confirm , headorder.date DESC;

SELECT *  FROM headstock where IO='in' ORDER BY date DESC;
=본사 재고관리=
insert into headstock values ('10','in','apple',10,"asdasd",default);
insert into headstock values ('11','in','apple',11,"asdasddsd",default);
insert into headstock values ('20','out','apple',20,"sadasd",default);
insert into headstock values ('21','out','apple',21,"asdasd122",default);
select * from headstock;

SELECT headvenderp.name,headvender.name FROM headvenderp,headvender WHERE (headvender.id=headvenderp.id) and headvenderp.name='apple'  ORDER BY headvender.name;

insert into headFranchise values ('root','root','DDD','010-xxxx-xxxx','3','2','4');
insert into headFranchise values ('daum','123','AAA','010-xxxx-xxxx','408-81-123123','Seoul Geumcheon','bbq-d');
insert into headFranchise values ('naver','123','BBB','010-xxxx-xxxx','408-81-456456','Seoul Geumcheon-1','bbq-n');
insert into headFranchise values ('google','123','CCC','010-xxxx-xxxx','408-81-789789','Seoul Geumcheon-2','bbq-g');
insert into headFranchise values (아이디,비밀번호,가맹점 대표,전화번호,사업자번호,주소,가맹점 이름);


insert into bodysales values (default,'daum',default,1,20000,6000,0,0);
insert into bodysales values (default,'daum','2018-12-06 10:11',1,1,1,1,1);
insert into bodysales values (default,'daum','2018-11-06 10:11',1,1,1,1,1);
insert into bodysales values (default,'daum','2018-10-06 10:11',1,1,1,1,1);
insert into bodysales values (default,'daum','2018-09-06 10:11',1,1,1,1,1);
insert into bodysales values (default,'daum','2018-08-06 10:11',1,1,1,1,1);
insert into bodysales values (default,'daum','2018-07-06 10:11',1,1,1,1,1);
insert into bodysales values (default,'daum','2018-06-06 10:11',1,1,1,1,1);
insert into bodysales values (default,'daum','2018-05-06 10:11',1,1,1,1,1);
insert into bodysales values (default,'daum','2018-04-06 10:11',1,1,1,1,1);
insert into bodysales values (default,'daum','2018-03-06 10:11',1,1,1,1,1);
insert into bodysales values (default,'daum','2018-02-06 10:11',1,1,1,1,1);
insert into bodysales values (default,'daum','2018-01-06 10:11',1,1,1,1,1);
insert into bodysales values (default,'naver','2018-01-07 10:11',1,1,1,1,1);

insert into bodysales values (default,'naver','2018-01-01 10:11',1,1,1,1,1);
insert into bodysales values (default,'naver','2018-01-04 10:11',1,1,1,1,1);
insert into bodysales values (default,'naver','2018-01-0 10:11',1,1,1,1,1);
insert into bodysales values (default,'naver','2018-01-07 10:11',1,1,1,1,1);
insert into bodysales values (default,'naver','2018-01-07 10:11',1,1,1,1,1);
insert into bodysales values (default,'naver','2018-01-07 10:11',1,1,1,1,1);
insert into bodysales values (default,'naver','2018-01-07 10:11',1,1,1,1,1);
insert into bodysales values (default,'naver','2018-01-07 10:11',1,1,1,1,1);

select * from headorder;
insert into bodysales values (  자동값, 가맹아이디,날짜자동,총금액,후라이드,양념,간장,사이드);

select bodysales.id, headFranchise.alias, SUM(bodysales.money) from bodysales,headFranchise where and bodysales.id='daum'and date like '%2018-01%';
select bodysales.id,SUM(bodysales.money) from bodysales where bodysales.id='naver'and bodysales.date like '%2018-01%' GROUP BY bodysales.id;

--select bodysales.id from (select id from headFranchise where alias='bbq-n') as temp,bodysales where headFranchise.id=temp.id ;
--select  temp.id from (select id from headFranchise where alias='bbq-n') as temp ;
--select id from headFranchise where alias='bbq-n';

select id,SUM(money) from bodysales where id='naver' and date like '%2018-01%' GROUP BY id;
SELECT SUM(money) FROM headorder where date LIKE '%2018-11%';
SELECT *  FROM headorder;

SELECT id,SUM(chickenF) from bodysales where id='naver' GROUP BY id;
SELECT id,SUM(?) from bodysales where id=? GROUP BY id;
SELECT * from bodysales;

drop database bbq;
SELECT SUM(money) FROM bodysales WHERE date LIKE '%2018-12%';
select * from bodysales;

SELECT * FROM bodysales ;

drop table bodysales;
create table bodysales(
num int auto_increment primary key,
id varchar(10) not null,
date TIMESTAMP DEFAULT NOW(),
money int not null,
chickenF int,
chickenH int,
chickenS int,
side int
);
