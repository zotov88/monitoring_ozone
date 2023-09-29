insert into ozone.roles(title)
values ('USER'),
       ('ADMIN');

update ozone.users
set tg_token = '5697984225:AAFoIt1WoiRi7w2AkQgYpn6VaWp_DBw9SPU',
               tg_chat_id = 613276132
where id = 3;