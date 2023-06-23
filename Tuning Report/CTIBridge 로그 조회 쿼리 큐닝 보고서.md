# CTIBridge 로그 조회 쿼리 튜닝 보고서
## SQL ID
vw_mn_server_resource_tbl  
## 원문 SQL
```postgres-sql
SELECT DISTINCT ON (t1.server_id)
    t1.server_resource_sq_id,
    t1.server_id,
    (SELECT s1.etc_2_ctnt
     FROM isac_skeleton.cm_std_cd s1
     WHERE s1.cd_type_id::text = 'CM101'::text AND s1.etc_1_ctnt::text = t1.server_id::text) AS center_nm,
    (SELECT s1.cd_id
     FROM isac_skeleton.cm_std_cd s1
     WHERE s1.cd_type_id::text = 'CM101'::text AND s1.etc_1_ctnt::text = t1.server_id::text) AS server_nm,
    (SELECT s1.cd_nm
     FROM isac_skeleton.cm_std_cd s1
     WHERE s1.cd_type_id::text = 'CM101'::text AND s1.etc_1_ctnt::text = t1.server_id::text) AS host_nm,
    t1.cpu_usage,
    t1.mem_usage,
    t1.load_dttm
FROM isac_skeleton.mn_server_resource_tbl t1
ORDER BY t1.server_id, t1.load_dttm DESC;
```
## 원문 SQL 실행 계획
![img.png](img.png)  
- 튜닝 전 전체 비용 : `2283.04`
- 튜닝 전 읽은 버퍼 블록 수 : `1162`개
- 튜닝 전 예상 소요 시간 : `16.051`
- 튜닝 전 예상 로우 수 : `4`건
## 원문 SQL 실행 결과
![img_1.png](img_1.png)
## 튜닝 포인트
실제 출력되는 로우의 수는 4건밖에 안 되는데 전체 129건에 대해서 스칼라 서브쿼리를 실행한다. 이것은 시스템의 CPU를 낭비하게 된다. 따라서
기존의 *스칼라 서브쿼리를 조인으로 바꾸고 최종 4건에 대해서만 조인*하도록 수정한다.
## 튜닝 후 SQL
