## 오라클 DB 재기동(리눅스)
오라클 엔진이 설치되어 있는 VM 리눅스로 접속하여 oracle 계정으로 로그인을 수행한다.
```text
[root@localhost ~]# su -l oracle
Last login: Sun Jan 22 18:16:32 KST 2023 on pts/0
[oracle@localhost ~]$
```
로그인 없이 sqlplus를 실행한다.
```text
[oracle@localhost ~]$ sqlplus /nolog

SQL*Plus: Release 19.0.0.0.0 - Production on 일 1월 22 18:18:56 2023
Version 19.3.0.0.0

Copyright (c) 1982, 2019, Oracle.  All rights reserved.

SQL>
```
sysdba 권한으로 sys 계정을 이용하여 로그인을 수행한다.
```text
SQL> conn sys/oracle as sysdba
휴지 인스턴스에 접속되었습니다.
```
MOUNT 및 OPEN을 진행한다.
```text
SQL> STARTUP NOMOUNT;
ORACLE 인스턴스가 시작되었습니다.

Total System Global Area 1174405120 bytes --> 인스턴스 SGA 총 크기
Fixed Size                  9134080 bytes
Variable Size             838860800 bytes
Database Buffers          318767104 bytes --> 캐시 버퍼 크기
Redo Buffers                7643136 bytes --> Redo 버퍼 크기
```
```text
SQL> ALTER DATABASE MOUNT;

데이타베이스가 변경되었습니다.
```
```text
SQL> ALTER DATABASE OPEN;

데이타베이스가 변경되었습니다.
```
sqlplus를 빠져나온 다음에 오라클 리스너를 실행한다.
```text
SQL> exit
Oracle Database 19c Enterprise Edition Release 19.0.0.0.0 - Production
Version 19.3.0.0.0에서 분리되었습니다.
```
```text
[oracle@localhost ~]$ lsnrctl start

LSNRCTL for Linux: Version 19.0.0.0.0 - Production on 22-1월 -2023 18:23:22

Copyright (c) 1991, 2019, Oracle.  All rights reserved.

시작 /opt/oracle/product/19c/dbhome_1/bin/tnslsnr: 잠시만 기다리세요...

TNSLSNR for Linux: Version 19.0.0.0.0 - Production
시스템 매개변수 파일은 /opt/oracle/product/19c/dbhome_1/network/admin/listener.ora 입니다
/opt/oracle/diag/tnslsnr/localhost/listener/alert/log.xml (으)로 로그 메시지를 기록했습니다
리스닝이: (DESCRIPTION=(ADDRESS=(PROTOCOL=tcp)(HOST=localhost)(PORT=1521)))
리스닝이: (DESCRIPTION=(ADDRESS=(PROTOCOL=ipc)(KEY=EXTPROC1521)))

(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=localhost)(PORT=1521)))에 연결되었습니다
리스너의 상태
------------------------
별칭                     LISTENER
버전                     TNSLSNR for Linux: Version 19.0.0.0.0 - Production
시작 날짜                 22-1월 -2023 18:23:24
업타임                   0 일 0 시간. 0 분. 0 초
트레이스 수준            off
보안                     ON: Local OS Authentication
SNMP                     OFF리스너 매개변수 파일   /opt/oracle/product/19c/dbhome_1/network/admin/listener.ora
리스너 로그 파일         /opt/oracle/diag/tnslsnr/localhost/listener/alert/log.xml
끝점 요약 청취 중...
  (DESCRIPTION=(ADDRESS=(PROTOCOL=tcp)(HOST=localhost)(PORT=1521)))
  (DESCRIPTION=(ADDRESS=(PROTOCOL=ipc)(KEY=EXTPROC1521)))
리스너는 서비스를 지원하지 않습니다
명령이 성공적으로 수행되었습니다
```
