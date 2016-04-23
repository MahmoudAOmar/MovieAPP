package com.example.mahmoud.poop;


import android.content.Context;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class Movie_Operations {

    Realm realm;
    Context context;
    RealmResults<DataOfMovie> result;

    public Movie_Operations(Context context){

        realm= Realm.getInstance(context);
        this.context = context;
    }

    public void insert(int id , String poster_Url , String title )
    {
        realm.beginTransaction();
        DataOfMovie data = new DataOfMovie(id , poster_Url , title );
        realm.copyToRealm(data);
        realm.commitTransaction();
    }


    public ArrayList<DataOfMovie> retrive_data()
    {
        if(realm==null)
        {
            realm = Realm.getInstance(context);
        }

        ArrayList<DataOfMovie> dataOfMovies = new ArrayList<>();

        result = realm.where(DataOfMovie.class).findAll();
        for (int i = 0; i<result.size() ; i++){

            dataOfMovies.add(result.get(i));
        }
        return dataOfMovies;
    }
}
