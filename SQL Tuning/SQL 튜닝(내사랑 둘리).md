# 튜닝 전
```
select 
    t1.event_sq_id 
   ,t1.server_id 
   ,t1.unique_call_id 
   ,t1.call_event 
   ,t1.station 
   ,t1.vdn_no 
   ,t1.vdn_type 
   ,cb.cd_nm as vdn_type_nm
   ,t1.ucid 
   ,t1.callid
   ,t1.dropped
   ,to_char(t1.log_dttm, 'yyyy-mm-dd HH24:mi:ss.ms') as log_dttm
from bridge_manager.tb_bm_mc_call_tracking_event t1 
left outer join bridge_manager.tb_bm_cm_codebook cb on (cb.cd_type_id = 'RS010' and 
                                                        cb.cd_id != '00' and 
                                                        t1.vdn_type = cb.cd_id)
where t1.unique_call_id = '0000102238169639529302238'
order by t1.log_dttm asc ,t1.event_sq_id asc
```
# 튜닝 후
```
with t2 as (
select 
    t1.event_sq_id 
   ,t1.server_id 
   ,t1.unique_call_id 
   ,t1.call_event 
   ,t1.station 
   ,t1.vdn_no 
   ,t1.vdn_type 
   ,cb.cd_nm as vdn_type_nm
   ,t1.ucid 
   ,t1.callid
   ,t1.dropped
   ,to_char(t1.log_dttm, 'yyyy-mm-dd HH24:mi:ss.ms') as log_dttm
   ,(ROW_NUMBER() OVER()) AS rownum
from bridge_manager.tb_bm_mc_call_tracking_event t1 
left outer join bridge_manager.tb_bm_cm_codebook cb on (cb.cd_type_id = 'RS010' and 
                                                        cb.cd_id != '00' and 
                                                        t1.vdn_type = cb.cd_id)
where t1.unique_call_id = '0000102238169639529302238'
order by t1.server_id asc, t1.log_dttm asc ,t1.event_sq_id asc)
select *
from t2
where t2.server_id = (select t2.server_id from t2 where t2.rownum = 1);
```
# 튜닝한 SQL 설명
먼저 select 절에 `(ROW_NUMBER() OVER()) AS rownum`을 추가하였다. 그리고 order by 절에는 `t1.server_id asc`을
추가하였다. 따라서 만약 server_id 컬럼 값으로 SC-CTI-01과 SC-CTI-02가 모두 존재한다면 오름차순을 통해서 rownum의 1번은
SC-CTI-01을 가진 로우가 될 것이다. 그렇게 작성한 SQL을 WITH를 이용하여 t2라는 서브 쿼리로 생성하고, t2 서브 쿼리를 이용하여
where 절에 `t2.server_id = (select t2.server_id from t2 where t2.rownum = 1)` 조건을 이용하면 한 개의 서버
아이디에 대한 로우 데이터를 추출할 수 있다.
# 튜닝 후 SQL 결과
```text
985	SC-CTI-01	0000102238169639529302238	ServiceInitiated	1000				00001022381696395293	2238		2023-10-04 13:54:53.343	1
987	SC-CTI-01	0000102238169639529302238	Delivered(Out)	1000				00001022381696395293	2238		2023-10-04 13:54:55.047	2
988	SC-CTI-01	0000102238169639529302238	Established	1000				00001022381696395293	2238		2023-10-04 13:54:56.401	3
```