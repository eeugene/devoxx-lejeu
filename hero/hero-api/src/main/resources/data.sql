delete from USER_ROLE where user_id = 'eeugene@aneo.fr';
delete from HERO where email = 'eeugene@aneo.fr';
insert into HERO(email, firstname, lastname, nickname, PASSWORD) values('eeugene@aneo.fr', 'emmanuel','eugene','manu', '$2a$10$6TajU85/gVrGUm5fv5Z8beVF37rlENohyLk3BEpZJFi6Av9JNkw9O');
insert into USER_ROLE(USER_ID, ROLE) values('eeugene@aneo.fr', 'ADMIN');
insert into USER_ROLE(USER_ID, ROLE) values('eeugene@aneo.fr', 'PLAYER');
