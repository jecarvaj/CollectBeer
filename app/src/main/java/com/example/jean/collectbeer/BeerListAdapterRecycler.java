package com.example.jean.collectbeer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jean.collectbeer.activity.DetalleActivity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jean on 30-06-2017.
 */

public class BeerListAdapterRecycler extends RecyclerView.Adapter<RecyclerViewHolders>  {

    private ArrayList<Beer> itemList;
    private RecyclerView mRecycler;
    private Context context;
    private int layout;
    private Beer beer;


    public BeerListAdapterRecycler(Context context, int layout, ArrayList<Beer> itemList) {
        this.itemList = itemList;
        this.layout=layout;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.beer_item2, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        beer=itemList.get(position);
        holder.tvNombre.setText(beer.getNombre());
        holder.tvVariedad.setText(beer.getVariedad()+" "+String.valueOf(beer.getAlcohol())+"°");
        holder.tvPais.setText(beer.getPais());
        holder.ratingBar.setRating(beer.getCalificacion());

        byte[] imgBeer = beer.getUriFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgBeer, 0, imgBeer.length);
        holder.imageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }


}


