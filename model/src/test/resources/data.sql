insert into tags (name) values ('tagName1');
insert into tags (name) values ('tagName3');
insert into tags (name) values ('tagName5');
insert into tags (name) values ('tagName4');
insert into tags (name) values ('tagName2');


INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date)
VALUES ('giftCertificate1', 'description1', 99.9, 1, '2020-10-20T07:20:15.156', '2020-10-20T07:20:15.156');

INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date)
VALUES ('giftCertificate3', 'description3', 100.99, 3, '2019-10-20T07:20:15.156', '2019-10-20T07:20:15.156');

INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date)
VALUES ('giftCertificate2', 'description2', 999.99, 2, '2018-10-20T07:20:15.156', '2018-10-20T07:20:15.156');


INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id)
VALUES (1, 2);

INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id)
VALUES (2, 2);

INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id)
VALUES (3, 4);

INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id)
VALUES (3, 2);

INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id)
VALUES (2, 4);

INSERT INTO users(email, password, role, name)
VALUES ('email1@email.com','$2a$10$zFdd9cCOS/A6./WuG7etC.Mhn3M.R4u2VFMVlz4cVSiNK6bLaT7S.', 'ADMIN', 'name1');

INSERT INTO users(email, password, role, name)
VALUES ('email2@email.com','$2a$10$gwocSNWrBeUOO.0.0./iveTo/uDatwYEWHadx1xMUZ.Do93yS9kc2', 'USER', 'name2');


INSERT INTO "orders" (price, purchase_time, user_id, gift_certificate_id)
VALUES (10.1, '2020-10-20T07:20:15.156', 1, 3);

INSERT INTO "orders" (price, purchase_time, user_id, gift_certificate_id)
VALUES (30.3, '2019-10-20T07:20:15.156', 1, 2);

INSERT INTO "orders" (price, purchase_time, user_id, gift_certificate_id)
VALUES (20.2, '2018-10-20T07:20:15.156', 2, 1);