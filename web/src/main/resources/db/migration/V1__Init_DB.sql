create table if not exists dishes
(
    id          bigint auto_increment
        primary key,
    category    varchar(255)   null,
    description varchar(2048)  null,
    sale        bit            null,
    price       decimal(19, 2) null,
    title       varchar(255)   null
);

create table if not exists users
(
    id       bigint auto_increment
        primary key,
    active   bit          not null,
    password varchar(255) null,
    username varchar(255) null
);

create table if not exists orders
(
    id      bigint auto_increment
        primary key,
    date    datetime(6) null,
    user_id bigint      null,
    constraint FK32ql8ubntj5uh44ph9659tiih
        foreign key (user_id) references users (id)
);

create table if not exists order_dish
(
    order_id bigint not null,
    dish_id  bigint not null,
    constraint FK1fevhe8ke4l3uebaotqn5ae77
        foreign key (order_id) references orders (id),
    constraint FKsxcogiw9xscinh77ixpor5apo
        foreign key (dish_id) references dishes (id)
);

create table if not exists personal_info
(
    id           bigint auto_increment
        primary key,
    email        varchar(255) null,
    first_name   varchar(255) null,
    last_name    varchar(255) null,
    phone_number varchar(255) null,
    user_id      bigint       null,
    constraint FK2ooyctbfk03w21tuk720ixnqh
        foreign key (user_id) references users (id)
);

create table if not exists addresses
(
    id               bigint auto_increment
        primary key,
    city             varchar(255) null,
    entrance_number  varchar(255) null,
    flat_number      varchar(255) null,
    house_number     varchar(255) null,
    stage_number     varchar(255) null,
    street           varchar(255) null,
    personal_info_id bigint       null,
    constraint FKghmnubj4avnwxgn4hvhnx1jnp
        foreign key (personal_info_id) references personal_info (id)
);

create table if not exists credit_cards
(
    id               bigint auto_increment
        primary key,
    cvv              varchar(255) null,
    date             varchar(255) null,
    number           varchar(255) null,
    personal_info_id bigint       null,
    constraint FKf4lqueasbu48c75vvcmk7bb3c
        foreign key (personal_info_id) references personal_info (id)
);

create table if not exists user_role
(
    user_id bigint       not null,
    roles   varchar(255) null,
    constraint FKj345gk1bovqvfame88rcx7yyx
        foreign key (user_id) references users (id)
);

