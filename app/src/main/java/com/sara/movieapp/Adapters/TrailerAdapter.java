package com.sara.movieapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sara.movieapp.Models.Trailer;
import com.sara.movieapp.R;
import com.squareup.picasso.Picasso;

/**
 * Created by sara on 2/22/2018.
 */

public class TrailerAdapter extends ArrayAdapter<Trailer> {

    public TrailerAdapter(Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trailer_item, parent, false);
        }

        ImageView image_item = convertView.findViewById(R.id.trailer_item);
        TextView name = convertView.findViewById(R.id.trailer_name);

        Picasso.with(getContext()).load("http://img.youtube.com/vi/" + getItem(position).getKey() + "/0.jpg")
                .into(image_item);
        name.setText(getItem(position).getName());

        return convertView;
    }
}