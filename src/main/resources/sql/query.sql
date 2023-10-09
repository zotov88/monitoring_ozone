insert into ozone.roles(title)
values ('USER'),
       ('ADMIN');

select price
from ozone.stories
where product_id = 28
and price <> 0
order by price
limit 1
