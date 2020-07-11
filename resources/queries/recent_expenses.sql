-- Retrieve recent expenses
SELECT id, "when", category, description, amount::decimal
FROM expenses
ORDER BY "when" DESC, id DESC
LIMIT :count
