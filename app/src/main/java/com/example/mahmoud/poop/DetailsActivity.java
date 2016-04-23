package com.example.mahmoud.poop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
//        DetailsFragment DF = new DetailsFragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, DF).commit();

        Bundle b = new Bundle();
        b.putInt("id" , getIntent().getExtras().getInt("id"));
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(b);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.details, detailsFragment)
                .commit();
    }
}
