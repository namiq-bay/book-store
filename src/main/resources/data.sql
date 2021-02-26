insert into authorities(username, authority)
values ('user', 'ROLE_USER');

insert into authorities(username, authority)
values ('publisher', 'ROLE_USER');

insert into authorities(username, authority)
values ('publisher', 'ROLE_EDITOR');


insert into users
values ('publisher', true, '{noop}qfrxxxadygrlwtfz');
insert into users
values ('user', true, '{noop}iyrgyamflafupzkn');

insert into books (id, book_name, author, username)
values ('bc633c81-4f03-4dae-9c33-3935a1226f52', 'Spring Boot in Action', 'Craig Walls', 'publisher');

insert into books (id, book_name, author, username)
values ('1dda15e2-9eb9-4c2c-8b82-2e274c1403a7', 'Head First Java', 'Kathy Sierra & Bert Bates', 'publisher');

insert into books (id, book_name, author, username)
values ('ac3c6dd0-2ef3-4d47-b8ca-62f2e7210433', 'Spring Microservices in Action', 'John Carnell', 'publisher');

insert into books (id, book_name, author, username)
values ('65a8f5ec-a2f3-40f4-88af-f0ecc1c937ea', 'Algorithms', 'Thomas H. Cormen', 'publisher');