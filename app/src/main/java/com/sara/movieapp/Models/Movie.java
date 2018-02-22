package com.sara.movieapp.Models;

import java.io.Serializable;

/**
 * Created by sara on 2/17/2018.
 */

public class Movie implements Serializable {

    private String title, overview, rate, date, image, id;

    public Movie() {
    }

    public Movie(String title, String overview, String rate, String date, String image, String id) {
        this.title = title;
        this.overview = overview;
        this.rate = rate;
        this.date = date;
        this.image = image;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRate() {
        return rate;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(String id) {
        this.id = id;
    }
}