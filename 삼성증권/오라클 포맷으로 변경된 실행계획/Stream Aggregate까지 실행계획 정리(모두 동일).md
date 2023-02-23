```text
SORT AGGREGATE --> 상위 1행에 대해서 집계 수행(불필요함)
    TOP-N --> 상위 1행만 추출
        NESTED LOOP --> 2행 조인 성공
            INDEX RANGE SCAN(DATE_TIME) --> 2행 조인 시도
            TABLE ACCESS BY USER ROWID(DATE_TIME) --> 2행
```