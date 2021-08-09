drop table if exists ers_reimbursement;
drop table if exists ers_users;
drop table if exists ers_reimbursement_status;
drop table if exists ers_user_roles;
drop table if exists ers_reimbursement_type;

create table if not exists ers_reimbursement_status(

	reimb_status_id SERIAL not null primary key,
	reimb_status VARCHAR(10) not null

);

create table if not exists ers_reimbursement_type(

	reimb_type_id SERIAL not null primary key,
	reimb_type VARCHAR(10) not null

);

create table if not exists ers_user_roles(

	ers_user_role_id SERIAL not null primary key,
	user_role VARCHAR(10) not null

);


create table if not exists ers_users(

	ers_user_id SERIAL primary key not null,
	ers_username VARCHAR(50) unique not null,
	ers_password VARCHAR(50) not null,
	user_first_name VARCHAR(100) not null,
	user_last_name VARCHAR(100) not null,
	user_email VARCHAR(150) unique not null,
	user_role_id INTEGER not null references ers_user_roles(ers_user_role_id)

);

create table if not exists ers_reimbursement(

	reimb_id SERIAL primary key not null,
	reimb_amount NUMERIC(10,2) not null,
	reimb_submitted TIMESTAMP not null,
	reimb_resolved TIMESTAMP,
	reimb_description VARCHAR(250),
	reimb_receipt BYTEA,
	reimb_author INTEGER not null references ers_users(ers_user_id),
	reimb_resolver INTEGER references ers_users(ers_user_id),
	reimb_status_id INTEGER not null references ers_reimbursement_status(reimb_status_id),
	reimb_type_id INTEGER not null references ers_reimbursement_type(reimb_type_id)

);


insert into ers_reimbursement_type (reimb_type) values ('LODGING');
insert into ers_reimbursement_type (reimb_type) values ('TRAVEL');
insert into ers_reimbursement_type (reimb_type) values ('FOOD');
insert into ers_reimbursement_type (reimb_type) values ('OTHER');

insert into ers_user_roles (user_role) values ('manager');
insert into ers_user_roles (user_role) values ('employee');

insert into ers_reimbursement_status (reimb_status) values ('pending');
insert into ers_reimbursement_status (reimb_status) values ('resolved');

insert into ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
values ('username', 'password', 'first', 'last', 'user@email.com', 2);
insert into ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
values ('david', 'password', 'first', 'last', 'david@email.com', 1);

insert into ers_reimbursement (reimb_amount, reimb_submitted, reimb_author, reimb_status_id, reimb_type_id)
values (10, current_timestamp, 1, 1, 1);
insert into ers_reimbursement (reimb_amount, reimb_submitted, reimb_author, reimb_status_id, reimb_type_id)
values (20, current_timestamp, 1, 1, 1);