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
# 튜닝 후 SQL 결과
```text
985	SC-CTI-01	0000102238169639529302238	ServiceInitiated	1000				00001022381696395293	2238		2023-10-04 13:54:53.343	1
987	SC-CTI-01	0000102238169639529302238	Delivered(Out)	1000				00001022381696395293	2238		2023-10-04 13:54:55.047	2
988	SC-CTI-01	0000102238169639529302238	Established	1000				00001022381696395293	2238		2023-10-04 13:54:56.401	3
```