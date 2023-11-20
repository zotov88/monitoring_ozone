insert into ozone.roles(title)
values ('USER'),
       ('ADMIN');

insert into ozone.markets(title)
values ('ozone'),
       ('wildberries');

insert into  ozone.stories(date, price, product_id)
values ('2023-06-11', 777, 84);

delete from ozone.stories where date = '2023-06-11' and product_id = 84;

select * from ozone.stories where product_id = 84;


-- select *
-- from (select date, min(price)
--       from ozone.stories
--       where product_id = 84
--         and price <> 0
--       group by date
--       order by date desc
--       limit 20) as t
-- order by date;

select id, date, product_id, min(price)
from ozone.stories
where product_id = 84
  and price <> 0
group by id, date, product_id
order by date desc
limit 20;

select *
from ozone.stories
where product_id = 84;


