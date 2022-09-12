DROP TABLE IF EXISTS addresses CASCADE;
DROP TABLE IF EXISTS announcement_rent_purchases CASCADE;
DROP TABLE IF EXISTS announcement_rent_timetables CASCADE;
DROP TABLE IF EXISTS announcement_top_prices CASCADE;
DROP TABLE IF EXISTS announcement_top_purchases CASCADE;
DROP TABLE IF EXISTS announcement_top_timetables CASCADE;
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

INSERT INTO users(username, password, last_name, first_name, patronymic, email, phone_number)
VALUES
    ('admin', '$2a$12$hkjIsXbZRBA/QowSzO6SRO2CzqJ3Z47fWKhyTFcDv0aWeDF8VIiuW', 'adminLastName', 'adminFirstName', 'adminPatronymic', 'adminEmail', '80000000000'),
    ('user', '$2a$12$xOXXXc4/JffK6dgcjmQknOWUDtZFSx3sWRD4VEEZwUYDC8wZ2/3mC', 'userLastName', 'userFirstName', 'userPatronymic', 'userEmail', '89842176544'),
    ('user1', '$2a$12$GccR6CmrLuR4FOH8n7Gl0eqvfgVX7hwh2ZPqiSJr5ipxY4iNjrWZe', 'user1LastName', 'user1FirstName', 'user1Patronymic', 'user1Email', '85475685154'),
    ('user2', '$2a$12$DovBkrdsfn82aTv1/BFi8emWt8pPRCfW9IdMbXQFavOLU6ucWi2J2', 'user2LastName', 'user2FirstName', 'user2Patronymic', 'user2Email', '86542568752'),
    ('admin1', '$2a$12$arGj2e/7MJvVsbqXXyQBS.vT1TmVGp2DjjpUn2zy4aw7tpGIfxYQ.', 'admin1LastName', 'admin1FirstName', 'admin1Patronymic', 'admin1Email', '86547589001');

INSERT INTO users(username, password, enabled, last_name, first_name, patronymic, email, phone_number, balance)
VALUES
    ('rich_user', '$2a$12$R3lh47p411V9tmOu1A4fOedVvsg2ZwDBar0ZIg.ZQmtUHVl7h3MRm', true, 'rich', 'user', 'rich', 'richuser@gold.com', '89545477885', 54998000000);

INSERT INTO user_role(user_id, role_id)
VALUES
    (100, 100),
    (101, 101),
    (102, 101),
    (103, 101),
    (104, 100),
    (105, 101);

INSERT INTO reviews(user_id_customer, user_id_seller, comment, note)
VALUES
    (101, 100, 'sample comment from user', 5),
    (102, 100, 'sample comment from user1', 3),
    (103, 100, 'sample comment from user2', 3);

INSERT INTO messages(user_id_sender, user_id_receiver, text, created_date_time)
VALUES
    (101, 100, 'Добрый день!', '2022-08-10 09:00:00'),
    (101, 100, 'Меня зовут user.', '2022-08-10 09:00:12'),
    (101, 100, 'Меня интересует объявление 100. Можете пожалуйста по подробнее рассказать про него?)', '2022-08-10 09:00:36'),
    (100, 101, 'Здравствуйте! На связи admin 😎', '2022-08-10 09:01:00'),
    (100, 101, 'Конечно. Вас какой именно вопрос интересует?', '2022-08-10 09:01:06'),
    (101, 100, 'Хочу узнать доступность до школы и детского сада, а также до продуктовых магазинов', '2022-08-10 09:01:20'),
    (100, 101, 'Рядом с домом находится школа №192, 6 минут пешком, а ещё детский садик "Колобок", до него идти также, они в одном направлении. Ещё в шаговой доступности находятся магазин Магнит, Пятёрочка и Вкус Вилл.', '2022-08-10 09:02:03'),
    (101, 100, 'Звучит здорово! Спасибо!', '2022-08-10 09:02:36'),
    (100, 101, 'Всегда рад помочь! Если у Вас возникнут ещё какие-нибудь вопросы, смело обращайтесь) 😊', '2022-08-10 09:03:04'),
    (101, 100, 'Обязательно! 🙂👌', '2022-08-10 09:03:12'),
    (102, 100, 'Добрый день!', '2022-07-10 09:00:00'),
    (102, 100, 'Меня зовут user1.', '2022-07-10 09:00:12'),
    (102, 100, 'Меня интересует объявление 100. Можете пожалуйста по подробнее рассказать про него?)', '2022-07-10 09:00:36'),
    (100, 102, 'Здравствуйте! На связи admin 😎', '2022-07-10 09:01:00'),
    (100, 102, 'Конечно. Вас какой именно вопрос интересует?', '2022-07-10 09:01:06'),
    (102, 100, 'Хочу узнать доступность до школы и детского сада, а также до продуктовых магазинов', '2022-07-10 09:01:20'),
    (100, 102, 'Рядом с домом находится школа №192, 6 минут пешком, а ещё детский садик "Колобок", до него идти также, они в одном направлении. Ещё в шаговой доступности находятся магазин Магнит, Пятёрочка и Вкус Вилл.', '2022-07-10 09:02:03'),
    (102, 100, 'Звучит здорово! Спасибо!', '2022-07-10 09:02:36'),
    (100, 102, 'Всегда рад помочь! Если у Вас возникнут ещё какие-нибудь вопросы, смело обращайтесь) 😊', '2022-07-10 09:03:04'),
    (102, 100, 'Обязательно! 🙂👌', '2022-07-10 09:03:12'),
    (100, 103, 'Добрый день! Если Вас интересуют какие-либо вопросы, пишите мне) Буду рад вам ответить)', '2022-08-10 09:00:00'),
    (104, 100, 'Коллега, добрый день! Нужна будет помощь, пиши)', '2022-08-11 08:50:00');

INSERT INTO balance_operations(user_id, sum, comment)
VALUES
    (104, 55000000000, null);



INSERT INTO regions(name)
VALUES
    ('Московская область'),
    ('Ленинградская область'),
    ('Калининградская область');

INSERT INTO cities(region_id, name)
VALUES
    (100, 'Москва'),
    (101, 'Санкт-Петербург'),
    (102, 'Калининград'),
    (102, 'Голубёво');

INSERT INTO streets(city_id, name)
VALUES
    (100, 'Кутузовский проспект'),
    (101, 'Проспект Мира'),
    (102, 'Александра невского'),
    (102, 'Старокаменная'),
    (103, 'Лазурная');

INSERT INTO addresses(street_id, house_number)
VALUES
    (100, '40'),
    (101, '34'),
    (102, '220'),
    (103, '3'),
    (104, '11');



INSERT INTO house_materials(name)
VALUES
    ('Кирпичный'),
    ('Деревянный'),
    ('Монолитный'),
    ('Панельный'),
    ('Блочный'),
    ('Кирпично-монолитный'),
    ('Сталинский');

INSERT INTO renovation_types(name)
VALUES
    ('Без ремонта'),
    ('Косметический'),
    ('Евроремонт'),
    ('Дизайнерский');

INSERT INTO apartment_houses(address_id, number_of_floors, building_year, house_material_id, housing_type, elevator)
VALUES
    (100, 90, 2020, 100, 'NEW_CONSTRUCTION', true),
    (102, 10, 2020, 100, 'SECONDARY', true),
    (103, 10, 2022, 103, 'SECONDARY', true),
    (104, 3, 2020, 104, 'SECONDARY', true);

INSERT INTO apartment_properties(area, user_id, apartment_house_id, number_of_rooms, renovation_type_id, apartment_number, floor)
VALUES
    (84, 100, 100, 3, 100, 50, 4),
    (84, 105, 100, 3, 100, 25, 2),
    (84, 100, 101, 1, 101, 13, 2),
    (84, 100, 102, 1, 100, 45, 8),
    (84, 100, 103, 1, 101, 40, 1);


INSERT INTO family_houses(address_id, number_of_floors, building_year, house_material_id, housing_type)
VALUES
    (101, 3, 1985, 100, 'SECONDARY');

INSERT INTO family_house_properties(area, user_id, number_of_rooms, renovation_type_id, family_house_id)
VALUES
    (160, 105, 4, 102, 104);


INSERT INTO land_properties(area, user_id, street_id)
VALUES
    (120, 100, 101);



INSERT INTO apartment_announcements(price, description, type, status, apartment_property_id)
VALUES
    (11000000, 'sample description', 'SELL', 'OPEN', 100),
    (35000, 'sample description', 'MONTHLY_RENT', 'OPEN', 101),
    (4200000, 'sample description', 'SELL', 'DELETED', 102),
    (4400000, 'sample description', 'SELL', 'HIDDEN', 103);

INSERT INTO apartment_announcements(price, description, created_date_time, type, status, closed_date_time, apartment_property_id)
VALUES
    (2650000, 'sample description', '2022-08-10 09:00:00', 'SELL', 'CLOSED', '2022-09-09 10:00:00', 104);

INSERT INTO family_house_announcements(price, description, type, status, family_house_property_id)
VALUES
    (25000, 'sample description', 'MONTHLY_RENT', 'HIDDEN', 105),
    (4500, 'sample description', 'DAILY_RENT', 'OPEN', 105);

INSERT INTO land_announcements(price, description, created_date_time, type, status, land_property_id, closed_date_time)
VALUES
    (54000000, 'sample description', '2022-08-10 09:00:00', 'SELL', 'CLOSED', 106, '2022-09-09 10:00:00');


INSERT INTO announcement_top_prices(property_type, announcement_type, price_per_hour)
VALUES
    ('APARTMENT', 'SELL', 800),
    ('APARTMENT', 'DAILY_RENT', 650),
    ('APARTMENT', 'MONTHLY_RENT', 350),
    ('FAMILY_HOUSE', 'SELL', 600),
    ('FAMILY_HOUSE', 'DAILY_RENT', 750),
    ('FAMILY_HOUSE', 'MONTHLY_RENT', 250),
    ('LAND', 'SELL', 300);


INSERT INTO apartment_announcement_top_timetables(from_date_time, to_date_time, apartment_announcement_id)
VALUES
    ('2022-08-10 09:00:00', '2023-08-10 09:00:00', 101);

INSERT INTO balance_operations(user_id, sum, comment)
VALUES
    (105, -1000000, 'top apartment with id 103 on 2022-08-10 09:00:00 - 2023-08-10 09:00:00');

INSERT INTO apartment_announcement_top_purchases(balance_operation_id, apartment_announcement_top_timetable_id)
VALUES
    (102, 100);



INSERT INTO family_house_announcement_top_timetables(from_date_time, to_date_time, family_house_announcement_id)
VALUES
    ('2022-08-10 09:00:00', '2023-08-10 09:00:00', 105);

INSERT INTO balance_operations(user_id, sum, comment)
VALUES
    (105, -1000000, 'rent family house with id 101 on 2022-08-10 09:00:00 - 2023-08-10 09:00:00');

INSERT INTO family_house_announcement_top_purchases(balance_operation_id, family_house_announcement_top_timetable_id)
VALUES
    (101, 101);



INSERT INTO apartment_announcement_rent_timetables(user_id_tenant, apartment_announcement_id, from_date_time, to_date_time)
VALUES
    (100, 101, '2022-08-01 14:00:00', '2022-09-01 14:00:00'),
    (102, 101, '2022-10-01 14:00:00', '2023-02-01 14:00:00');

INSERT INTO balance_operations(user_id, sum, comment)
VALUES
    (100, -35000, 'DAILY_RENT');

INSERT INTO apartment_announcement_rent_purchases(balance_operation_id, apartment_announcement_rent_timetable_id)
VALUES
    (102, 100);


INSERT INTO family_house_announcement_rent_timetables(user_id_tenant, family_house_announcement_id, from_date_time, to_date_time)
VALUES
    (105, 106, '2022-08-10 14:00:00', '2022-08-15 14:00:00'),
    (102, 106, '2022-08-15 14:00:00', '2022-08-21 14:00:00'),
    (103, 106, '2022-08-23 14:00:00', '2022-08-24 14:00:00'),
    (105, 106, '2022-09-10 14:00:00', '2022-09-20 14:00:00');


