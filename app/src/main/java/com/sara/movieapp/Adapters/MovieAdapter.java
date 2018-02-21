package com.sara.movieapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.sara.movieapp.Models.Movie;
import com.sara.movieapp.R;
import com.squareup.picasso.Picasso;


/**
 * Created by sara on 2/17/2018.
 */

public class MovieAdapter extends ArrayAdapter<Movie>{

    private ImageView moviePoster;

    public MovieAdapter( Context context) {
        super(context, 0);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_poster, parent, false);
        }

        moviePoster = convertView.findViewById(R.id.poster_image);
        Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w185/" + getItem(position).getImage())
                .into(moviePoster);

        return convertView;
    }

}