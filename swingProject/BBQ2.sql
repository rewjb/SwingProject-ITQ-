create table bodyorder(
num int auto_increment primary key,
id varchar(10)  not null,
name varchar(15) not null,
quantity int not null,
date TIMESTAMP DEFAULT NOW(),
hconfirm varchar(10),
bconfirm varchar(10)
);

create table bodysales(
num int auto_increment primary key,
id varchar(10) not null,
date TIMESTAMP DEFAULT NOW(),r
money int not null,
chickenF int,
chickenH int,
chickenS int,
side int
);

create table bodystock(
id varchar(10) not null,
name varchar(15) not null,
quantity int not null,
date TIMESTAMP DEFAULT NOW()
);

create table headstock(
point varchar(5) not null,
IO varchar(5) not null,
name varchar(15) not null,
quantity int not null,
place  varchar(15) not null,
date TIMESTAMP DEFAULT NOW()
);

create table headFranchise(
id varchar(10) primary key not null,
pw varchar(10) not null,
ownername varchar(10) not null,
tel varchar(15) not null,
comnum varchar(15) not null,
addr varchar(50) unique not null,
alias varchar(20) not null
);

create table headvender(
id varchar(10) primary key not null,
name varchar(10) unique not null,
tel varchar(15) not null,
comNum varchar(15) not null
);

create table headvenderp(
id varchar(10) not null,
num int auto_increment unique,
name varchar(10) not null,  
money int,
foreign key (id) references headvender(id) on delete cascade
);

create table headOrder(
vendername varchar(10) not null,
num int auto_increment unique,
name varchar(10) not null,  
quantity int,
money int,
date TIMESTAMP DEFAULT NOW(),
confirm varchar(10)
);

