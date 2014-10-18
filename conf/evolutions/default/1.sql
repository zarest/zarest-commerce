# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

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

create sequence category_seq;

create sequence customer_seq;

create sequence product_seq;

alter table category add constraint fk_category_parentCategory_1 foreign key (parent_category_id) references category (id) on delete restrict on update restrict;
create index ix_category_parentCategory_1 on category (parent_category_id);
alter table product add constraint fk_product_category_2 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_product_category_2 on product (category_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists category;

drop table if exists customer;

drop table if exists product;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists category_seq;

drop sequence if exists customer_seq;

drop sequence if exists product_seq;

