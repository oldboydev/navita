---------------------------
-- creating navita_users table
----------------------------
create table navita_users(
  n_id_user int not null,
  s_name varchar(200) not null,
  s_email varchar(100) not null,
  s_cpf varchar(11) not null,
  s_password varchar(100) not null,
  t_updated_at timestamp,
  t_created_at timestamp
);
-- add primary key;
alter table navita_users add constraint pk_id_user unique (n_id_user);

-- unique key
alter table navita_users add constraint uk_cpf unique (s_cpf);
alter table navita_users add constraint uk_email unique (s_email);
-- creating sequence;
create sequence seq_id_user increment 1 minvalue 1 maxvalue 9223372036854775807 start 1 cache 1;

insert into navita_users(n_id_user, s_name, s_email, s_cpf, s_password, t_updated_at) values (nextval('seq_id_user'), 'ADMIN', 'admin@navita.com.br', '99999999999', 'OYXXA9dtM4niiInjEGOxIQ==', current_timestamp, current_timestamp);