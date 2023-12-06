CREATE TABLE IF NOT EXISTS account (
   id_account INT PRIMARY KEY,
   name VARCHAR (100) CHECK (name = 'current' OR name = 'saving'),
   balance DOUBLE PRECISION,
   type VARCHAR (50) CHECK (type = 'BANK' or type = 'CASH' or type = 'MOBILE MONEY'),
   id_currency INT NOT NULL REFERENCES currency (id_currency),
   id_transactions INT NOT NULL REFERENCES transactions(id_transactions)
);

INSERT INTO account VALUES (1,'saving',50000,'MOBILE MONEY',1,1);
INSERT INTO account VALUES (2,'current',7000,'CASH',1,2);
INSERT INTO account VALUES (3,'saving',30000,'BANK',2,3);
