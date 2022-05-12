-- 10. The population of the city with the smallest population in Oregon (OR). Name the column 'smallest_oregon_population'.
-- Expected answer is around 100,000
-- (1 row)
select population as smallest_oregon_population from city where state_abbreviation = 'OR' order by smallest_oregon_population limit 1;
