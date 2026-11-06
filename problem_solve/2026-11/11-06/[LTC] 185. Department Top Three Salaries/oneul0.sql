SELECT 
    d.name AS Department,
    e.name AS Employee,
    e.salary AS Salary
FROM Employee e

-- 1. Employee와 Department 테이블을 조인
JOIN Department d
    ON e.departmentId = d.id

-- 2. 각 직원의 급여를 기준으로 필터링
WHERE 3 > (

    -- 3. 현재 직원(e)보다 높은 급여의 종류가 몇 개인지 계산
    SELECT COUNT(DISTINCT e2.salary)

    -- 4. Employee 테이블을 다시 조회
    FROM Employee e2

    -- 5. 현재 직원(e)과 같은 부서에 속한 직원만 조회
    WHERE e2.departmentId = e.departmentId

      -- 6. 현재 직원(e)보다 급여가 높은 직원만 조회
      AND e2.salary > e.salary

    -- 7. 중복되지 않는 급여의 개수를 반환
);

-- 8. 위 조건을 만족하는 직원들의 부서명, 이름, 급여 출력