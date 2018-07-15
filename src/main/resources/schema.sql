DROP table ACCOUNT IF EXISTS;

create table account
(
   id integer not null,
   customerId integer not null,
   accountType varchar(255) not null,
   message varchar(255) not null,
   DATE DATE DEFAULT CURRENT_DATE,
);