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
