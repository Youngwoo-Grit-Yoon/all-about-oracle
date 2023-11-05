## 오라클 DB 제거(CentOS 7.9)
#### 1. Run the deinstall script in the $ORACLE_HOME/deinstall folder.
```text
[oracle@vm ~]$ cd $ORACLE_HOME/deinstall/
[oracle@vm deinstall]$ ./deinstall
```
#### 2. Select the default options unless you need to drop or delete schemas and listeners.
#### 3. Type y to continue.
#### 4. After the script finishes, remove the oracle folder.
```text
rm -rf /home/oracle/app/oracle
```