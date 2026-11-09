# Write your MySQL query statement below

# 부서로 분리하고 salary를 내림차순 정렬해서 유니크한 3개까지만 뽑는다는 생각

select d.name as department, k.name as employee, k.salary as salary
from department d
join (
    SELECT *
    FROM (SELECT *,
            DENSE_RANK() OVER (
                PARTITION BY departmentId -- 행을 합치지 않고 부서별로 나눌 수 있다는 것
                ORDER BY salary DESC
            ) AS sal_rank -- dense_rank 결과로 한 컬럼 추가했고 그걸 e로 만듦
            FROM Employee
         ) e
    WHERE e.sal_rank <= 3
) k on d.id = k.departmentid -- 새롭게 만들어진 테이블 K를 부서 d랑 조인해서 마무리


