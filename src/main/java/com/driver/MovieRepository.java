package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class MovieRepository {

    private HashMap<String, Movie> movieMap;
    private HashMap<String, Director> directorMap;
    private HashMap<String, List<String>> directorMovieMapping;

    public MovieRepository() {
        this.movieMap = new HashMap<String, Movie>();
        this.directorMap = new HashMap<String, Director>();
        this.directorMovieMapping = new HashMap<String, List<String>>();
    }

    public void saveMovie(Movie movie) {
        movieMap.put(movie.getName(), movie);
    }

    public void saveDirector(Director director) {
        directorMap.put(director.getName(), director);
    }

    public void saveMovieDirectorPair(String movie, String director) {

        if (movieMap.containsKey(movie) && directorMap.containsKey(director)) {

            List<String> currentMoviesByDirector = new ArrayList<>();

            if (directorMovieMapping.containsKey(director))
                currentMoviesByDirector = directorMovieMapping.get(director);

            currentMoviesByDirector.add(movie);

            directorMovieMapping.put(director, currentMoviesByDirector);

        }

    }

    public String getDirectorByMovieName(String name) {
        for (String director : directorMovieMapping.keySet()) {

            for (String movie : directorMovieMapping.get(director)) {
                if (movie.equals(name))
                    return director;
            }
        }
        return null;
    }


    public Movie findMovie(String movie) {
        return movieMap.get(movie);
    }

    public Director findDirector(String director) {
        return directorMap.get(director);
    }

    public List<String> findMoviesFromDirector(String director) {
        List<String> moviesList = new ArrayList<String>();
        if (directorMovieMapping.containsKey(director)) moviesList = directorMovieMapping.get(director);
        return moviesList;
    }

    public List<String> findAllMovies() {
        return new ArrayList<>(movieMap.keySet());
    }

    public void deleteDirector(String director) {

        List<String> movies = new ArrayList<String>();
        if (directorMovieMapping.containsKey(director)) {
            movies = directorMovieMapping.get(director);

            for (String movie : movies) {
                if (movieMap.containsKey(movie)) {
                    movieMap.remove(movie);
                }
            }
            directorMovieMapping.remove(director);
        }

        //4. Delete the director from directorDb.
        if (directorMap.containsKey(director)) {
            directorMap.remove(director);
        }
    }

    public void deleteAllDirector() {

        HashSet<String> moviesSet = new HashSet<String>();

        //Deleting the director's map
        directorMap = new HashMap<>();

        //Finding out all the movies by all the directors combined
        for (String director : directorMovieMapping.keySet()) {

            //Iterating in the list of movies by a director.
            for (String movie : directorMovieMapping.get(director)) {
                moviesSet.add(movie);
            }
        }
        for (String movie : moviesSet) {
            if (movieMap.containsKey(movie)) {
                movieMap.remove(movie);
            }
        }
        directorMovieMapping = new HashMap<>();
    }
}
