-- 3. For all actors with the last name of "Jones", display the actor's name and movie titles 
--they appeared in. Order the results by the actor names (A-Z). (48 rows)
SELECT person.person_name, movie.title
FROM movie
JOIN movie_actor ON movie.movie_id = movie_actor.movie_id
JOIN person ON movie_actor.actor_id = person.person_id
WHERE person.person_name LIKE '% Jones'
ORDER BY person.person_name ASC;
