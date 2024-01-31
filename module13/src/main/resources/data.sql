insert into users(id, name, age)
values (1,'Maksim', 29),
       (2,'Arseniy', 5),
       (3,'Kira', 2),
       (4,'Alena', 26),
       (5,'admin', 29),
       (6,'user', 30);

insert into roles (name)
values ('ROLE_ADMIN');
insert into roles (name)
values ('ROLE_USER');

insert into auth (username, password)
values ('admin', '$2a$10$n2i96nBl4HTOx7p2dVqsCOvkFj1CliVJUfX.zz1uEHQfVUEawgZW6');
insert into auth_roles (auth_id, role_id)
select (select id from auth where username = 'admin'), (SELECT id FROM roles WHERE name = 'ROLE_ADMIN');

insert into auth (username, password)
values ('user', '$2a$10$n2i96nBl4HTOx7p2dVqsCOvkFj1CliVJUfX.zz1uEHQfVUEawgZW6');
insert into auth_roles (auth_id, role_id)
select (select id from auth where username = 'user'), (SELECT id FROM roles WHERE name = 'ROLE_USER');