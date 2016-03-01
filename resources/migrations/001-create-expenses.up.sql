-- resources/migrations/001-create-expenses.up.sql
CREATE TABLE "expenses" (
       "id" SERIAL PRIMARY KEY,
       "when" DATE NOT NULL,
       "category" VARCHAR(200) NOT NULL,
       "description" VARCHAR(255) NOT NULL,
       "amount" MONEY NOT NULL);
