## Python Oracle API
Python Oracle API를 이용하여 Oracle DB에 연결 및 User Call을 호출한다. 쿼리 결과 및 수행 시간을 확인할 수 있다. 실행할
쿼리문은 `main.py`가 위치한 경로에 `main.sql`을 생성하여 해당 파일에 작성한다.
### Anaconda
하기 명령어를 실행하면 Anaconda 가상 환경 목록을 확인할 수 있다.
```text
codna env list
```
하기 명령어를 실행하면 Anaconda 가상 환경을 생성할 수 있다.
```text
conda create --name "{가상 환경 이름}" python={파이썬 버전}
```
```text
conda create --name "Python Oracle API" python=3.8
```
하기 명령어를 실행하면 Anaconda 가상 환경을 실행할 수 있다.
```text
conda activate "Python Oracle API"
```
### Install python-oracledb
Python Oracle API 콘다 가상 환경에 진입한 후 하기 명령어를 실행하여 python-oracledb 라이브러리를 설치한다.
```text
pip install oracledb
```
```text
(Python Oracle API) C:\Users\YYW>pip show oracledb
Name: oracledb
Version: 1.2.2  --> 버전 확인
Summary: Python interface to Oracle Database
Home-page: https://oracle.github.io/python-oracledb
Author: Anthony Tuininga
Author-email: anthony.tuininga@oracle.com
License: Apache and/or UPL
Location: c:\users\yyw\anaconda2\envs\python oracle api\lib\site-packages
Requires: cryptography
Required-by:
```
### After writing the files to be excluded into the .gitignore file
.gitignore 파일에 제외할 파일을 작성하였으면 하기 명령어를 실행한 후 commit, push를 수행한다.
```text
git rm --cached {제외할 파일 이름}
```