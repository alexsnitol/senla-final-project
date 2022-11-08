DROP TABLE IF EXISTS addresses CASCADE;
DROP TABLE IF EXISTS announcement_rent_purchases CASCADE;
DROP TABLE IF EXISTS apartment_announcement_rent_purchases CASCADE;
DROP TABLE IF EXISTS family_house_announcement_rent_purchases CASCADE;
DROP TABLE IF EXISTS announcement_rent_timetables CASCADE;
DROP TABLE IF EXISTS apartment_announcement_rent_timetables CASCADE;
DROP TABLE IF EXISTS family_house_announcement_rent_timetables CASCADE;
DROP TABLE IF EXISTS announcement_top_prices CASCADE;
DROP TABLE IF EXISTS announcement_top_purchases CASCADE;
DROP TABLE IF EXISTS apartment_announcement_top_purchases CASCADE;
DROP TABLE IF EXISTS family_house_announcement_top_purchases CASCADE;
DROP TABLE IF EXISTS land_announcement_top_purchases CASCADE;
DROP TABLE IF EXISTS announcement_top_timetables CASCADE;
DROP TABLE IF EXISTS apartment_announcement_top_timetables CASCADE;
DROP TABLE IF EXISTS family_house_announcement_top_timetables CASCADE;
DROP TABLE IF EXISTS land_announcement_top_timetables CASCADE;
DROP TABLE IF EXISTS announcements CASCADE;
DROP TABLE IF EXISTS apartment_announcements CASCADE;
DROP TABLE IF EXISTS apartment_houses CASCADE;
DROP TABLE IF EXISTS apartment_properties CASCADE;
DROP TABLE IF EXISTS authorities CASCADE;
DROP TABLE IF EXISTS balance_operations CASCADE;
DROP TABLE IF EXISTS cities CASCADE;
DROP TABLE IF EXISTS family_house_announcements CASCADE;
DROP TABLE IF EXISTS family_house_properties CASCADE;
DROP TABLE IF EXISTS family_houses CASCADE;
DROP TABLE IF EXISTS house_materials CASCADE;
DROP TABLE IF EXISTS houses CASCADE;
DROP TABLE IF EXISTS housing_announcements CASCADE;
DROP TABLE IF EXISTS housing_properties CASCADE;
DROP TABLE IF EXISTS land_announcements CASCADE;
DROP TABLE IF EXISTS land_properties CASCADE;
DROP TABLE IF EXISTS messages CASCADE;
DROP TABLE IF EXISTS properties CASCADE;
DROP TABLE IF EXISTS purchases CASCADE;
DROP TABLE IF EXISTS reviews CASCADE;
DROP TABLE IF EXISTS regions CASCADE;
DROP TABLE IF EXISTS renovation_types CASCADE;
DROP TABLE IF EXISTS role_authority CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS streets CASCADE;
DROP TABLE IF EXISTS user_role CASCADE;
DROP TABLE IF EXISTS users CASCADE;


DROP SEQUENCE IF EXISTS seq_addresses;
DROP SEQUENCE IF EXISTS seq_announcement_rent_timetables;
DROP SEQUENCE IF EXISTS seq_announcement_top_prices;
DROP SEQUENCE IF EXISTS seq_announcement_top_timetables;
DROP SEQUENCE IF EXISTS seq_announcements;
DROP SEQUENCE IF EXISTS seq_authorities;
DROP SEQUENCE IF EXISTS seq_balance_operations;
DROP SEQUENCE IF EXISTS seq_cities;
DROP SEQUENCE IF EXISTS seq_house_materials;
DROP SEQUENCE IF EXISTS seq_houses;
DROP SEQUENCE IF EXISTS seq_messages;
DROP SEQUENCE IF EXISTS seq_properties;
DROP SEQUENCE IF EXISTS seq_purchases;
DROP SEQUENCE IF EXISTS seq_reviews;
DROP SEQUENCE IF EXISTS seq_regions;
DROP SEQUENCE IF EXISTS seq_renovation_types;
DROP SEQUENCE IF EXISTS seq_role_authority;
DROP SEQUENCE IF EXISTS seq_roles;
DROP SEQUENCE IF EXISTS seq_streets;
DROP SEQUENCE IF EXISTS seq_user_role;
DROP SEQUENCE IF EXISTS seq_users;







CREATE SEQUENCE seq_addresses START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_announcement_rent_timetables START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_announcement_top_prices START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_announcement_top_timetables START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_announcements START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_authorities START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_balance_operations START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_cities START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_house_materials START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_houses START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_messages START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_properties START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_purchases START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_reviews START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_regions START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_renovation_types START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_role_authority START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_roles START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_streets START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_user_role START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE seq_users START WITH 100 INCREMENT BY 1;



SET CLIENT_ENCODING TO 'utf8';



-- USERS

CREATE TABLE authorities (
  id   int8 NOT NULL DEFAULT nextval('seq_authorities'),
  name varchar(255) NOT NULL UNIQUE,

  PRIMARY KEY (id)
);

CREATE TABLE roles (
  id   int8 NOT NULL DEFAULT nextval('seq_roles'),
  name varchar(255) NOT NULL UNIQUE,

  PRIMARY KEY (id)
);

CREATE TABLE role_authority (
  id           int8 NOT NULL DEFAULT nextval('seq_role_authority'),
  role_id      int8 NOT NULL,
  authority_id int8 NOT NULL,

  PRIMARY KEY (id),

  FOREIGN KEY (role_id) REFERENCES roles (id),
  FOREIGN KEY (authority_id) REFERENCES authorities (id)
);

CREATE TABLE users (
  id                    int8 NOT NULL DEFAULT nextval('seq_users'),
  username              varchar(255) NOT NULL UNIQUE,
  password              varchar(60) NOT NULL,
  enabled               bool DEFAULT true NOT NULL,
  last_name             varchar(255),
  first_name            varchar(255),
  patronymic            varchar(255),
  email                 varchar(255),
  phone_number          varchar(15),
  balance               float8 DEFAULT 0 NOT NULL,
  created_date_time     timestamp NOT NULL DEFAULT now(),

  PRIMARY KEY (id)
);

CREATE TABLE user_role (
  id      int8 NOT NULL DEFAULT nextval('seq_user_role'),
  user_id int8 NOT NULL,
  role_id int8 NOT NULL,

  PRIMARY KEY (id),

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE balance_operations (
  id                int8 NOT NULL DEFAULT nextval('seq_balance_operations'),
  user_id           int8 NOT NULL,
  sum               float8 NOT NULL,
  created_date_time timestamp NOT NULL DEFAULT now(),
  comment           varchar(255),

  PRIMARY KEY (id),

  FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE messages (
  id                int8 NOT NULL DEFAULT nextval('seq_messages'),
  user_id_sender    int8 NOT NULL,
  user_id_receiver  int8 NOT NULL,
  text              varchar(1023) NOT NULL,
  created_date_time timestamp NOT NULL DEFAULT now(),

  PRIMARY KEY (id),

  FOREIGN KEY (user_id_sender) REFERENCES users (id),
  FOREIGN KEY (user_id_receiver) REFERENCES users (id)
);

CREATE TABLE reviews (
  id               int8 NOT NULL DEFAULT nextval('seq_reviews'),
  user_id_customer int8 NOT NULL,
  user_id_seller   int8 NOT NULL,
  comment          varchar(2047),
  note             int2 NOT NULL,
  created_date_time       timestamp NOT NULL DEFAULT now(),

  PRIMARY KEY (id),

  FOREIGN KEY (user_id_customer) REFERENCES users (id),
  FOREIGN KEY (user_id_seller) REFERENCES users (id)
);


-- ADDRESSES

CREATE TABLE regions (
  id   int8 NOT NULL DEFAULT nextval('seq_regions'),
  name varchar(255) NOT NULL UNIQUE,

  PRIMARY KEY (id)
);

CREATE TABLE cities (
  id            int8 NOT NULL DEFAULT nextval('seq_cities'),
  region_id     int8,
  name          varchar(255) NOT NULL,

  PRIMARY KEY (id),

  FOREIGN KEY (region_id) REFERENCES regions (id)
);

CREATE TABLE streets (
  id        int8 NOT NULL DEFAULT nextval('seq_streets'),
  city_id   int8 NOT NULL,
  name      varchar(255) NOT NULL,

  PRIMARY KEY (id),

  FOREIGN KEY (city_id) REFERENCES cities (id)
);

CREATE TABLE addresses (
  id                    int8 NOT NULL DEFAULT nextval('seq_addresses'),
  street_id             int8 NOT NULL,
  house_number          varchar(255), 
  
  PRIMARY KEY (id),

  FOREIGN KEY (street_id) REFERENCES streets (id)
);


-- HOUSES

CREATE TABLE house_materials (
  id   int8 NOT NULL DEFAULT nextval('seq_house_materials'),
  name varchar(255) UNIQUE,

  PRIMARY KEY (id)
);

CREATE TABLE houses (
  id                int8 NOT NULL DEFAULT nextval('seq_houses'),
  address_id        int8 NOT NULL UNIQUE,
  number_of_floors  int2,
  building_year     int2,
  house_material_id int8,
  housing_type      varchar(31),

  PRIMARY KEY (id),

  FOREIGN KEY (address_id) REFERENCES addresses (id),
  FOREIGN KEY (house_material_id) REFERENCES house_materials (id)
);

CREATE TABLE apartment_houses (
  elevator bool,

  PRIMARY KEY (id)
) INHERITS (houses);

CREATE TABLE family_houses (
  swimming_pool bool DEFAULT false,

  PRIMARY KEY (id)
) INHERITS (houses);


-- PROPERTIES

CREATE TABLE properties (
  id      int8 NOT NULL DEFAULT nextval('seq_properties'),
  area    float4,
  user_id int8 NOT NULL,
  status  varchar(15) DEFAULT 'ACTIVE' NOT NULL,

  PRIMARY KEY (id),

  FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE renovation_types (
  id   int8 NOT NULL DEFAULT nextval('seq_renovation_types'),
  name varchar(255) NOT NULL UNIQUE,

  PRIMARY KEY (id)
);

CREATE TABLE housing_properties (
  number_of_rooms    int2,
  renovation_type_id int8,

  PRIMARY KEY (id),

  FOREIGN KEY (renovation_type_id) REFERENCES renovation_types (id)
) INHERITS (properties);

CREATE TABLE apartment_properties (
  apartment_house_id int8 NOT NULL,
  apartment_number   varchar(255),
  floor              int2,

  PRIMARY KEY (id),

  FOREIGN KEY (apartment_house_id) REFERENCES apartment_houses (id)
) INHERITS (housing_properties);

CREATE TABLE family_house_properties (
  family_house_id int8 NOT NULL UNIQUE,

  PRIMARY KEY (id),

  FOREIGN KEY (family_house_id) REFERENCES family_houses (id)
) INHERITS (housing_properties);

CREATE TABLE land_properties (
  street_id int8 NOT NULL,

  PRIMARY KEY (id),

  FOREIGN KEY (street_id) REFERENCES streets (id)
) INHERITS (properties);


-- ANNOUNCEMENTS

CREATE TABLE announcements (
  id                int8 NOT NULL DEFAULT nextval('seq_announcements'),
  price             float8 NOT NULL,
  description       varchar(4095),
  created_date_time timestamp NOT NULL DEFAULT now(),
  type              varchar(15) NOT NULL,
  status            varchar(15) DEFAULT 'HIDDEN' NOT NULL,
  closed_date_time  timestamp DEFAULT null,

  PRIMARY KEY (id)
);


-- HOUSING ANNOUNCEMENTS

CREATE TABLE housing_announcements (
  PRIMARY KEY (id)
) INHERITS (announcements);

CREATE TABLE apartment_announcements (
  apartment_property_id int8 NOT NULL,

  PRIMARY KEY (id),

  FOREIGN KEY (apartment_property_id) REFERENCES apartment_properties (id)
) INHERITS (housing_announcements);

CREATE TABLE family_house_announcements (
  family_house_property_id int8 NOT NULL,

  PRIMARY KEY (id),

  FOREIGN KEY (family_house_property_id) REFERENCES family_house_properties (id)
) INHERITS (housing_announcements);


-- NON HOUSING ANNOUNCEMENTS

CREATE TABLE land_announcements (
  land_property_id int8 NOT NULL,

  PRIMARY KEY (id),

  FOREIGN KEY (land_property_id) REFERENCES land_properties (id)
) INHERITS (announcements);


-- RENT TIMETABLES

CREATE TABLE announcement_rent_timetables (
  id                      int8 NOT NULL DEFAULT nextval('seq_announcement_rent_timetables'),
  user_id_tenant          int8 NOT NULL,
  from_date_time          timestamp NOT NULL,
  to_date_time            timestamp NOT NULL,

  PRIMARY KEY (id),

  FOREIGN KEY (user_id_tenant) REFERENCES users (id)
);

CREATE TABLE apartment_announcement_rent_timetables (
  apartment_announcement_id   int8 NOT NULL,
    
  PRIMARY KEY (id),
                                                   
  FOREIGN KEY (apartment_announcement_id) REFERENCES apartment_announcements (id)                                                
) INHERITS (announcement_rent_timetables);

CREATE TABLE family_house_announcement_rent_timetables (
  family_house_announcement_id   int8 NOT NULL,
    
  PRIMARY KEY (id),
                                                   
  FOREIGN KEY (family_house_announcement_id) REFERENCES family_house_announcements (id)                                                
) INHERITS (announcement_rent_timetables);


-- TOP PRICES

CREATE TABLE announcement_top_prices (
  id                int8 NOT NULL DEFAULT nextval('seq_announcement_top_prices'),
  property_type     varchar(15) NOT NULL,
  announcement_type varchar(15) NOT NULL,
  price_per_hour    float4 NOT NULL,

  PRIMARY KEY (id)
);


-- TOP TIMETABLES

CREATE TABLE announcement_top_timetables (
  id              int8 NOT NULL DEFAULT nextval('seq_announcement_top_timetables'),
  from_date_time  timestamp NOT NULL,
  to_date_time    timestamp NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE apartment_announcement_top_timetables (
  apartment_announcement_id int8 NOT NULL,

  PRIMARY KEY (id),

  FOREIGN KEY (apartment_announcement_id) REFERENCES apartment_announcements (id)
) INHERITS (announcement_top_timetables);

CREATE TABLE family_house_announcement_top_timetables (
  family_house_announcement_id int8 NOT NULL,

  PRIMARY KEY (id),

  FOREIGN KEY (family_house_announcement_id) REFERENCES family_house_announcements (id)
) INHERITS (announcement_top_timetables);

CREATE TABLE land_announcement_top_timetables (
  land_announcement_id int8 NOT NULL,

  PRIMARY KEY (id),

  FOREIGN KEY (land_announcement_id) REFERENCES land_announcements (id)
) INHERITS (announcement_top_timetables);



-- PURCHASES

CREATE TABLE purchases (
  id                   int8 NOT NULL DEFAULT nextval('seq_purchases'),
  balance_operation_id int8 NOT NULL,

  PRIMARY KEY (id),

  FOREIGN KEY (balance_operation_id) REFERENCES balance_operations (id)
);


-- RENT PURCHASES

CREATE TABLE announcement_rent_purchases (
  PRIMARY KEY (id)
) INHERITS (purchases);

CREATE TABLE apartment_announcement_rent_purchases (
  apartment_announcement_rent_timetable_id int8 NOT NULL,
  
  PRIMARY KEY (id),

  FOREIGN KEY (apartment_announcement_rent_timetable_id) REFERENCES apartment_announcement_rent_timetables (id)
) INHERITS (announcement_rent_purchases);

CREATE TABLE family_house_announcement_rent_purchases (
  family_house_announcement_rent_timetable_id int8 NOT NULL,
  
  PRIMARY KEY (id),

  FOREIGN KEY (family_house_announcement_rent_timetable_id) REFERENCES family_house_announcement_rent_timetables (id)
) INHERITS (announcement_rent_purchases);


-- TOP PURCHASES

CREATE TABLE announcement_top_purchases (
  PRIMARY KEY (id)
) INHERITS (purchases);

CREATE TABLE apartment_announcement_top_purchases (
  apartment_announcement_top_timetable_id int8 NOT NULL,

  PRIMARY KEY (id),

  FOREIGN KEY (apartment_announcement_top_timetable_id) REFERENCES apartment_announcement_top_timetables (id)
) INHERITS (announcement_top_purchases);

CREATE TABLE family_house_announcement_top_purchases (
  family_house_announcement_top_timetable_id int8 NOT NULL,

  PRIMARY KEY (id),

  FOREIGN KEY (family_house_announcement_top_timetable_id) REFERENCES family_house_announcement_top_timetables (id)
) INHERITS (announcement_top_purchases);

CREATE TABLE land_announcement_top_purchases (
  land_announcement_top_timetable_id int8 NOT NULL,

  PRIMARY KEY (id),

  FOREIGN KEY (land_announcement_top_timetable_id) REFERENCES land_announcement_top_timetables (id)
) INHERITS (announcement_top_purchases);



INSERT INTO roles(name)
VALUES
    ('ROLE_ADMIN'),
    ('ROLE_USER');