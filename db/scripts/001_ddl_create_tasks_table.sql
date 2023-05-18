create table tasks (
   id serial   primary key,
   name        text      not null,
   description text      not null,
   created     timestamp not null,
   done        boolean   not null
);