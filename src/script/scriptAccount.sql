CREATE TABLE IF NOT EXISTS account (
   id_account INT PRIMARY KEY,
   username VARCHAR (100),
   id_currency INT NOT NULL REFERENCES currency (id_currency),
   balance INT,
   type VARCHAR (50) CHECK (type = 'BANK' or type = 'CASH' or type = 'MOBILE MONEY')
);

INSERT INTO account VALUES (1,'Rakoto',1,10000,'BANK');
INSERT INTO account VALUES (2,'Rabe',1,20000,'BANK');
INSERT INTO account VALUES (3,'Rasolo',2,50000,'MOBILE MONEY');
INSERT INTO account VALUES (4,'Andria',2,7000,'CASH');
INSERT INTO account VALUES (5,'Zaka',1,30000,'BANK');
