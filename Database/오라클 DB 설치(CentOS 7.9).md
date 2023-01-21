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
하기 명령어들을 순서대로 실행하여 라이브러리를 설치한다.
```shell
yum -y install compat-libstdc++-33.x86_64 binutils elfutils-libelf elfutils-libelf-devel
```
```shell
yum -y install glibc glibc-common glibc-devel glibc-headers gcc gcc-c++ libaio-devel
```
```shell
yum -y install libaio libgcc libstdc++ libstdc++ make sysstat unixODBC unixODBC-devel
```
