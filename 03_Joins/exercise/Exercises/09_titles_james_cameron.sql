-- 9. The titles of movies directed by James Cameron (6 rows)
SELECT movie.title 
FROM movie
JOIN person
ON movie.director_id = person.person_id
WHERE person.person_name = 'James Cameron';
