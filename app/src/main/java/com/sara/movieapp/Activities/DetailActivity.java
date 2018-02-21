package com.sara.movieapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sara.movieapp.Fragments.DetailFragment;
import com.sara.movieapp.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle("MovieDetail");
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetailFragment.DETAIL_URI , getIntent().getSerializableExtra(DetailFragment.DETAIL_URI));

        DetailFragment detail = new DetailFragment();
        detail.setArguments(bundle);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail , detail)
                    .commit();
        }
    }
}
