DROP DATABASE if exists ProfitPal;

CREATE DATABASE ProfitPal;

USE ProfitPal;

CREATE TABLE ProfitPal.Users (
  userID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(45) NOT NULL,
  password VARCHAR(45) NOT NULL
);


CREATE TABLE ProfitPal.BudgetItems (
    username VARCHAR(45) NOT NULL,
    category VARCHAR(45) NOT NULL,
    spending DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (username, category)
);

INSERT INTO ProfitPal.Users (password, email) VALUES ('test', 'test@gmail.com');
INSERT INTO ProfitPal.BudgetItems (username, category, spending) VALUES ('test', 'Grocery', 100.00);
INSERT INTO ProfitPal.BudgetItems (username, category, spending) VALUES ('test', 'Gas', 1000.00);
INSERT INTO ProfitPal.BudgetItems (username, category, spending) VALUES ('test', 'Other', 900.00);
INSERT INTO ProfitPal.BudgetItems (username, category, spending) VALUES ('test', 'Entertainment', 10.00);