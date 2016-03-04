-- Create a new expense
INSERT INTO expenses
("when", category, description, amount)
VALUES (:when, :category, :description, :amount)
