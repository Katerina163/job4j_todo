create table users (
	id       serial          primary key,
	name     varchar         not null check (name <> ''),
	login    varchar unique  not null check (login <> ''),
	password varchar         not null check (password <> '')
);