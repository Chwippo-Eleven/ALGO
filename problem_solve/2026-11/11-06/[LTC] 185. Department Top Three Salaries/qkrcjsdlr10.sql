SELECT
    t.Department,
    t.Employee,
    t.Salary
FROM (
    SELECT
        d.name AS Department,
        e.name AS Employee,
        e.salary AS Salary,
        DENSE_RANK() OVER (
            PARTITION BY d.id
            ORDER BY e.salary DESC
        ) AS rnk
    FROM Employee e
    JOIN Department d
        ON e.departmentId = d.id
) t
WHERE t.rnk <= 3;
