-- List the available categories
SELECT DISTINCT(category)
FROM expenses
ORDER BY category ASC
LIMIT 100
