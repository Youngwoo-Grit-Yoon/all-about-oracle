## Auto Trace
```oracle-sql
SQL> set autotrace on
SP2-0618: 세션 식별자를 찾을 수 없습니다. PLUSTRACE 롤이 사용으로 설정되었는지 점검하십시오
SP2-0611: STATISTICS 레포트를 사용 가능시 오류가 생겼습니다
```
v_$sessstat, v_$statname, v_$mystat 뷰에 대한 읽기 권한이 필요하다. 따라서 간단하게 DBA 권한을 부여한다.
```oracle-sql
grant dba to c##youngwoo
```
```oracle-sql
SQL> select * from emp where empno = 7900;

     EMPNO ENAME      JOB              MGR HIREDATE        SAL       COMM     DEPTNO
---------- ---------- --------- ---------- -------- ---------- ---------- ----------
      7900 JAMES      CLERK           7698 81/12/03        950                    30


Execution Plan
----------------------------------------------------------
Plan hash value: 2949544139

--------------------------------------------------------------------------------------
| Id  | Operation                   | Name   | Rows  | Bytes | Cost (%CPU)| Time     |
--------------------------------------------------------------------------------------
|   0 | SELECT STATEMENT            |        |     1 |    87 |     1   (0)| 00:00:01 |
|   1 |  TABLE ACCESS BY INDEX ROWID| EMP    |     1 |    87 |     1   (0)| 00:00:01 |
|*  2 |   INDEX UNIQUE SCAN         | PK_EMP |     1 |       |     1   (0)| 00:00:01 |
--------------------------------------------------------------------------------------

Predicate Information (identified by operation id):
---------------------------------------------------

   2 - access("EMPNO"=7900)


Statistics
----------------------------------------------------------
          0  recursive calls
          0  db block gets --> current gets
          2  consistent gets
          0  physical reads
          0  redo size
        985  bytes sent via SQL*Net to client
        387  bytes received via SQL*Net from client
          1  SQL*Net roundtrips to/from client
          0  sorts (memory)
          0  sorts (disk)
          1  rows processed
```
1. set autotrace on
2. set autotrace on explain
3. set autotrace on statistics
4. set autotrace traceonly
5. set autotrace traceonly explain
6. set autotrace traceonly statistics  

1~3은 실행 결과를 출력해야 하므로 쿼리를 실제로 수행한다.  
4, 6는 실행 통계를 보여줘야 하므로 쿼리를 실제로 수행한다.