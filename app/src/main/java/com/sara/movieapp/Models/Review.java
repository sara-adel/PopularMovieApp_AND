package com.sara.movieapp.Models;

import java.io.Serializable;

/**
 * Created by sara on 2/22/2018.
 */

public class Review implements Serializable {
    private String author, content;

    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
