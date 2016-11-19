
CREATE TABLE AUDITORIUM (
    id LONG NOT NULL AUTO_INCREMENT,
    sits INTEGER,
    name VARCHAR,
    PRIMARY KEY (id)
);

CREATE TABLE AUDITORIUM_VIP_SITS (
    auditorium_id LONG NOT NULL,
    sit_number INTEGER NOT NULL,
    PRIMARY KEY (auditorium_id, sit_number)
);

CREATE TABLE EVENT (
    id LONG NOT NULL AUTO_INCREMENT,
    name VARCHAR NOT NULL,
    eventRating VARCHAR NOT NULL,
    price DOUBLE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE EVENT_SHOW (
    event_id LONG,
    show_id LONG,
    PRIMARY KEY (event_id, show_id)
);

CREATE TABLE SHOW (
    id LONG NOT NULL AUTO_INCREMENT,
    auditorium LONG NOT NULL,
    start LONG NOT NULL,
    end LONG NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE TICKET (
    id LONG NOT NULL AUTO_INCREMENT,
    event_id LONG NOT NULL,
    show_id LONG NOT NULL,
    price DOUBLE,
    sit_number INTEGER,
    PRIMARY KEY(id)
);

CREATE TABLE USERS (
    id LONG NOT NULL AUTO_INCREMENT,
    email VARCHAR UNIQUE NOT NULL,
    birthday LONG,
    user_role VARCHAR,
    password_hash VARCHAR,
    PRIMARY KEY(id)
);

CREATE TABLE USER_TICKETS (
    user_id LONG NOT NULL,
    ticket_id LONG NOT NULL,
    PRIMARY KEY(user_id, ticket_id)
);

CREATE TABLE COUNTER (
    counter_name VARCHAR NOT NULL,
    key VARCHAR NOT NULL,
    counter INTEGER DEFAULT 1,
    PRIMARY KEY(counter_name, key)
);

INSERT INTO AUDITORIUM VALUES(1, 30, 'red');
INSERT INTO AUDITORIUM_VIP_SITS VALUES(1, 1);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(1, 2);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(1, 3);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(1, 4);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(1, 5);

INSERT INTO AUDITORIUM VALUES(2, 40, 'green');
INSERT INTO AUDITORIUM_VIP_SITS VALUES(2, 1);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(2, 2);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(2, 3);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(2, 4);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(2, 5);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(2, 6);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(2, 7);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(2, 8);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(2, 9);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(2, 10);

INSERT INTO AUDITORIUM VALUES(3, 20, 'blue');
INSERT INTO AUDITORIUM_VIP_SITS VALUES(3, 1);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(3, 2);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(3, 3);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(3, 4);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(3, 5);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(3, 6);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(3, 7);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(3, 8);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(3, 9);
INSERT INTO AUDITORIUM_VIP_SITS VALUES(3, 10);

INSERT INTO SHOW VALUES(1, 1, 123123123, 4125330000000);
INSERT INTO SHOW VALUES(2, 1, 124123123, 4125330000000);
INSERT INTO SHOW VALUES(3, 2, 124123123, 4125330000000);
INSERT INTO SHOW VALUES(4, 3, 124123123, 4125330000000);
INSERT INTO SHOW VALUES(5, 1, 125123123, 4125330000000);

INSERT INTO EVENT VALUES(1, 'Ghosts', 'LOW', 1500);
INSERT INTO EVENT VALUES(2, 'Lost and found', 'HIGH', 3000);
INSERT INTO EVENT VALUES(3, 'Earth', 'HIGH', 3500);
INSERT INTO EVENT VALUES(4, 'Star track', 'MEDIUM', 2000);

INSERT INTO EVENT_SHOW VALUES(1, 1);
INSERT INTO EVENT_SHOW VALUES(2, 2);
INSERT INTO EVENT_SHOW VALUES(2, 5);
INSERT INTO EVENT_SHOW VALUES(3, 4);
INSERT INTO EVENT_SHOW VALUES(4, 3);

INSERT INTO TICKET VALUES(1, 1, 1, 1500, 10);
INSERT INTO TICKET VALUES(2, 2, 5, 1500, 15);
INSERT INTO TICKET VALUES(3, 2, 5, 1500, 16);
INSERT INTO TICKET VALUES(4, 2, 5, 1500, 17);
INSERT INTO TICKET VALUES(5, 2, 5, 1500, 18);
INSERT INTO TICKET VALUES(6, 2, 5, 1500, 19);
INSERT INTO TICKET VALUES(7, 2, 5, 1500, 20);
INSERT INTO TICKET VALUES(8, 2, 5, 1500, 21);
INSERT INTO TICKET VALUES(9, 2, 5, 1500, 22);
INSERT INTO TICKET VALUES(10, 2, 5, 1500, 23);
INSERT INTO TICKET VALUES(11, 2, 5, 1500, 24);
INSERT INTO TICKET VALUES(12, 2, 5, 1500, 25);
INSERT INTO TICKET VALUES(13, 2, 5, 1500, 26);

INSERT INTO USERS VALUES(1, 'qweqwe@qwe.qwe', 123123123, 'ADMIN', '$2a$10$tI7teJ7R5ir29uTKeA/H0eIaViIZVUs0Z3WJVqIPgbnv5DlpqWtfC');
INSERT INTO USERS VALUES(2, 'asdasd@qwe.qwe', 123123123, 'REGISTERED', '$2a$10$tI7teJ7R5ir29uTKeA/H0eIaViIZVUs0Z3WJVqIPgbnv5DlpqWtfC');
INSERT INTO USERS VALUES(3, 'zxczxc@qwe.qwe', 123123123, 'BOOKING_MANAGER', '$2a$10$tI7teJ7R5ir29uTKeA/H0eIaViIZVUs0Z3WJVqIPgbnv5DlpqWtfC');

INSERT INTO USER_TICKETS VALUES(1, 1);
INSERT INTO USER_TICKETS VALUES(2, 2);
INSERT INTO USER_TICKETS VALUES(2, 3);
INSERT INTO USER_TICKETS VALUES(2, 4);
INSERT INTO USER_TICKETS VALUES(2, 5);
INSERT INTO USER_TICKETS VALUES(2, 6);
INSERT INTO USER_TICKETS VALUES(2, 7);
INSERT INTO USER_TICKETS VALUES(2, 8);
INSERT INTO USER_TICKETS VALUES(2, 9);
INSERT INTO USER_TICKETS VALUES(2, 10);