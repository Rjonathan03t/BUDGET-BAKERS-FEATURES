CREATE TABLE IF NOT EXISTS account (
   id_account INT PRIMARY KEY,
   name VARCHAR (100) CHECK (name = 'current' OR name = 'saving'),
   id_currency INT NOT NULL REFERENCES currency (id_currency),
   balance DOUBLE PRECISION,
   type VARCHAR (50) CHECK (type = 'BANK' or type = 'CASH' or type = 'MOBILE MONEY')
);

INSERT INTO account VALUES (1,'saving',1,10000,'BANK');
INSERT INTO account VALUES (2,'current',1,20000,'BANK');
INSERT INTO account VALUES (3,'saving',2,50000,'MOBILE MONEY');
INSERT INTO account VALUES (4,'current',2,7000,'CASH');
INSERT INTO account VALUES (5,'saving',1,30000,'BANK');
