package com.sara.movieapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sara.movieapp.Models.Review;
import com.sara.movieapp.R;

/**
 * Created by sara on 2/22/2018.
 */

public class ReviewAdapter extends ArrayAdapter<Review> {

    public ReviewAdapter(Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_item, parent, false);
        }

        TextView text_author = convertView.findViewById(R.id.Rauthor);
        TextView text_content = convertView.findViewById(R.id.Rcontent);

        text_author.setText(getItem(position).getAuthor());
        text_content.setText(getItem(position).getContent());

        return convertView;
    }
}
