create database pokedex;
use pokedex;

create table generations
(
    id             int auto_increment primary key,
    identifier     varchar(79) not null
);


create table move_damage_classes
(
    id         int auto_increment primary key,
    identifier varchar(79) not null
);


create table types
(
    id int auto_increment primary key,
    identifier      varchar(79) not null
);


create table moves
(
    id                      int auto_increment primary key,
    identifier              varchar(79) not null,
    generation_id           int         not null,
    type_id                 int         not null,
    power                   smallint    null,
    pp                      smallint    null,
    accuracy                smallint    null,
    priority                smallint    not null,
    damage_class_id         int         not null,
    constraint moves_ibfk_1 foreign key (generation_id) references generations (id),
    constraint moves_ibfk_2 foreign key (type_id) references types (id),
    constraint moves_ibfk_4 foreign key (damage_class_id) references move_damage_classes (id)
);

create table pokemon_colors
(
    id         int         not null primary key,
    identifier varchar(79) not null
);

create table pokemon_habitats
(
    id         int         not null primary key,
    identifier varchar(79) not null
);

create table type_efficacy
(
    damage_type_id int not null,
    target_type_id int not null,
    damage_factor  int not null,
    primary key (damage_type_id, target_type_id),
    constraint type_efficacy_ibfk_1 foreign key (damage_type_id) references types (id),
    constraint type_efficacy_ibfk_2 foreign key (target_type_id) references types (id)
);

create index target_type_id
    on type_efficacy (target_type_id);

create table pokemon_shapes
(
    id int auto_increment primary key,
    identifier varchar(79) not null
);


create table pokemon_species
(
    id                      int auto_increment primary key,
    identifier              varchar(79) not null,
    generation_id           int         null,
    evolves_from_species_id int         null,
    color_id                int         not null,
    shape_id                int         null,
    habitat_id              int         null,
    capture_rate            int         not null,
    is_legendary            tinyint(1)  not null,
    is_mythical             tinyint(1)  not null,
    constraint pokemon_species_ibfk_1 foreign key (generation_id) references generations (id),
    constraint pokemon_species_ibfk_2 foreign key (evolves_from_species_id) references pokemon_species (id),
    constraint pokemon_species_ibfk_4 foreign key (color_id) references pokemon_colors (id),
    constraint pokemon_species_ibfk_5 foreign key (shape_id) references pokemon_shapes (id),
    constraint pokemon_species_ibfk_6 foreign key (habitat_id) references pokemon_habitats (id),
    check (`is_legendary` in (0, 1)),
    check (`is_mythical` in (0, 1))
);

create table pokemon
(
    id              int auto_increment primary key,
    identifier      varchar(79) not null,
    species_id      int         null,
    height          int         not null,
    weight          int         not null,
    base_experience int         not null,
    constraint pokemon_ibfk_1 foreign key (species_id) references pokemon_species (id)
);

create index species_id
    on pokemon (species_id);


create table pokemon_moves
(
    pokemon_id             int not null,
    move_id                int not null,
    level                  int not null,
    primary key (pokemon_id, move_id, level),
    constraint pokemon_moves_ibfk_1 foreign key (pokemon_id) references pokemon (id),
    constraint pokemon_moves_ibfk_3 foreign key (move_id) references moves (id)
);

create index ix_pokemon_moves_level
    on pokemon_moves (level);

create index ix_pokemon_moves_move_id
    on pokemon_moves (move_id);

create index ix_pokemon_moves_pokemon_id
    on pokemon_moves (pokemon_id);

create table pokemon_types
(
    pokemon_id int not null,
    type_id    int not null,
    slot       int not null,
    primary key (pokemon_id, slot),
    constraint pokemon_types_ibfk_1 foreign key (pokemon_id) references pokemon (id),
    constraint pokemon_types_ibfk_2 foreign key (type_id) references types (id)
);

create index type_id
    on pokemon_types (type_id);

-- # use pokedex;
-- # DROP  table roster;
-- # drop table roster_user;
-- # drop table auth;
-- # drop table  user;

CREATE TABLE user (
    user_id INT auto_increment PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255)
);

CREATE TABLE auth (
    user_id INT PRIMARY KEY,
    password VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

-- DROP TABLE roster_user;
-- DROP TABLE roster;

CREATE TABLE roster_user (
    roster_id INT auto_increment PRIMARY KEY,
    roster_name VARCHAR(255),
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    CONSTRAINT unique_roster_name_per_user UNIQUE (user_id, roster_name)
);

CREATE TABLE roster (
    roster_id INT,
    pokemon_id INT,
    move_id INT,
    PRIMARY KEY (roster_id, pokemon_id, move_id),
    FOREIGN KEY (roster_id) REFERENCES roster_user(roster_id),
    FOREIGN KEY (pokemon_id) REFERENCES pokemon(id),
    FOREIGN KEY (move_id) REFERENCES moves(id)
);