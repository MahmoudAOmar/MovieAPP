package com.example.mahmoud.poop;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mahmoud on 3/25/2016.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Callback callback;
    Context context;
    ArrayList <DataOfMovie> arrayList;
    public LayoutInflater layoutInflater;

    public Adapter(Context c, ArrayList<DataOfMovie> d ){
        callback = (Callback) c;
        context=c;
        arrayList =d;
        layoutInflater =LayoutInflater.from(c);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.movie_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(Uri.parse(Variables.base + arrayList.get(position).getPoster_url())).into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;

        public ViewHolder(View itemView) {
            super(itemView);
            poster= (ImageView) itemView.findViewById(R.id.image_poster);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onMovieClicked(arrayList.get(getPosition()).getId());
                }
            });
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i = new Intent(context,DetailsActivity.class);
//                    i.putExtra("id" , arrayList.get(getPosition()).getId());
//                    context.startActivity(i);
//                }
//            });
        }

    }

    public interface Callback {

        void onMovieClicked(int id);


    }
}