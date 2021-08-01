drop table if exists users;

create table if not exists users(

	user_id SERIAL primary key,
	user_user VARCHAR(20) not null unique,
	user_pass VARCHAR(20) not null check (length(user_pass) > 5),
	user_role VARCHAR(10)

);


insert into users (user_user, user_pass, user_role) values ('david', 'helloworld', 'customer');