-- 20. The titles, lengths, and release dates of the 5 longest movies in the "Action" genre. Order the movies by 
-- length (highest first), then by release date (latest first).
-- (5 rows, expected lengths around 180 - 200)
SELECT movie.title, movie.length_minutes, movie.release_date
FROM movie
JOIN movie_genre USING (movie_id)
JOIN genre using (genre_id)
WHERE genre_name = 'Action'
ORDER BY length_minutes DESC, release_date DESC
LIMIT 5;