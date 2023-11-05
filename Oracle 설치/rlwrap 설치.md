## rlwrap 설치
rlwrap-0.45.2-1.el7.x86_64.rpm 파일을 업로드 한다. 이후 하기 명령어를 통해서 설치한다.
```shell
yum install -y rlwrap-0.45.2-1.el7.x86_64.rpm
```
oracle 계정으로 접속 후 ~/.bash_profile의 마지막에 하기 내용을 추가한다.
```text
alias sqlplus='rlwrap sqlplus'
```
이후 재로그인 또는 하기 명령어를 실행하여 바로 반영한다.
```shell
sh ~/.bash_profile
```
