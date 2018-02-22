package com.sara.movieapp.Models;

import java.io.Serializable;

/**
 * Created by sara on 2/22/2018.
 */

public class Trailer implements Serializable {
    private String key, name;

    public Trailer(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }
}
