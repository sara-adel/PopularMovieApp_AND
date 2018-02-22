package com.sara.movieapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.sara.movieapp.Models.Movie;

import java.util.ArrayList;

/**
 * Created by sara on 2/22/2018.
 */

public class MovieDBController {

    MovieContract movieContract;
    SQLiteDatabase db;
    Context context;

    public MovieDBController(Context con) {
        this.context = con;
        movieContract = new MovieContract(con);
    }

    public long insertNewMovie(String id, String title, String image, String overview, String rate, String date) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.FavouriteEntry.COLUMN_MOVIE_ID, id);
        values.put(MovieContract.FavouriteEntry.COLUMN_TITLE, title);
        values.put(MovieContract.FavouriteEntry.COLUMN_IMAGE, image);
        values.put(MovieContract.FavouriteEntry.COLUMN_OVERVIEW, overview);
        values.put(MovieContract.FavouriteEntry.COLUMN_RATE, rate);
        values.put(MovieContract.FavouriteEntry.COLUMN_DATE, date);

        db = movieContract.getWritableDatabase();

        long result = db.insert(MovieContract.FavouriteEntry.TABLE_NAME, null, values);
        return result;
    }

    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> movieList = new ArrayList<>();
        db = movieContract.getReadableDatabase();
        Cursor cursor = db.query(true, MovieContract.FavouriteEntry.TABLE_NAME,
                new String[]{
                        MovieContract.FavouriteEntry.COLUMN_MOVIE_ID,
                        MovieContract.FavouriteEntry.COLUMN_TITLE,
                        MovieContract.FavouriteEntry.COLUMN_IMAGE,
                        MovieContract.FavouriteEntry.COLUMN_OVERVIEW,
                        MovieContract.FavouriteEntry.COLUMN_RATE,
                        MovieContract.FavouriteEntry.COLUMN_DATE
                },
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(cursor.getString(0));
                movie.setTitle(cursor.getString(1));
                movie.setImage(cursor.getString(2));
                movie.setOverview(cursor.getString(3));
                movie.setRate(cursor.getString(4));
                movie.setDate(cursor.getString(5));

                movieList.add(movie);
            } while (cursor.moveToNext());
        }
        return movieList;
    }

    public boolean deleteMovie(String rowId) {
        boolean delete = db.delete(MovieContract.FavouriteEntry.TABLE_NAME,
                MovieContract.FavouriteEntry.COLUMN_MOVIE_ID +
                        "=" + rowId, null) > 0;
        return delete;
    }

    public Cursor searchMovies(String rowId) throws SQLException {
        db = movieContract.getReadableDatabase();

        Cursor cursor = db.query(true, MovieContract.FavouriteEntry.TABLE_NAME,
                new String[]{
                        MovieContract.FavouriteEntry.COLUMN_IMAGE},
                MovieContract.FavouriteEntry.COLUMN_MOVIE_ID + "=?",
                new String[]{rowId},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
