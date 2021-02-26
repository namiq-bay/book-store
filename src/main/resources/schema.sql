DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

create table books
(
    id        varchar(255) not null,
    book_name varchar(255),
    username  varchar(255),
    author  varchar(255),
    primary key (id)
);

create table users
(
    username varchar(255) not null,
    enabled  boolean      not null,
    password varchar(255),
    primary key (username)
);

create table authorities
(
    username  varchar(255),
    authority varchar(255)
);

alter table books
    add constraint FK2e3nba5q71thmdvjtiaauu23i foreign key (username) references users;
