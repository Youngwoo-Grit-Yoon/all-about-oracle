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
### 리포트 생성
tkprof 유틸리티를 사용하면 트레이스 파일을 보기 쉽게 포맷팅한 리포트를 생성해 준다.
```shell
tkprof ORCLCDB_ora_8272.trc report.txt sys=no
```
tkprof를 통해 생성된 report.txt 파일을 vi 에디터나 윈도우 노트패드로 열어서 결과를 확인하면 된다.  
/opt/oracle/diag/rdbms/orclcdb/ORCLCDB/trace 경로로 빠르게 이동하기 위해서 oracle 계정 홈 디렉토리의 .bash_profile
하단에 하기 내용을 기입한다.
```text
... 생략 ...
alias gototrace="cd /opt/oracle/diag/rdbms/orclcdb/ORCLCDB/trace"
```
