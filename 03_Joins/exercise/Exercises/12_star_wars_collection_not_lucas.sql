-- 12. The titles of the movies in the "Star Wars Collection" that weren't directed by George Lucas (5 rows)
SELECT movie.title
FROM movie
JOIN person
ON movie.director_id = person.person_id
JOIN collection 
ON movie.collection_id = collection.collection_id
WHERE collection.collection_name = 'Star Wars Collection' and person.person_name NOT LIKE 'George Lucas';
