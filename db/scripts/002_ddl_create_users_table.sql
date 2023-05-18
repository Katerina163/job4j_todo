create table users (
	id       serial    primary key,
	name     text         not null,
	login    text unique  not null,
	password text         not null
)