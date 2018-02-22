package com.sara.movieapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sara.movieapp.Models.Movie;
import com.sara.movieapp.R;
import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {

    public static final String DETAIL_URI = "URI";


    Movie movieData;
    private TextView title, rate, date, overview;
    private ImageView imagePoster;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            movieData = (Movie) arguments.getSerializable(DetailFragment.DETAIL_URI);
        }

        View view = inflater.inflate(R.layout.fragment_detail, container, false);


        title = view.findViewById(R.id.title);
        imagePoster = view.findViewById(R.id.view_image);
        rate = view.findViewById(R.id.rate);
        date = view.findViewById(R.id.date);
        overview = view.findViewById(R.id.overview);

        title.setText(movieData.getTitle());
        Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w185/" + movieData.getImage())
                .into(imagePoster);
        rate.setText(movieData.getRate() + " /10");
        date.setText(movieData.getDate());
        overview.setText(movieData.getOverview());

//        Toast.makeText(getActivity()," "+movieData.getTitle()+movieData.getRate()+movieData.getDate()
//                +movieData.getOverview(),Toast.LENGTH_LONG).show();
        return view;
    }

}
