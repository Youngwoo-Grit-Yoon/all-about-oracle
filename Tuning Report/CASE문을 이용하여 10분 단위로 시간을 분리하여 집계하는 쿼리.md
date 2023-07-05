# 쿼리
```postgres-sql
select 
        a.cti_server_ip
        ,b.etc_2_ctnt as center_id
        ,case when b.etc_2_ctnt = 'SC' then '서을센터'
              when b.etc_2_ctnt = 'DC' then '대전센터' end as center_nm
        ,b.cd_id as server_nm
        ,b.cd_nm as host_nm
        ,a.grouped_regi_timestamp
        ,count(*) as cnt
from (select 
            t1.cti_server_ip as cti_server_ip
            ,t1.regi_timestamp as regi_timestamp
            ,case when (extract(minute from t1.regi_timestamp) / 10) between 0 and 0.9 then (0 * INTERVAL '10 minutes') + date_trunc('hour', t1.regi_timestamp)
                  when (extract(minute from t1.regi_timestamp) / 10) between 1 and 1.9 then (1 * INTERVAL '10 minutes') + date_trunc('hour', t1.regi_timestamp)
                  when (extract(minute from t1.regi_timestamp) / 10) between 2 and 2.9 then (2 * INTERVAL '10 minutes') + date_trunc('hour', t1.regi_timestamp)
                  when (extract(minute from t1.regi_timestamp) / 10) between 3 and 3.9 then (3 * INTERVAL '10 minutes') + date_trunc('hour', t1.regi_timestamp)
                  when (extract(minute from t1.regi_timestamp) / 10) between 4 and 4.9 then (4 * INTERVAL '10 minutes') + date_trunc('hour', t1.regi_timestamp)
                  when (extract(minute from t1.regi_timestamp) / 10) between 5 and 5.9 then (5 * INTERVAL '10 minutes') + date_trunc('hour', t1.regi_timestamp) end as grouped_regi_timestamp
      from isac_skeleton.vw_mc_vdn_incoming_test_tbl t1
      where t1.regi_dttm::date = current_date -8) a
inner join isac_skeleton.cm_std_cd b
on b.cd_type_id = 'CM101'::text
and b.etc_1_ctnt = a.cti_server_ip::text
group by  a.cti_server_ip, b.etc_2_ctnt, b.cd_id, b.cd_nm, a.grouped_regi_timestamp
order by a.grouped_regi_timestamp
```
regi_timestamp 컬럼으로부터 분을 추출하여 0부터 5.9 사이의 값으로 가공한다. 이후 각 구간을 대표하는 값으로 치환하여 다시 분으로 바꿔주고  
분으로 바꾼 값을 시간을 기준으로 버림한 시각에 더해준다.
# 실행 결과
![img_4.png](img_4.png)