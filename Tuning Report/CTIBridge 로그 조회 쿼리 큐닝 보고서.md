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
1. 실제 출력되는 로우의 수는 4건밖에 안 되는데 전체 129건에 대해서 스칼라 서브쿼리를 실행한다. 이것은 시스템의 CPU를 낭비하게 된다. 따라서
기존의 *스칼라 서브쿼리를 조인으로 바꾸고 최종 4건에 대해서만 조인*하도록 수정한다.
2. server_id, load_dttm 컬럼 내림차순을 기준으로 정렬하여 첫 번째 Row만 추출하는데 두 컬럼에 대한 인덱스가 없다. 이것은 매번 모든 데이터를
PGA에 정렬하여 메모리를 낭비하게 된다. 따라서 *server_id, load_dttm 컬럼 순서로 인덱스를 생성*하여 빠르게 조인할 데이터를 추출할 수 있도록
한다.
3. isac_skeleton.cm_std_cd 테이블은 코드성 테이블인 것 같다. 따라서 해시 조인을 통해서 isac_skeleton.cm_std_cd 테이블을 Build Input으로
정하고 isac_skeleton.mn_server_resource_tbl 테이블과 조인하면 될 것 같지만 Probe Input은 DISTINCT ON 과정을 통해서 몇 건 없을 수 있다.
그렇다면 오히려 코드성 테이블을 Full Scan하여 Build Input으로 만드는 과정이 메모리를 낭비하는 비효율적인 수행일 수 있으니 NL 조인을 통해서
isac_skeleton.mn_server_resource_tbl 테이블을 isac_skeleton.cm_std_cd 테이블과 조인하도록 한다.
## 튜닝 후 SQL
하기 순서와 같이 인덱스를 생성한다.
- `mn_server_resource_tbl_server_id_idx` : server_id + load_dttm
```postgres-sql

```