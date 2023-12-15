CREATE TABLE IF NOT EXISTS currency_value (
    id_currency_value INT PRIMARY KEY,
    id_currency_source VARCHAR(50) NOT NULL,
    id_currency_destination VARCHAR(50) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    date_effect TIMESTAMP NOT NULL
);


INSERT INTO currency_value VALUES (1, 'ID_Devise_euro', 'ID_Devise_ariary', 4500, '2023-12-06 10:10:08');
INSERT INTO currency_value VALUES (2, 'ID_Devise_euro', 'ID_Devise_ariary', 4600, '2023-12-06 10:20:05');
INSERT INTO currency_value VALUES (3, 'ID_Devise_euro', 'ID_Devise_ariary', 4550, '2023-12-06 10:50:05');
INSERT INTO currency_value VALUES (4, 'ID_Devise_euro', 'ID_Devise_ariary', 4800, '2023-12-06 10:30:05');


