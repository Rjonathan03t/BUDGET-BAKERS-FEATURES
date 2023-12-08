CREATE TABLE currency_exchange_rate (
    id INT PRIMARY KEY AUTO_INCREMENT,
    source_currency INT,
    destination_currency INT,
    exchange_rate DOUBLE,
    date DATE
);
