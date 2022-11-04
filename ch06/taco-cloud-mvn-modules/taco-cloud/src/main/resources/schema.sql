create table if not exists Ingredient
(
    id   varchar(4)  not null PRIMARY KEY,
    name varchar(25) not null,
    type varchar(10) not null
);

CREATE TABLE IF NOT EXISTS Taco
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(50) NOT NULL,
    createdAt TIMESTAMP   NOT NULL
);

create table if not exists Taco_Ingredients
(
    taco       bigint     not null,
    ingredient varchar(4) not null
);

alter table Taco_Ingredients
    add foreign key (taco) references Taco (id);
alter table Taco_Ingredients
    add foreign key (ingredient) references Ingredient (id);

create table if not exists Taco_Order
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    deliveryName   varchar(50) not null,
    deliveryStreet varchar(50) not null,
    deliveryCity   varchar(50) not null,
    deliveryState  varchar(2)  not null,
    deliveryZip    varchar(10) not null,
    ccNumber       varchar(16) not null,
    ccExpiration   varchar(5)  not null,
    ccCVV          varchar(3)  not null,
    placedAt       timestamp   not null,
    user_id        bigint      not null
);

create table if not exists Taco_Order_Tacos
(
    tacoOrder bigint not null,
    taco      bigint not null
);

alter table Taco_Order_Tacos
    add foreign key (tacoOrder) references Taco_Order (id);
alter table Taco_Order_Tacos
    add foreign key (taco) references Taco (id);

create table if not exists t_user
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    username     varchar(255) not null,
    password     varchar(255) not null,
    fullname     varchar(255) not null,
    street       varchar(255) not null,
    city         varchar(255) not null,
    state        varchar(255) not null,
    zip          varchar(255) not null,
    phone_Number varchar(255) not null
);

CREATE TABLE if not exists `hibernate_sequence`
(
    `next_val` bigint DEFAULT NULL
);