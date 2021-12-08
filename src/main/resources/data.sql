-- Insertion of four contacts into contact table
INSERT INTO contacts(Name, PhoneNumber, Address, Email)
VALUES('Steve Penney', '647-111-111', '1 Main st', 'amos@gmail.com');

INSERT INTO contacts(Name, PhoneNumber, Address, Email)
VALUES('Richard Dickson', '647-222-555', '255 Elwood dr', 'richard@hotmail.com');

INSERT INTO contacts(Name, PhoneNumber, Address, Email)
VALUES('LeBron James', '647-333-666', '7 McMuffin crt', 'lebron@yahoo.com');

INSERT INTO contacts(Name, PhoneNumber, Address, Email)
VALUES('Kobe Bryant', '647-444-888', '88 Alberta cres', 'kobe@gmail.com');

INSERT INTO sec_user (email, encryptedPassword, enabled)
VALUES ('amos.turner@sheridancollege.ca', '$2a$10$yKHf.14bItd9S85xWTJhTuoz3e2bwJ/YKCe8Rb/7W26ocz8ohHlka', 1);

INSERT INTO sec_user (email, encryptedPassword, enabled)
VALUES ('guest.guest@sheridancollege.ca', '$2a$10$OLWdZOshrqUFjOZC.ZuqKelNL9xVI3jfTMC5eVnKtlgpAoyBX15iq', 1);


INSERT INTO sec_role (roleName)
VALUES ('ROLE_ADMIN');

INSERT INTO sec_role (roleName)
VALUES ('ROLE_USER');



INSERT INTO user_role (userId, roleId)
VALUES (1, 1);

INSERT INTO user_role (userId, roleId)
VALUES (1, 2);

INSERT INTO user_role (userId, roleId)
VALUES (2, 2);
