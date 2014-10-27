# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table address (
  id                        bigint not null,
  address1                  varchar(255),
  address2                  varchar(255),
  city                      varchar(255),
  state                     varchar(255),
  country_code              varchar(255),
  country_name              varchar(255),
  postal_code               varchar(255),
  user_email                varchar(255),
  constraint pk_address primary key (id))
;

create table category (
  id                        bigint not null,
  name                      varchar(255),
  parent_category_id        bigint,
  constraint pk_category primary key (id))
;

create table customer (
  id                        bigint not null,
  constraint pk_customer primary key (id))
;

create table product (
  id                        bigint not null,
  sku                       varchar(255),
  supplier_prod_id          varchar(255),
  product_name              varchar(255),
  product_description       varchar(255),
  supplier                  bigint,
  category_id               bigint,
  quantity_per_unit         bigint,
  constraint pk_product primary key (id))
;

create table user (
  email                     varchar(255) not null,
  password                  varchar(255),
  name                      varchar(255),
  phone                     varchar(255),
  is_admin                  boolean,
  constraint pk_user primary key (email))
;

create sequence address_seq;

create sequence category_seq;

create sequence customer_seq;

create sequence product_seq;

create sequence user_seq;

alter table address add constraint fk_address_user_1 foreign key (user_email) references user (email) on delete restrict on update restrict;
create index ix_address_user_1 on address (user_email);
alter table category add constraint fk_category_parentCategory_2 foreign key (parent_category_id) references category (id) on delete restrict on update restrict;
create index ix_category_parentCategory_2 on category (parent_category_id);
alter table product add constraint fk_product_category_3 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_product_category_3 on product (category_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists address;

drop table if exists category;

drop table if exists customer;

drop table if exists product;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists address_seq;

drop sequence if exists category_seq;

drop sequence if exists customer_seq;

drop sequence if exists product_seq;

drop sequence if exists user_seq;

