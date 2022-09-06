DROP DATABASE IF EXISTS ATM_DB;
CREATE DATABASE ATM_DB;
USE ATM_DB;



CREATE TABLE T_Accounts (

accountID				INT UNSIGNED		NOT NULL PRIMARY KEY AUTO_INCREMENT,
firstName				VARCHAR(15)			NOT NULL,
lastName				VARCHAR(15)			NOT NULL,
IBAN					VARCHAR(40)			NOT NULL,
creditCardNumber		VARCHAR(20)			NOT NULL,
secretCode				VARCHAR(80)			NOT NULL DEFAULT "0000",
balance					INT					NOT NULL DEFAULT 0

);



CREATE TABLE T_Transactions (

transactionID			INT UNSIGNED 								NOT NULL PRIMARY KEY AUTO_INCREMENT,
transactionDate			DATETIME									DEFAULT NULL,
transactionType 		ENUM('DEPOSIT', 'WITHDRAWAL', 'TRANSFER')	NOT NULL,
transactionAmount		INT											NOT NULL,
sourceAccountID			INT UNSIGNED								NOT NULL,
destinationAccountID	INT UNSIGNED,

FOREIGN KEY (sourceAccountID) 		REFERENCES T_Accounts (accountID),
FOREIGN KEY (destinationAccountID) 	REFERENCES T_Accounts (accountID)

);


# Thierry Henry  : 1234
# Andrés Iniesta : 5678
INSERT INTO T_Accounts (firstName, lastName, IBAN, creditCardNumber, secretCode, balance) VALUES ('Thierry', 'HENRY', 'FR76 3000 6000 0112 3456 7890 189', '1234 5678 9012 3456', '$2a$10$7MdzYSYMBo9qr/Q9yYaaJuAxBJOlJ7CEMVXj58w1EaFV4JQeNtDg.', 500000);
INSERT INTO T_Accounts (firstName, lastName, IBAN, creditCardNumber, secretCode, balance) VALUES ('Andrés', 'INIESTA', 'ES79 2100 0813 6101 2345 6789', '5678 9012 3456 7890', '$2a$10$nCSTuZ7w7GLX2VpD8wAZDOg/Ch.cDWIuDodqJgcb9F5MgZC8KoSKe', 300000);
INSERT INTO T_Accounts (firstName, lastName, IBAN, creditCardNumber, secretCode, balance) VALUES ('Dani', 'ALVES', 'BR15 0000 0000 0000 1093 2840 814 P2', '5678 9012 3456 7890', '$2a$10$MVa8bL6rZYthL9f7IpXTU.dP9dtwsyzbBs9rNnc0mDg5uV8kQQ9s2', 200000);

INSERT INTO T_Transactions (transactionDate, transactionType, transactionAmount, sourceAccountID, destinationAccountID) VALUES (NOW(), 'DEPOSIT',		2000, 1, NULL);
INSERT INTO T_Transactions (transactionDate, transactionType, transactionAmount, sourceAccountID, destinationAccountID) VALUES (NOW(), 'WITHDRAWAL',	1000, 1, NULL);
INSERT INTO T_Transactions (transactionDate, transactionType, transactionAmount, sourceAccountID, destinationAccountID) VALUES (NOW(), 'TRANSFER',		5000, 1, 2);
INSERT INTO T_Transactions (transactionDate, transactionType, transactionAmount, sourceAccountID, destinationAccountID) VALUES (NOW(), 'TRANSFER',		3000, 3, 1);



SELECT * FROM T_Accounts;
SELECT * FROM T_Transactions;