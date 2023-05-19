create table tasks (
   id          serial    primary key,
   name        varchar   not null check (name <> ''),
   description text      not null,
   created     timestamp not null,
   done        boolean   not null
);