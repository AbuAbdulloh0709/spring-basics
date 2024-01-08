CREATE TABLE tags (
                                   id          IDENTITY        NOT NULL PRIMARY KEY,
                                   name    VARCHAR(30)     NOT NULL
    );

create table gift_certificates
(
    id                  identity           not null primary key,
    name                varchar(30)        not null,
    description         varchar(200)       not null,
    duration            integer            not null,
    price               numeric(8, 2)      not null,
    create_date         timestamp          not null,
    last_update_date    timestamp          not null
);

create table gift_certificates_tags
(
    gift_certificate_id bigint
        references gift_certificates
            on update cascade on delete cascade,
    tag_id              bigint
        references tags
            on update cascade on delete cascade
);

create table users (
                        id      identity          not null primary key,
                        name    varchar(50)     not null
);

create table orders (
                         id                      identity          not null primary key,
                         price                    numeric(8, 2)   not null,
                         purchase_time           timestamp       not null,
                         gift_certificate_id     bigint
                             references gift_certificates
                                 on update cascade on delete cascade,
                         user_id                 bigint
                             references users
                                 on update cascade on delete cascade
)