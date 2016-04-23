package com.example.mahmoud.poop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements Adapter.Callback {

    private boolean pane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//      MainFragment DF = new MainFragment();
//      getSupportFragmentManager().beginTransaction().replace(R.id.container, DF).commit();
//
        pane = findViewById(R.id.details) != null;

    }

    @Override
    public void onMovieClicked(int id) {


        Bundle b = new Bundle();
        b.putInt("id", id);

        if (pane)
        {
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(b);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details, detailsFragment)
                    .commit();
        }

        else {
            Intent i = new Intent(this, DetailsActivity.class);
            i.putExtra("id", id);
            startActivity(i);
        }
    }
}
