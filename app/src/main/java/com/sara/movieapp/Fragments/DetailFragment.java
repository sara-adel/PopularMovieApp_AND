package com.sara.movieapp.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sara.movieapp.Adapters.ReviewAdapter;
import com.sara.movieapp.Adapters.TrailerAdapter;
import com.sara.movieapp.Database.MovieDBController;
import com.sara.movieapp.Models.Movie;
import com.sara.movieapp.Models.Review;
import com.sara.movieapp.Models.Trailer;
import com.sara.movieapp.R;
import com.sara.movieapp.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DetailFragment extends Fragment {

    public static final String DETAIL_URI = "URI";


    Movie movieData;
    TrailerAdapter trailerAdapter;
    ReviewAdapter reviewAdapter;
    MovieDBController db;

    private TextView title, rate, date, overview;
    private ImageView imagePoster;
    CheckBox favourite;
    ListView listTrailer, listReview;

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
        favourite = view.findViewById(R.id.checkBox);
        listTrailer = view.findViewById(R.id.trailers);
        listReview = view.findViewById(R.id.reviews);


        title.setText(movieData.getTitle());
        Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w185/" + movieData.getImage())
                .into(imagePoster);
        rate.setText(movieData.getRate() + " /10");
        date.setText(movieData.getDate());
        overview.setText(movieData.getOverview());

        //view data of Trailer
        trailerAdapter = new TrailerAdapter(getContext());
        FetchTrailerData fetchTrailer = new FetchTrailerData();
        fetchTrailer.execute("videos");
        listTrailer.setAdapter(trailerAdapter);

        listTrailer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Trailer trailer = trailerAdapter.getItem(position);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
                startActivity(intent);
            }
        });

        //view data of Review
        reviewAdapter = new ReviewAdapter(getContext());
        FetchReviewData fetchReview = new FetchReviewData();
        fetchReview.execute("review");
        listReview.setAdapter(reviewAdapter);

        //favourite
        db = new MovieDBController(getActivity());

        Cursor cursor = db.searchMovies(movieData.getId());
        int num = cursor.getCount();

        if (num == 1) {
            favourite.setChecked(true);
        } else {
            favourite.setChecked(false);
        }

        favourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (favourite.isChecked()) {
                    Toast.makeText(getContext(), "Favourite", Toast.LENGTH_LONG).show();
                    db.insertNewMovie(
                            movieData.getId(),
                            movieData.getTitle(),
                            movieData.getImage(),
                            movieData.getOverview(),
                            movieData.getRate(),
                            movieData.getDate()
                    );
                } else {
                    Toast.makeText(getContext(), "UnFavourite", Toast.LENGTH_LONG).show();
                    db.deleteMovie(movieData.getId());
                }
            }
        });

        return view;
    }

    /////////////////////////////////////////// fetch data of Trailer  ////////////////////////////////////////////////////

    public class FetchTrailerData extends AsyncTask<String, Void, ArrayList<Trailer>> {

        String baseurl = "https://api.themoviedb.org/3/movie/" + movieData.getId();
        final String API_STRINGS = "api_key";
        String API_VALUE = "e78b2940f343f66faef64fa2fe876545";

        @Override
        protected ArrayList<Trailer> doInBackground(String... strings) {

            HttpURLConnection urlConnection = null;
            BufferedReader bufferedReader = null;
            String jsontr = null;

            try {
                Uri uri = Uri.parse(baseurl + "/" + "videos")
                        .buildUpon()
                        .appendQueryParameter(API_STRINGS, API_VALUE)
                        .build();
                Log.e("dov", uri.toString());

                URL url = new URL(uri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }
                jsontr = stringBuffer.toString();
                Log.e("jsv", jsontr);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return GetTrailerData(jsontr);
        }

        private ArrayList<Trailer> GetTrailerData(String result) {
            Log.e("pars", result);
            ArrayList<Trailer> trailerData = new ArrayList<Trailer>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray array = (JSONArray) jsonObject.get("results");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonMovieObject = array.getJSONObject(i);
                    String data_key = jsonMovieObject.getString("key");
                    String data_name = jsonMovieObject.getString("name");

                    Trailer trailerModel = new Trailer(data_key, data_name);
                    trailerData.add(trailerModel);
                }
            } catch (JSONException e) {
                Log.e("Parsing", e.getMessage(), e);
                e.printStackTrace();
            }
            return trailerData;
        }

        @Override
        protected void onPostExecute(ArrayList<Trailer> trailers) {
            trailerAdapter.clear();
            for (Trailer trailer : trailers) {
                trailerAdapter.add(trailer);
            }
            trailerAdapter.notifyDataSetChanged();
            Utility.setListViewHeightBasedOnItems(listTrailer);
        }
    }


    /////////////////////////////////////////// fetch data of Review  ////////////////////////////////////////////////////

    public class FetchReviewData extends AsyncTask<String, Void, ArrayList<Review>> {

        String baseurl = "https://api.themoviedb.org/3/movie/" + movieData.getId();
        final String APPID = "api_key";
        String APPID_VALUE = "e78b2940f343f66faef64fa2fe876545";

        @Override
        protected ArrayList<Review> doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader bufferedReader = null;
            String jsonrev = null;
            try {
                Uri uri = Uri.parse(baseurl + "/" + "reviews")
                        .buildUpon()
                        .appendQueryParameter(APPID, APPID_VALUE).build();

                Log.e("do", uri.toString());

                URL url = new URL(uri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer st = new StringBuffer();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    st.append(line + "\n");
                }
                jsonrev = st.toString();
                Log.e("js", jsonrev);

            } catch (Exception e) {

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception e) {

                    }
                }

            }
            return getReviewData(jsonrev);
        }


        private ArrayList<Review> getReviewData(String result) {
            Log.e("pars", result);
            ArrayList<Review> reviewData = new ArrayList<Review>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray array = (JSONArray) jsonObject.get("results");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonMovieObject = array.getJSONObject(i);

                    String data_author = jsonMovieObject.getString("author");
                    String data_content = jsonMovieObject.getString("content");

                    Review reviewModel = new Review(data_author, data_content);
                    reviewData.add(reviewModel);
                }
            } catch (JSONException e) {
                Log.e("Parsing", e.getMessage(), e);
                e.printStackTrace();
            }

            return reviewData;
        }

        @Override
        protected void onPostExecute(ArrayList<Review> reviews) {

            reviewAdapter.clear();
            for (Review review : reviews) {
                reviewAdapter.add(review);
            }
            reviewAdapter.notifyDataSetChanged();
            Utility.setListViewHeightBasedOnItems(listReview);
        }
    }

}