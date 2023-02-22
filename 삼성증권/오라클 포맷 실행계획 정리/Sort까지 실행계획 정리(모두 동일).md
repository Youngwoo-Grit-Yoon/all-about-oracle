```text
SORT ORDER BY --> 168행 전체 정렬
    UNION ALL --> 168행 = 110행 + 58행
        HASH JOIN --> 110행 조인 성공
            NESTED LOOP --> 109행 조인 성공
                TABLE FULL SCAN(XST_SKILL_MST) --> 115행 중 109행 조인 시도
                INDEX RANGE SCAN(XST_SKILL_CODE_MST) --> 109행 탐색
            INDEX RANGE SCAN(XST_QUEUE_REL) --> 228행 중 110행 조인 시도
        TABLE FULL SCAN(TXS_ST_VQ_SKILL_FIX) --> 58행
```