CREATE TABLE IF NOT EXISTS transfer_history (
   id_transfer_history INT PRIMARY KEY,
   id_debit_transaction INT,
   id_credit_transaction INT,
   transfer_date TIMESTAMP,
   transfer_amount DOUBLE PRECISION
);

INSERT INTO transfer_history VALUES (1,1,2,'2023-12-01',10000.0);
INSERT INTO transfer_history VALUES (2,1,3,'2023-11-10',1000.0);
INSERT INTO transfer_history VALUES (3,2,3,'2023-12-02',2000.0);
