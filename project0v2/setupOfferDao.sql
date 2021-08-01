drop table if exists offers;
drop table if exists items;
drop table if exists users;

create table if not exists users(

	user_id SERIAL primary key,
	user_user VARCHAR(20) not null unique,
	user_pass VARCHAR(20) not null check (length(user_pass) > 5),
	user_role VARCHAR(10)

);


insert into users (user_user, user_pass, user_role) values ('david', 'helloworld', 'customer');
insert into users (user_user, user_pass, user_role) values ('test', 'helloworld', 'customer');


create table if not exists items(

	item_id SERIAL primary key,
	item_name VARCHAR(20),
	item_seller INTEGER references users(user_id),
	item_owner INTEGER references users(user_id),
	item_value NUMERIC(5,2),
	item_remaining_value NUMERIC(5,2)

);



insert into items (item_name, item_seller, item_owner, item_value, item_remaining_value) values ('thing', 1, 1, 10, 10);
insert into items (item_name, item_seller, item_owner, item_value, item_remaining_value) values ('thing', 1, 1, 10, 10);
insert into items (item_name, item_seller, item_owner, item_value, item_remaining_value) values ('thing', 1, 1, 10, 10);
insert into items (item_name, item_seller, item_owner, item_value, item_remaining_value) values ('thing', 1, 2, 10, 10);


create table if not exists offers(

	offer_id SERIAL primary key,
	offer_amount NUMERIC(5,2),
	offer_on INTEGER references items(item_id) on delete cascade,
	offer_from INTEGER references users(user_id)

);

insert into offers (offer_amount, offer_on, offer_from) values (20, 1, 1);
insert into offers (offer_amount, offer_on, offer_from) values (20, 1, 1);
insert into offers (offer_amount, offer_on, offer_from) values (20, 2, 1);