package com.sara.movieapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by sara on 2/22/2018.
 */

public class MovieContract extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "movie.db";

    public MovieContract(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //table of favourite
    public static final class FavouriteEntry implements BaseColumns {

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RATE = "rate";
        public static final String COLUMN_DATE = "date";
    }

    String CREATE_FAVOUROTE_TABLE = "CREATE TABLE " + FavouriteEntry.TABLE_NAME + " (" +
            FavouriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FavouriteEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, " +
            FavouriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
            FavouriteEntry.COLUMN_IMAGE + " TEXT, " +
            FavouriteEntry.COLUMN_OVERVIEW + " TEXT, " +
            FavouriteEntry.COLUMN_RATE + " TEXT DEFAULT SYSDATE, " +
            FavouriteEntry.COLUMN_DATE + " TEXT);";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_FAVOUROTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + FavouriteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
