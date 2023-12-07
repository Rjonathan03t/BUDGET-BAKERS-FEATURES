CREATE TABLE IF NOT EXISTS account_transactions(
  id_account_transactions INT PRIMARY KEY,
  id_account INT NOT NULL REFERENCES account (id_account),
  id_transactions INT NOT NULL REFERENCES transactions (id_transactions)
);
