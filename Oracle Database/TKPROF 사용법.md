## TKPROF 사용법
실행 계획 추적을 위해서 쿼리 실행 전 하기 쿼리를 순서대로 실행한다.
```
alter session set sql_trace = true;
```
```
alter system flush buffer_cache;
```
```
alter session set events '10046 trace name context forever, level 8';
```
trace 파일이 위치한 경로롤 이동한다.
```
cd /opt/oracle/diag/rdbms/orclcdb/ORCLCDB/trace
```
하기 tkprof 명령어를 실행하여 트레이스 파일을 읽을 수 있는 텍스트 파일로 변환한다.
```
tkprof ORCLCDB_ora_17901.trc trace.text explain=sys/oracle sys=no
```