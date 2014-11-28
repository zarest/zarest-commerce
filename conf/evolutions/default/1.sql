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
  constraint pk_address primary key (id))
;

create table category (
  id                        bigint not null,
  name                      varchar(255),
  description               varchar(255),
  image_path                varchar(255),
  active                    boolean,
  parent_category_id        bigint,
  constraint uq_category_name unique (name),
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

create table image (
  id                        bigint not null,
  caption                   varchar(255),
  file_path                 varchar(255),
  product_id                bigint,
  constraint pk_image primary key (id))
;

create table oder_table (
  id                        bigint not null,
  customer_email            varchar(255),
  payment_id                bigint,
  order_date                timestamp,
  required_date             timestamp,
  ship_date                 timestamp,
  shipper_id                bigint,
  freight                   decimal(38),
  sales_tax                 decimal(38),
  time_stamp                varchar(255),
  transact_status           varchar(255),
  err_loc                   varchar(255),
  err_msg                   varchar(255),
  fulfilled                 boolean,
  deleted                   boolean,
  paid                      decimal(38),
  payment_date              timestamp,
  constraint pk_oder_table primary key (id))
;

create table order_details (
  id                        bigint not null,
  order_id                  bigint,
  product_id                bigint,
  price                     decimal(38),
  quantity                  smallint,
  discount                  double,
  total                     decimal(38),
  size                      varchar(255),
  color                     varchar(255),
  fulfilled                 boolean,
  bill_date                 timestamp,
  ship_date                 timestamp,
  shipper_id                bigint,
  frieght                   decimal(38),
  sales_tax                 decimal(38),
  constraint uq_order_details_1 unique (order_id,product_id),
  constraint pk_order_details primary key (id))
;

create table payment (
  id                        bigint not null,
  payment_type              varchar(255),
  allowed                   boolean,
  constraint pk_payment primary key (id))
;

create table product (
  id                        bigint not null,
  sku                       varchar(255) not null,
  supplier_prod_id          varchar(255),
  product_name              varchar(255),
  product_description       varchar(255),
  product_details           varchar(255),
  supplier_id               bigint,
  category_id               bigint,
  quantity_per_unit         integer,
  unit_size                 varchar(255),
  unit_price                decimal(38),
  msrp                      decimal(38),
  available_sizes           varchar(255),
  available_colors          varchar(255),
  discount                  double,
  unit_weight               double,
  unit_in_stock             smallint,
  unit_on_order             smallint,
  reorder_level             smallint,
  product_available         boolean,
  discount_available        boolean,
  current_order             boolean,
  ranking                   integer,
  warranty_specification    varchar(255),
  note                      varchar(255),
  constraint uq_product_sku unique (sku),
  constraint pk_product primary key (id))
;

create table shipper (
  id                        bigint not null,
  company_name              varchar(255),
  phone                     varchar(255),
  constraint pk_shipper primary key (id))
;

create table supplier (
  id                        bigint not null,
  company_name              varchar(255),
  contact_first_name        varchar(255),
  contact_last_name         varchar(255),
  contact_title             varchar(255),
  phone                     varchar(255),
  fax                       varchar(255),
  email                     varchar(255),
  website                   varchar(255),
  payment_methods           varchar(255),
  discount_types            varchar(255),
  discount_rate             float,
  type_goods                varchar(255),
  discount_available        boolean,
  current_order             boolean,
  customer_id               varchar(255),
  size_url                  varchar(255),
  color_url                 varchar(255),
  logo_image                varchar(255),
  ranking                   integer,
  note                      varchar(255),
  constraint pk_supplier primary key (id))
;


create table customer_address (
  generic_user_email             varchar(255) not null,
  address_id                     bigint not null,
  constraint pk_customer_address primary key (generic_user_email, address_id))
;

create table Supplier_address (
  supplier_id                    bigint not null,
  address_id                     bigint not null,
  constraint pk_Supplier_address primary key (supplier_id, address_id))
;
create sequence address_seq;

create sequence category_seq;

create sequence credit_card_seq;

create sequence generic_user_seq;

create sequence image_seq;

create sequence oder_table_seq;

create sequence order_details_seq;

create sequence payment_seq;

create sequence product_seq;

create sequence shipper_seq;

create sequence supplier_seq;

alter table category add constraint fk_category_parentCategory_1 foreign key (parent_category_id) references category (id) on delete restrict on update restrict;
create index ix_category_parentCategory_1 on category (parent_category_id);
alter table credit_card add constraint fk_credit_card_customer_2 foreign key (customer_email) references generic_user (email) on delete restrict on update restrict;
create index ix_credit_card_customer_2 on credit_card (customer_email);
alter table image add constraint fk_image_product_3 foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_image_product_3 on image (product_id);
alter table oder_table add constraint fk_oder_table_customer_4 foreign key (customer_email) references generic_user (email) on delete restrict on update restrict;
create index ix_oder_table_customer_4 on oder_table (customer_email);
alter table oder_table add constraint fk_oder_table_payment_5 foreign key (payment_id) references payment (id) on delete restrict on update restrict;
create index ix_oder_table_payment_5 on oder_table (payment_id);
alter table oder_table add constraint fk_oder_table_shipper_6 foreign key (shipper_id) references shipper (id) on delete restrict on update restrict;
create index ix_oder_table_shipper_6 on oder_table (shipper_id);
alter table order_details add constraint fk_order_details_order_7 foreign key (order_id) references oder_table (id) on delete restrict on update restrict;
create index ix_order_details_order_7 on order_details (order_id);
alter table order_details add constraint fk_order_details_product_8 foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_order_details_product_8 on order_details (product_id);
alter table product add constraint fk_product_supplier_9 foreign key (supplier_id) references supplier (id) on delete restrict on update restrict;
create index ix_product_supplier_9 on product (supplier_id);
alter table product add constraint fk_product_category_10 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_product_category_10 on product (category_id);



alter table customer_address add constraint fk_customer_address_generic_u_01 foreign key (generic_user_email) references generic_user (email) on delete restrict on update restrict;

alter table customer_address add constraint fk_customer_address_address_02 foreign key (address_id) references address (id) on delete restrict on update restrict;

alter table Supplier_address add constraint fk_Supplier_address_supplier_01 foreign key (supplier_id) references supplier (id) on delete restrict on update restrict;

alter table Supplier_address add constraint fk_Supplier_address_address_02 foreign key (address_id) references address (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists address;

drop table if exists category;

drop table if exists credit_card;

drop table if exists generic_user;

drop table if exists image;

drop table if exists oder_table;

drop table if exists order_details;

drop table if exists payment;

drop table if exists product;

drop table if exists shipper;

drop table if exists supplier;

drop table if exists Supplier_address;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists address_seq;

drop sequence if exists category_seq;

drop sequence if exists credit_card_seq;

drop sequence if exists generic_user_seq;

drop sequence if exists image_seq;

drop sequence if exists oder_table_seq;

drop sequence if exists order_details_seq;

drop sequence if exists payment_seq;

drop sequence if exists product_seq;

drop sequence if exists shipper_seq;

drop sequence if exists supplier_seq;

