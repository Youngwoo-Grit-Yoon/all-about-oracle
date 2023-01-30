## SQL 트레이스
### SQL 트레이스 수집 및 파일 찾기
사전 실행 계획과 AutoTrace 결과만으로 문제점을 찾을 수 없을 때 SQL 트레이스를 이용하면 문제점을 쉽게 찾아낼 수 있다.  
아래는 현재 자신이 접속해 있는 세션에 트레이스를 설정하는 방법이다.
```oracle-sql
SQL> alter session set sql_trace = true;

세션이 변경되었습니다.

SQL> select * from emp where empno = 7900;

     EMPNO ENAME      JOB              MGR HIREDATE        SAL       COMM     DEPTNO
---------- ---------- --------- ---------- -------- ---------- ---------- ----------
      7900 JAMES      CLERK           7698 81/12/03        950                    30

SQL> select * from dual;

D
-
X

SQL> alter session set sql_trace = false;

세션이 변경되었습니다.
```
위와 같이 설정하고 아래 쿼리를 실행하면 서버 디렉토리에 생성된 트레이스 파일 이름을 확인할 수 있다.
```oracle-sql
SQL> select value
  2  from v$diag_info
  3  where name = 'Default Trace File';

VALUE
-----------------------------------------------------------------
/opt/oracle/diag/rdbms/orclcdb/ORCLCDB/trace/ORCLCDB_ora_8272.trc
```
