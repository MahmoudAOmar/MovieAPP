package com.example.mahmoud.poop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainFragment extends Fragment {

    RecyclerView rec;
    RequestQueue req;
    ArrayList<DataOfMovie> Movies;
    Adapter adapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<DataOfMovie> dataOfMovies;
    Movie_Operations movie_operations;


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        req= Volley.newRequestQueue(getActivity());
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_main, container, false);

        rec= (RecyclerView) v.findViewById(R.id.myrecycler);
        rec.setLayoutManager(new GridLayoutManager(getActivity(),2));
        sharedPreferences = getActivity().getSharedPreferences("db" , Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        movie_operations = new Movie_Operations(getActivity());

        return v;
    }


    private void sendrequest (String url){
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(JsonObjectRequest.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Movies = Response(jsonObject);
                if(Movies != null){
                    if(getActivity() != null){
                        adapter=new Adapter(getActivity(),Movies);
                        rec.setAdapter(adapter);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        req.add(jsonObjectRequest);
    }

    public ArrayList<DataOfMovie>Response(JSONObject jsonObject){

        Movies=new ArrayList<>();
        if (jsonObject != null && jsonObject.length() >0){
        try {
            JSONArray jsonArray=jsonObject.getJSONArray(Variables.result);

            for (int i=0 ; i<jsonArray.length();i++){
                JSONObject object= jsonArray.getJSONObject(i);
                int id =object.getInt(Variables.id);
                String posterurl= object.getString(Variables.poster_url);
                String title = object.getString(Variables.title);
                Movies.add(new DataOfMovie(id,posterurl,title));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
            return Movies;

        }
        else{ return  null;}

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.option_menu , menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.pop)
        {
            editor.putInt("View",0);
            editor.apply();

            sendrequest(Variables.Popular);

        }
        else if(id == R.id.rate)
        {
            editor.putInt("View",1);
            editor.apply();

            sendrequest(Variables.MostRate);
        }

        else if(id == R.id.fav)
        {
            editor.putInt("View",2);
            editor.apply();

            dataOfMovies = movie_operations.retrive_data();
            rec.setAdapter(new Adapter(getActivity() , dataOfMovies));

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();

        int x = sharedPreferences.getInt("View",0);
        if (x ==0)
        {
            sendrequest(Variables.Popular);
        }
        else if (x == 1)
        {
            sendrequest(Variables.MostRate);
        }
        else
        {
            dataOfMovies = movie_operations.retrive_data();
            rec.setAdapter(new Adapter(getActivity(), dataOfMovies));
        }

    }
}
