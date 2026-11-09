# Write your MySQL query statement below
SELECT  d.name as Department, e.name as Employee, e.salary as Salary
FROM    (
    SELECT  name, salary, departmentId,
    DENSE_RANK() OVER (PARTITION BY departmentId ORDER BY salary DESC) as salRank
    FROM    employee
) as e
JOIN    department as d
ON      e.departmentId = d.id
WHERE   e.salRank <= 3;
