-- 12. Create a "Bill Murray Collection" in the collection table. For the movies that have Bill Murray in them, set their 
-- collection ID to the "Bill Murray Collection". (1 row, 6 rows)
insert into collection (collection_name)
values ('Bill Murray Collection');
update movie set collection_id = (select collection_id from collection where collection_name = 'Bill Murray Collection') 
WHERE movie_id IN (SELECT movie_id FROM movie_actor JOIN person ON actor_id = person_id WHERE person_name = 'Bill Murray');