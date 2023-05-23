insert into priorities (name, position) values ('urgently', 1);
insert into priorities (name, position) values ('normal', 2);

alter table tasks add column priority_id int references priorities(id);

update tasks set priority_id = (select id from priorities where name = 'urgently');