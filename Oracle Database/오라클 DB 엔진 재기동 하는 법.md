## 오라클 DB 재기동하는 법
오라클 엔진이 설치되어 있는 VM 리눅스로 접속하여 하기 명령어를 순서대로 실행한다.

oracle 계정으로 로그인을 수행합니다.
```
su -l oracle
```
로그인하지 않고 접속합니다.
```
sqlplus /nolog
```
sysdba 권한으로 sys 계정을 이용하여 로그인을 수행합니다.
```
conn sys/oracle as sysdba
```
MOUNT 및 OPEN 과정을 수행합니다.
```
STARTUP NOMOUNT;
```
```
ALTER DATABASE MOUNT;
```
```
ALTER DATABASE OPEN;
```
sqlplus를 빠져나와 다음 명령어를 실행하여 오라클 리스너를 실행합니다.
```
lsnrctl start
```
---
#### VM Oracle 계정 정보
ID : system  
PASSWORD : Duddn!002
