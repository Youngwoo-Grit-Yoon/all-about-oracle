# Pivot
```
SELECT *
FROM OLYMPIC_MEDAL_WINNERS omw ;
```
![[Pasted image 20230219201930.png]]  
Pivot은 행 데이터를 열 데이터로 바꿔준다.
```
SELECT *
FROM OLYMPIC_MEDAL_WINNERS omw
pivot (count(*) FOR medal IN ('Gold' gold, 'Silver' silver, 'Bronze' bronze))
ORDER BY OLYMPIC_YEAR ;
```
![[Pasted image 20230219202426.png]]  
```
SELECT *
FROM (
SELECT athlete, medal
FROM OLYMPIC_MEDAL_WINNERS omw
)
pivot (count(*) FOR medal IN ('Gold' gold, 'Silver' silver, 'Bronze' bronze))
ORDER BY ATHLETE
FETCH FIRST 5 ROWS ONLY ;
```
![[Pasted image 20230219203125.png]]  
```
SELECT *
FROM (
SELECT noc, medal
FROM OLYMPIC_MEDAL_WINNERS omw
)
pivot (count(*) FOR medal IN ('Gold' gold, 'Silver' silver, 'Bronze' bronze))
ORDER BY noc
FETCH FIRST 10 ROWS ONLY ;
```
![[Pasted image 20230219203248.png]]  
```
SELECT *
FROM (
SELECT noc, medal, sport, event, gender
FROM OLYMPIC_MEDAL_WINNERS
)
pivot(
count(DISTINCT sport || '#' || event || '#' || gender) medals
FOR medal IN ('Gold' gold)
);
```
![[Pasted image 20230219204730.png]]  
```
SELECT *
FROM (
SELECT noc, athlete, medal
FROM OLYMPIC_MEDAL_WINNERS
)
pivot(
listagg(athlete, ',') WITHIN GROUP (ORDER BY athlete) athletes, --> ORDER BY는 누락되면 안 됨
count(*)
FOR medal IN ('Gold' gold)
);
```
![[Pasted image 20230219205702.png]]  
listagg 사용법은 하기와 같다.
```
SELECT a.noc, listagg(a.athlete, ',') WITHIN GROUP (ORDER BY a.athlete) athletes
FROM (SELECT noc, athlete
FROM OLYMPIC_MEDAL_WINNERS
WHERE medal = 'Gold') a
GROUP BY a.noc;
```
![[Pasted image 20230219210630.png]]  
```
SELECT *
FROM (
SELECT noc, sport
FROM OLYMPIC_MEDAL_WINNERS
)
pivot (min('x') FOR sport IN (
'Archery' AS arc, 'Athletics' AS ath, 'Hockey' AS hoc,
'Judo' AS jud, 'Sailing' AS sai, 'Wrestling' AS wre
))
ORDER BY noc
FETCH FIRST 7 ROWS ONLY;
```
![[Pasted image 20230219211203.png]]  
pivot을 사용하지 않고 case문을 사용하는 방법은 아래와 같다.
```
SELECT noc,
count(CASE medal WHEN 'Gold' THEN 1 END) AS "gold_medals",
count(CASE medal WHEN 'Silver' THEN 1 END) AS "silver_medals",
count(CASE medal WHEN 'Bronze' THEN 1 END) AS "bronze_medals"
FROM OLYMPIC_MEDAL_WINNERS omw
GROUP BY noc
FETCH FIRST 10 ROWS ONLY;
```
![[Pasted image 20230219211450.png]]  
