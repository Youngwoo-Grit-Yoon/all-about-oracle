## DBMS_XPLAN 패키지
### 예상 실행계획 출력
```oracle-sql
explain plan set statement_id = 'SQL1' for 
select *
from emp e, dept d
where d.deptno = e.deptno
and e.sal >= 1000 ;
```
```text
SQL> select * from table(dbms_xplan.display('PLAN_TABLE', 'SQL1', 'BASIC'));
------------------------------------------------
| Id  | Operation                    | Name    |
------------------------------------------------
|   0 | SELECT STATEMENT             |         |
|   1 |  MERGE JOIN                  |         |
|   2 |   TABLE ACCESS BY INDEX ROWID| DEPT    |
|   3 |    INDEX FULL SCAN           | PK_DEPT |
|   4 |   SORT JOIN                  |         |
|   5 |    TABLE ACCESS FULL         | EMP     |
------------------------------------------------
```
```oracle-sql
explain plan set statement_id = 'SQL1' for 
select /*+ leading(e) use_nl(d) index(d PK_DEPT) */ *
from emp e, dept d
where d.deptno = e.deptno
and e.sal >= 1000 ;
```
```text
SQL> select * from table(dbms_xplan.display('PLAN_TABLE', 'SQL1', 'BASIC'));
------------------------------------------------
| Id  | Operation                    | Name    |
------------------------------------------------
|   0 | SELECT STATEMENT             |         |
|   1 |  NESTED LOOPS --> Batch I/O  |         |
|   2 |   NESTED LOOPS               |         |
|   3 |    TABLE ACCESS FULL         | EMP     |
|   4 |    INDEX UNIQUE SCAN         | PK_DEPT |
|   5 |   TABLE ACCESS BY INDEX ROWID| DEPT    |
------------------------------------------------
```