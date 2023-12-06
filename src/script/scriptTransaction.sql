CREATE TABLE IF NOT EXISTS transactions(
   id_transactions INT PRIMARY KEY,
   label VARCHAR (70),
   amount DOUBLE PRECISION,
   type VARCHAR (50) CHECK (type = 'CREDIT' OR type = 'DEBIT'),
   date TIMESTAMP
);

INSERT INTO transactions VALUES (1,'super market',15000,'DEBIT','2023-12-06 10:10:08');
INSERT INTO transactions VALUES (2,'food', 21000,'DEBIT' , '2023-12-01 12:30:40');
INSERT INTO transactions VALUES (3,'work', 100000,'CREDIT','2023-11-28 15:10:10');
