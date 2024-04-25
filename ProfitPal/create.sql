DROP DATABASE if exists ProfitPal;

CREATE DATABASE ProfitPal;

CREATE TABLE ProfitPal.Users (
    username VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    email VARCHAR(45) NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE ProfitPal.BudgetItems (
    username VARCHAR(45) NOT NULL,
    category VARCHAR(45) NOT NULL,
    spending DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (username, category)
);