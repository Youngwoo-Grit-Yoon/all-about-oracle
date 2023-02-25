# 빠른 쿼리의 SQL Trace 분석
## Description
MS SQL이 생성한 Graphical SQL Plan의 맨 우측부터 분석을 시작하여 분할 정복 방식으로 진행하였습니다. SQL Plan이 제공하는 정보만을 이용하여
위쪽에는 추측한 SQL문을, 아래쪽에는 Text SQL Plan을 작성하였습니다. Text SQL Plan의 화살표 우측에는 테이블 탐색 결과, 인덱스 탐색 결과,
조인 결과에 따른 Row 수를 기입하였습니다. 추측한 SQL문은 Oracle Syntax로 작성하여 힌트를 통해 Graphical SQL Plan에서 보여주는 조인 방법 및
순서를 강제로 지정하였습니다.

실제로 작성된 SQL문과의 비교를 통해 Oracle Syntax에서 MS SQL Syntax로 마이그레이션 하는 방법 서치, 통계 정보를 이용한 옵티마이저의 동적인 SQL Plan 생성과
튜너의 힌트 지정을 통한 정적인 SQL Plan 생성 중 어떤 것이 더 효율적인가에 대한 고민은 주인선 선임님의 몫으로 남겨두었습니다.
## Graphical SQL Plan
![img_8.png](img_8.png)
## SQL and Plan
```oracle-sql
SELECT /*+ leading(XSM, XSCM) use_nl(XSCM PK_XST_SKILL_CODE_MST) */ XSM.SKILL_ID, XSM.SKILL_NM
FROM XST_SKILL_MST XSM, XST_SKILL_CODE_MST XSCM
WHERE XSM.SKILL_GRP_CD <> '12'
AND XSCM.CODE = XSM.SKILL_GRP_CD
AND XSCM.CLSS_CD = '1000';
```
```text
NESTED LOOP --> 109행
    TABLE FULL SCAN(XST_SKILL_MST) --> 109행
    INDEX RANGE SCAN(XST_SKILL_CODE_MST) --> 109행
```
***
```oracle-sql
SELECT /*+ leading(XSM XSCM XQM) use_nl(XSCM PK_XST_SKILL_CODE_MST) use_hash(XQM PK_XST_QUEUE_REL) */ 
XQM.RESOURCE_KEY, XSM.SKILL_ID, XSM.SKILL_NM
FROM XST_SKILL_MST XSM, XST_SKILL_CODE_MST XSCM, XST_QUEUE_REL XQM
WHERE XSM.SKILL_GRP_CD <> '12'
AND XSCM.CODE = XSM.SKILL_GRP_CD
AND XSCM.CLSS_CD = '1000'
AND XQM.SKILL_ID = XSM.SKILL_ID
AND XQM.CNFR_CLSS_CD = 'FM'
```
```text
HASH JOIN --> 110행
    NESTED LOOP --> 109행
        TABLE FULL SCAN(XST_SKILL_MST) --> 109행
        INDEX RANGE SCAN(PK_XST_SKILL_CODE_MST) --> 109행
    TABLE ACCESS BY INDEX ROWID --> 110행
        INDEX RANGE SCAN(PK_XST_QUEUE_REL) --> 228행
```
***
```oracle-sql
SELECT /*+ leading(XSM XSCM XQM) use_nl(XSCM PK_XST_SKILL_CODE_MST) use_hash(XQM PK_XST_QUEUE_REL) */ 
XQM.RESOURCE_KEY, XSM.SKILL_ID, XSM.SKILL_NM
FROM XST_SKILL_MST XSM, XST_SKILL_CODE_MST XSCM, XST_QUEUE_REL XQM
WHERE XSM.SKILL_GRP_CD <> '12'
AND XSCM.CODE = XSM.SKILL_GRP_CD
AND XSCM.CLSS_CD = '1000'
AND XQM.SKILL_ID = XSM.SKILL_ID
AND XQM.CNFR_CLSS_CD = 'FM'
UNION ALL
SELECT TSF.S_RESOURCE_KEY, TSF.S_SKILL_ID, TSF.S_SKILL_NM
FROM TXS_ST_VQ_SKILL_FIX TSF
WHERE TSF.S_SKILL_GRP_CD <> '12'
AND TSF.S_CNFR_CLSS_CD = 'FM'
```
```text
UNION-ALL --> 168행
    HASH JOIN --> 110행
        NESTED LOOP --> 109행
            TABLE FULL SCAN(XST_SKILL_MST) --> 109행
            INDEX RANGE SCAN(PK_XST_SKILL_CODE_MST) --> 109행
        TABLE ACCESS BY INDEX ROWID --> 110행
            INDEX RANGE SCAN(PK_XST_QUEUE_REL) --> 228행
    TABLE FULL SCAN(TXS_ST_VQ_SKILL_FIX) --> 58행
```
***
