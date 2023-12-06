CREATE TABLE IF NOT EXISTS transactions_list(
   id_transactions INT PRIMARY KEY,
   label VARCHAR (70),
   amount DOUBLE PRECISION,
   type VARCHAR (50) CHECK (type = 'CREDIT' OR type = 'DEBIT'),
   date DATE
);

INSERT INTO transactions_list VALUES (1,'super market',15000,'DEBIT','2023-12-06');
INSERT INTO transactions_list VALUES (2,'food', 21000,'DEBIT' , '2023-12-01');
INSERT INTO transactions_list VALUES (3,'work', 100000,'CREDIT','2023-11-28');
