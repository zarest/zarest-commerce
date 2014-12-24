# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table address (
  id                        bigint auto_increment not null,
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
  id                        bigint auto_increment not null,
  name                      varchar(255),
  description               varchar(255),
  image_path                varchar(255),
  active                    tinyint(1) default 0,
  parent_category_id        bigint,
  constraint uq_category_name unique (name),
  constraint pk_category primary key (id))
;

create table credit_card (
  id                        bigint auto_increment not null,
  credit_card_number        varchar(255),
  exp_date                  datetime,
  credit_card_type          varchar(255),
  customer_email            varchar(255),
  constraint pk_credit_card primary key (id))
;

create table generic_user (
  _type                     integer(31) not null,
  email                     varchar(255) not null,
  password                  varchar(255),
  name                      varchar(255),
  is_admin                  tinyint(1) default 0,
  phone                     varchar(255),
  is_shipping_address       tinyint(1) default 0,
  constraint pk_generic_user primary key (email))
;

create table image (
  id                        bigint auto_increment not null,
  caption                   varchar(255),
  file_path                 varchar(255),
  product_id                bigint,
  constraint pk_image primary key (id))
;

create table oder_table (
  id                        bigint auto_increment not null,
  customer_email            varchar(255),
  payment_id                bigint,
  order_date                datetime,
  required_date             datetime,
  ship_date                 datetime,
  shipper_id                bigint,
  freight                   decimal(38),
  sales_tax                 decimal(38),
  time_stamp                varchar(255),
  transact_status           varchar(255),
  err_loc                   varchar(255),
  err_msg                   varchar(255),
  fulfilled                 tinyint(1) default 0,
  deleted                   tinyint(1) default 0,
  paid                      decimal(38),
  payment_date              datetime,
  constraint pk_oder_table primary key (id))
;

create table order_details (
  id                        bigint auto_increment not null,
  order_id                  bigint,
  product_id                bigint,
  price                     decimal(38),
  quantity                  smallint,
  discount                  double,
  total                     decimal(38),
  size                      varchar(255),
  color                     varchar(255),
  fulfilled                 tinyint(1) default 0,
  bill_date                 datetime,
  ship_date                 datetime,
  shipper_id                bigint,
  frieght                   decimal(38),
  sales_tax                 decimal(38),
  constraint uq_order_details_1 unique (order_id,product_id),
  constraint pk_order_details primary key (id))
;

create table payment (
  id                        bigint auto_increment not null,
  payment_type              varchar(255),
  allowed                   tinyint(1) default 0,
  constraint pk_payment primary key (id))
;

create table product (
  id                        bigint auto_increment not null,
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
  product_available         tinyint(1) default 0,
  discount_available        tinyint(1) default 0,
  current_order             tinyint(1) default 0,
  ranking                   integer,
  warranty_specification    varchar(255),
  note                      varchar(255),
  date                      datetime,
  constraint uq_product_sku unique (sku),
  constraint pk_product primary key (id))
;

create table shipper (
  id                        bigint auto_increment not null,
  company_name              varchar(255),
  phone                     varchar(255),
  constraint pk_shipper primary key (id))
;

create table supplier (
  id                        bigint auto_increment not null,
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
  discount_available        tinyint(1) default 0,
  current_order             tinyint(1) default 0,
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



alter table customer_address add constraint fk_customer_address_generic_user_01 foreign key (generic_user_email) references generic_user (email) on delete restrict on update restrict;

alter table customer_address add constraint fk_customer_address_address_02 foreign key (address_id) references address (id) on delete restrict on update restrict;

alter table Supplier_address add constraint fk_Supplier_address_supplier_01 foreign key (supplier_id) references supplier (id) on delete restrict on update restrict;

alter table Supplier_address add constraint fk_Supplier_address_address_02 foreign key (address_id) references address (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table address;

drop table category;

drop table credit_card;

drop table generic_user;

drop table image;

drop table oder_table;

drop table order_details;

drop table payment;

drop table product;

drop table shipper;

drop table supplier;

drop table Supplier_address;

SET FOREIGN_KEY_CHECKS=1;

