CREATE TABLE IF NOT EXISTS CurrencyValue (
    ID INT PRIMARY KEY,
    ID_Devise_source VARCHAR(50) NOT NULL,
    ID_Devise_destination VARCHAR(50) NOT NULL,
    valeur DOUBLE PRECISION NOT NULL,
    date_de_la_valeur DATE NOT NULL
);


INSERT INTO CurrencyValue (ID, ID_Devise_source, ID_Devise_destination, valeur, date_de_la_valeur)
VALUES
    (1, 'ID_Devise_euro', 'ID_Devise_ariary', 4500, '2023-12-05'),
    (2, 'ID_Devise_euro', 'ID_Devise_ariary', 4600, '2023-12-06');
