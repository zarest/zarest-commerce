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
  address_type              varchar(255),
  customer_email            varchar(255),
  constraint pk_address primary key (id))
;

create table category (
  id                        bigint not null,
  name                      varchar(255),
  parent_category_id        bigint,
  constraint pk_category primary key (id))
;

create table credit_card (
  id                        bigint not null,
  credit_card_number        varchar(255),
  exp_date                  timestamp,
  credit_card_type          varchar(255),
  customer_email            varchar(255),
  constraint pk_credit_card primary key (id))
;

create table generic_user (
  _type                     integer(31) not null,
  email                     varchar(255) not null,
  password                  varchar(255),
  name                      varchar(255),
  is_admin                  boolean,
  phone                     varchar(255),
  is_shipping_address       boolean,
  constraint pk_generic_user primary key (email))
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

create sequence address_seq;

create sequence category_seq;

create sequence credit_card_seq;

create sequence generic_user_seq;

create sequence product_seq;

alter table address add constraint fk_address_customer_1 foreign key (customer_email) references generic_user (email) on delete restrict on update restrict;
create index ix_address_customer_1 on address (customer_email);
alter table category add constraint fk_category_parentCategory_2 foreign key (parent_category_id) references category (id) on delete restrict on update restrict;
create index ix_category_parentCategory_2 on category (parent_category_id);
alter table credit_card add constraint fk_credit_card_customer_3 foreign key (customer_email) references generic_user (email) on delete restrict on update restrict;
create index ix_credit_card_customer_3 on credit_card (customer_email);
alter table product add constraint fk_product_category_4 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_product_category_4 on product (category_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists address;

drop table if exists category;

drop table if exists credit_card;

drop table if exists generic_user;

drop table if exists product;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists address_seq;

drop sequence if exists category_seq;

drop sequence if exists credit_card_seq;

drop sequence if exists generic_user_seq;

drop sequence if exists product_seq;

