---------------------------
-- creating navita_parking_lot table
----------------------------
create table navita_parking_lot(
  n_id_parking_lot int not null,
  s_name varchar(200) not null,
  s_cnpj varchar(14) not null,
  s_address varchar(250) not null,
  s_phone varchar(11) not null,
  n_spaces_moto int not null,
  n_spaces_cars int not null,
  t_updated_at timestamp,
  t_created_at timestamp
);
-- add primary key;
alter table navita_parking_lot add constraint pk_id_parking_lot unique (n_id_parking_lot);
-- unique key
alter table navita_parking_lot add constraint uk_cnpj unique (s_cnpj);
-- creating sequence;
create sequence seq_id_parking_lot increment 1 minvalue 1 maxvalue 9223372036854775807 start 1 cache 1;
---------------------------
-- creating navita_vehicles table
----------------------------
create table navita_vehicles(
  n_id_vehicle int not null,
  s_brand varchar(50) not null,
  s_model varchar(50) not null,
  s_color varchar(50) not null,
  s_plate varchar(20) not null,
  n_type int not null,
  t_updated_at timestamp,
  t_created_at timestamp
);
-- add primary key;
alter table navita_vehicles add constraint pk_id_vehicle unique (n_id_vehicle);
-- unique key
alter table navita_vehicles add constraint uk_plate unique (s_plate);
-- creating sequence;
create sequence seq_id_vehicles increment 1 minvalue 1 maxvalue 9223372036854775807 start 1 cache 1;
---------------------------
-- creating navita_manager table
----------------------------
create table navita_manager(
  n_id_manager int not null,
  n_id_parking_lot int not null,
  n_spaces_moto int not null,
  n_spaces_cars int not null,
  n_spaces_moto_empty int not null,
  n_spaces_cars_empty int not null,
  t_updated_at timestamp,
  t_created_at timestamp
);
-- add primary key;
alter table navita_manager add constraint pk_id_manager unique (n_id_manager);
-- unique key
alter table navita_manager add constraint uk_manager unique (n_id_parking_lot);
-- creating sequence;
create sequence seq_id_manager increment 1 minvalue 1 maxvalue 9223372036854775807 start 1 cache 1;

---------------------------
-- creating navita_manager_vehicle table
----------------------------
create table navita_manager_vehicle(
  n_id_manager_vehicle int not null,
  n_id_parking_lot int not null,
  n_id_vehicle int not null,
  t_updated_at timestamp,
  t_created_at timestamp
);
-- add primary key;
alter table navita_manager_vehicle add constraint pk_id_manager_vehicle unique (n_id_manager_vehicle);
-- unique key
alter table navita_manager_vehicle add constraint uk_manager_vehicle unique (n_id_vehicle);
-- creating sequence;
create sequence seq_id_manager_vehicle increment 1 minvalue 1 maxvalue 9223372036854775807 start 1 cache 1;