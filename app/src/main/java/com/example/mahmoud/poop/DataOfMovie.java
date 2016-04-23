package com.example.mahmoud.poop;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mahmoud on 3/25/2016.
 */
public class DataOfMovie extends RealmObject {

    @PrimaryKey
    private int id;
    private String title;
    private String backdrop_url;
    private String date;
    private String poster_url;
    private String summary;
    private double movie_vote;

    public DataOfMovie()
    {}

    public DataOfMovie(int id, String title ,String poster_url,String backdrop_url , String date, double movie_vote ,String summary ){
        this.id=id;
        this.title=title;
        this.poster_url=poster_url;
        this.backdrop_url=backdrop_url;
        this.date=date;
        this.movie_vote=movie_vote;
        this.summary=summary;

    }

    public DataOfMovie(int id,String url,String title ){
        this.id=id;
        poster_url= url;
        this.title=title;
    }


    public double getMovie_vote() {
        return movie_vote;
    }

    public void setMovie_vote(double movie_vote) {
        this.movie_vote = movie_vote;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getBackdrop_url() {
        return backdrop_url;
    }

    public String getDate() {
        return date;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public String getSummary() {
        return summary;
    }

    public void setBackdrop_url(String backdrop_url) {
        this.backdrop_url = backdrop_url;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


}
