-- Retrieve recent expenses
SELECT id, "when", category, description, amount
FROM expenses
ORDER BY "when" DESC, id DESC
LIMIT :count
