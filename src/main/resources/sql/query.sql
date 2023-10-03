insert into ozone.roles(title)
values ('USER'),
       ('ADMIN');

update ozone.users
set password = '$2a$12$B6A1PpCwbdN/C0D10y7sl.XjrqHBNHGJsfWSq2I9EISfUVZLPKRT6'
where id = 4;

