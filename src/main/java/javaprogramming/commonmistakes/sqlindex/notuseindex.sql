explain select * from person where NAME like '%name123' LIMIT 100
explain select * from person where NAME like 'name123%' LIMIT 100


explain select * from person where SCORE>45678
explain select * from person where SCORE>45678 and NAME like 'NAME45%'


explain select * from person where length(NAME)=7
