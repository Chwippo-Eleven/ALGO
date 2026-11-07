# Write your MySQL query statement below

select Department, Employee, Salary
from (
        select d.name Department, e.name Employee, e.salary,
            dense_rank () 
            over (partition by d.name order by e.salary desc) as rn
        from Employee e
        join Department d
        on e.departmentId = d.id
    ) as sub
where rn <=3


                                                                      
