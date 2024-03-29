## 계정 생성 및 테이블 스페이스 부여(CentOS 7.9)
하기와 같이 계정을 생성한다. 12c 이상 버전부터는 생성할 계정 이름 앞에 c##을 붙인다.
```oracle-sql
SQL> create user c##youngwoo identified by password;

사용자가 생성되었습니다.
```
필요한 권한을 부여한다.
```oracle-sql
SQL> grant resource, connect to c##youngwoo;

권한이 부여되었습니다.
```
계정에 `테이블 스페이스`와 `Temp 테이블 스페이스`를 지정해주어야 한다.

- 테이블 스페이스 : 테이블, 인덱스 등과 같은 오브젝트를 저장하는 물리적인 공간이다.
- Temp 테이블 스페이스 : Sort, Join 등을 수행할 때 메모리 내부의 중간 연산 결과를 임시로 저장하는 공간이다. Temp 테이블 스페이스에 대한
I/O가 많을수록 성능은 떨어진다.

테이블 스페이스를 새로 생성한다.
```oracle-sql
SQL> create tablespace ts_youngwoo --> 테이블 스페이스 이름
  2  datafile '/opt/oracle/oradata/ORCLCDB/ts_youngwoo.dbf' --> 데이터 파일 저장 위치
  3  size 500M --> 데이터 파일 크기
  4  autoextend on --> 기본 용량 자동 관리
  5  extent management local autoallocate;

테이블스페이스가 생성되었습니다.
```
Temp 테이블 스페이스를 새로 생성한다. Temp 테이블 스페이스의 최소 용량은 1M이다. 따라서 처음 생성시 2M로 생성한 뒤 자동 증가 옵션을 사용한다.
```oracle-sql
SQL> create temporary tablespace ts_temp_youngwoo --> Temp 테이블 스페이스 이름
  2  tempfile '/opt/oracle/oradata/ORCLCDB/ts_temp_youngwoo.dbf'
  3  size 2M
  4  autoextend on
  5  extent management local;

테이블스페이스가 생성되었습니다.
```
생성한 테이블 스페이스 및 Temp 테이블 스페이스를 계정에 할당한다.
```oracle-sql
SQL> alter user c##youngwoo default tablespace ts_youngwoo;

사용자가 변경되었습니다.
```
```oracle-sql
SQL> alter user c##youngwoo temporary tablespace ts_temp_youngwoo;

사용자가 변경되었습니다.
```
테이블 생성 후 해당 테이블에 데이터를 삽입하기 위해서는 다음과 쿼리를 실행한다.
```oracle-sql
SQL> alter user c##youngwoo default tablespace ts_youngwoo quota unlimited on ts_youngwoo;

사용자가 변경되었습니다.
```