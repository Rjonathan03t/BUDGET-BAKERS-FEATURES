CREATE TABLE IF NOT EXISTS transactions(
   id_transactions INT PRIMARY KEY,
   label VARCHAR (100),
   amount DOUBLE PRECISION,
   type VARCHAR (50) CHECK (type = 'CREDIT' OR type = 'DEBIT'),
   date TIMESTAMP,
   category VARCHAR (70),
   CHECK (category IN ('restaurant','salary','supermarket'))
);


INSERT INTO transactions VALUES (1,'buy some pizza',15000,'DEBIT','2023-12-06 10:10:08','restaurant');
INSERT INTO transactions VALUES (2,'buy tacos with friedn', 21000,'DEBIT' , '2023-12-01 12:30:40','restaurant');
INSERT INTO transactions VALUES (3,'have done good work', 100000,'CREDIT','2023-11-28 15:10:10','supermarket');

UPDATE transactions
SET type = CASE
    WHEN category = 'salary' THEN 'CREDIT'
    ELSE 'DEBIT'
END;
