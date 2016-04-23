package com.example.mahmoud.poop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DetailsFragment extends Fragment {


    RequestQueue requestQueue;
    ImageView backdrop_path;
    ImageView poster_path;
    ImageView fav;
    TextView release_date;
    TextView vote_average;
    TextView overview;
    LinearLayout reLayout;
    LinearLayout trLayout;
    int ID;
    String poster;
    String title;
    Movie_Operations movie_operations;

    Bundle b = null;

    public DetailsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getActivity());
        b= getArguments();
        ID = b.getInt("id");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_details, container, false);
//        ID = getActivity().getIntent().getExtras().getInt("id");

        backdrop_path = (ImageView) v.findViewById(R.id.backdrop_path);
        poster_path   = (ImageView) v.findViewById(R.id.poster_path);
        fav           = (ImageView) v.findViewById(R.id.fav);
        release_date  = (TextView) v.findViewById(R.id.release_date);
        vote_average  = (TextView) v.findViewById(R.id.vote_average);
        overview      = (TextView) v.findViewById(R.id.overview);
        reLayout      = (LinearLayout) v.findViewById(R.id.relayout);
        trLayout      = (LinearLayout) v.findViewById(R.id.trlayout);
        movie_operations = new Movie_Operations(getActivity());

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fav.setImageResource(android.R.drawable.star_big_on);
                movie_operations.insert(ID , poster , title );
            }
        });
        return v;
    }


    public void MovieDetailsRequest(String mUrl)
    {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, mUrl, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject jsonObject) {

                DataOfMovie dataOfMovie = MovieDetailsResponse(jsonObject);
                Picasso.with(getActivity())
                        .load(Uri.parse("http://image.tmdb.org/t/p/w342" + dataOfMovie.getPoster_url()))
                        .into(poster_path);
                Picasso.with(getActivity())
                        .load(Uri.parse("http://image.tmdb.org/t/p/w342" + dataOfMovie.getBackdrop_url()))
                        .into(backdrop_path);
                release_date.setText(dataOfMovie.getDate());
                overview.setText(dataOfMovie.getSummary());
                vote_average.setText(dataOfMovie.getMovie_vote() + "");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }

        }
        );

        requestQueue.add(request);
    }

    public DataOfMovie MovieDetailsResponse(JSONObject jsonObject) {


        if (jsonObject == null || jsonObject.length() == 0)
        {
            return null;
        }
        else {
            try {
                int id = jsonObject.getInt(Variables.id);
                title = jsonObject.getString(Variables.title);
                poster  = jsonObject.getString(Variables.poster_url);
                String backdrop = jsonObject.getString(Variables.backdrop_url);
                String ReleaseDate = jsonObject.getString(Variables.date);
                double vote_avg = jsonObject.getDouble(Variables.movie_vote);
                String overview = jsonObject.getString(Variables.summary);

                DataOfMovie dataOfMovie  = new DataOfMovie(id , title , poster, backdrop , ReleaseDate , vote_avg , overview);
                    return dataOfMovie;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
        return null;
    }


    public void TrailerDetailsRequest(String mUrl)
    {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, mUrl, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject jsonObject) {

                if (jsonObject == null || jsonObject.length() == 0)
                {
                    return ;
                }
                else {
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            final String key = jsonObject1.getString("key");
                            String name = jsonObject1.getString("name");

                            View v = LayoutInflater.from(getActivity()).inflate(R.layout.trailer_item, null);

                            TextView title = (TextView) v.findViewById(R.id.t_title);
                            ImageView play = (ImageView) v.findViewById(R.id.play_image);

                            title.setText(name);

                            play.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse("https://www.youtube.com/watch?v=" + key));
                                    startActivity(i);
                                }
                            });

                            trLayout.addView(v);

                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }

        }
        );

        requestQueue.add(request);
    }


    public void ReviewDetailsRequest(String mUrl)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, mUrl, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject jsonObject) {

                if (jsonObject == null || jsonObject.length() == 0)
                {
                    return ;
                }
                else {
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String Sauthor = jsonObject1.getString("author");
                            String Scontent = jsonObject1.getString("content");

                            View v = LayoutInflater.from(getActivity()).inflate(R.layout.review_item, null);

                            TextView Tauthor = (TextView) v.findViewById(R.id.author);
                            TextView Tcontent = (TextView) v.findViewById(R.id.content);

                            Tauthor.setText(Sauthor);
                            Tcontent.setText(Scontent);

                            reLayout.addView(v);

                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }

        }
        );

        requestQueue.add(request);
    }

    @Override
    public void onResume() {
        super.onResume();

        TrailerDetailsRequest("http://api.themoviedb.org/3/movie/" + ID + "/videos?api_key=6291b787b5285071e3150d7fa51ccf45");
        ReviewDetailsRequest("http://api.themoviedb.org/3/movie/" + ID + "/reviews?api_key=6291b787b5285071e3150d7fa51ccf45");

        MovieDetailsRequest("http://api.themoviedb.org/3/movie/" + ID + "?api_key=6291b787b5285071e3150d7fa51ccf45");
    }
}
