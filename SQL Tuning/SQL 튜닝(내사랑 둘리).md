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