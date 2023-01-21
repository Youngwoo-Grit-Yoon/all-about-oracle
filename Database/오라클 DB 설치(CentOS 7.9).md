## 오라클 DB 설치(CentOS 7.9)
### 1. 설치 파일 다운로드
하기 링크를 클릭하여 오라클 19c RPM 파일을 다운로드 한다.  
https://www.oracle.com/kr/database/technologies/oracle19c-linux-downloads.html  

| Download                                |
|-----------------------------------------|
| oracle-database-ee-19c-1.0-1.x86_64.rpm |

만약 로그인 화면이 나타나면 로그인을 수행한 후에 다운로드를 진행한다.
### 2. 설치 파일 업로드 
rpm 파일 다운로드 후 해당 파일을 리눅스에 업로드 하기 전에 다음 명령어를 먼저 실행한다.
```shell
mkdir -p /usr/local/setting
```
rpm 파일은 /usr/local/setting 디렉토리로 업로드 한다.
### 3. 오라클 설치에 필요한 라이브러리 추가
하기 명령어들을 실행하여 라이브러리를 설치한다.
```shell
yum -y install compat-libstdc++-33.x86_64 binutils elfutils-libelf elfutils-libelf-devel
```
```shell
yum -y install glibc glibc-common glibc-devel glibc-headers gcc gcc-c++ libaio-devel
```
```shell
yum -y install libaio libgcc libstdc++ libstdc++ make sysstat unixODBC unixODBC-devel
```
### 4. 오라클 DB 설치
/usr/local/setting 디렉토리로 이동 후 하기 명령어를 실행하여 oracle-database-preinstall-19c-1.0-1.el7.x86_64.rpm
파일을 다운로드 한다.
```shell
curl -o oracle-database-preinstall-19c-1.0-1.el7.x86_64.rpm https://yum.oracle.com/repo/OracleLinux/OL7/latest/x86_64/getPackage/oracle-database-preinstall-19c-1.0-1.el7.x86_64.rpm
```
oracle-database-preinstall-19c-1.0-1.el7.x86_64.rpm를 설치한다.
```shell
yum install -y oracle-database-preinstall-19c-1.0-1.el7.x86_64.rpm
```
oracle-database-ee-19c-1.0-1.x86_64.rpm를 설치한다.
```shell
yum install -y oracle-database-ee-19c-1.0-1.x86_64.rpm
```
### 5. oracle-database-preinstall-19c.conf 파일 확인
```shell
cat /etc/security/limits.d/oracle-database-preinstall-19c.conf
```
```text
# oracle-database-preinstall-19c setting for nofile soft limit is 1024
oracle   soft   nofile    1024

# oracle-database-preinstall-19c setting for nofile hard limit is 65536
oracle   hard   nofile    65536

# oracle-database-preinstall-19c setting for nproc soft limit is 16384
# refer orabug15971421 for more info.
oracle   soft   nproc    16384

# oracle-database-preinstall-19c setting for nproc hard limit is 16384
oracle   hard   nproc    16384

# oracle-database-preinstall-19c setting for stack soft limit is 10240KB
oracle   soft   stack    10240

# oracle-database-preinstall-19c setting for stack hard limit is 32768KB
oracle   hard   stack    32768

# oracle-database-preinstall-19c setting for memlock hard limit is maximum of 128GB on x86_64 or 3GB on x86 OR 90 % of RAM
oracle   hard   memlock    134217728

# oracle-database-preinstall-19c setting for memlock soft limit is maximum of 128GB on x86_64 or 3GB on x86 OR 90% of RAM
oracle   soft   memlock    134217728
```
### 6. oracle 계정 확인
```text
[root@localhost home]# cat /etc/passwd | grep oracle
oracle:x:54321:54321::/home/oracle:/bin/bash
```
### 7. SELINUX disabled 설정
/etc/selinux/config 파일을 열어서 `SELINUX=disabled`로 설정한다.
```shell
vi /etc/selinux/config
```
```text
# This file controls the state of SELinux on the system.
# SELINUX= can take one of these three values:
#     enforcing - SELinux security policy is enforced.
#     permissive - SELinux prints warnings instead of enforcing.
#     disabled - No SELinux policy is loaded.
SELINUX=disabled
# SELINUXTYPE= can take one of three values:
#     targeted - Targeted processes are protected,
#     minimum - Modification of targeted policy. Only selected processes are protected.
#     mls - Multi Level Security protection.
SELINUXTYPE=targeted
```
### 8. 방화벽 해제
```shell
systemctl stop firewalld
```
```shell
systemctl disable firewalld
```
### 9. 오라클 DB 생성 및 구성
하기 명령어는 root 계정으로 실행해야 한다.
```shell
/etc/init.d/oracledb_ORCLCDB-19c configure
```
