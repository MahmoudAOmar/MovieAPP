package com.example.mahmoud.poop;

/**
 * Created by mahmoud on 3/25/2016.
 */
public class Variables {

    static String key ="392302ba16f8654836907c24c55d8fb1";
    static String Api_key = "http://api.themoviedb.org/3/discover/movie?api_key=";
    static String Popularity ="&sort_by=popularity.desc";
    static String heigest = "&sort_by=vote_average.desc";
    public static final String MostRate= Api_key+key+heigest;
    public static final String base ="http://image.tmdb.org/t/p/w342";
    public static final String movie="http://api.themoviedb.org/3/movie/";
    public static final String Popular = Api_key+key+Popularity;
    public static final String result = "results";
    public static final String title = "title";
    public static final String id = "id";
    public static final String backdrop_url = "backdrop_path";
    public static final String date = "release_date";
    public static final String poster_url = "poster_path";
    public static final String summary = "overview";
    public static final String movie_vote="vote_average";
}
