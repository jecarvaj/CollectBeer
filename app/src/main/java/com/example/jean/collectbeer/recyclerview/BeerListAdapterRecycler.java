package com.example.jean.collectbeer.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jean.collectbeer.Beer;
import com.example.jean.collectbeer.R;
import com.example.jean.collectbeer.activity.MostrarActivity;

import java.util.ArrayList;

/**
 * Created by Jean on 30-06-2017.
 */

public class BeerListAdapterRecycler extends RecyclerView.Adapter<RecyclerViewHolders>  {

    private ArrayList<Beer> itemList, itemListCopy;
    private RecyclerView mRecycler;
    private Context context;
    private int layout;
    private Beer beer;


    public BeerListAdapterRecycler(Context context, int layout, ArrayList<Beer> itemList) {
        this.itemList = itemList;
        this.layout=layout;
        this.context = context;

    }

    public void setFilter(ArrayList<Beer> countryModels) {
        itemList = new ArrayList<>();
        itemList.addAll(countryModels);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(MostrarActivity.LAYOUT_ITEM, parent, false);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        beer=itemList.get(position);
       /* holder.tvNombre.setText(beer.getNombre());
        holder.tvVariedad.setText(beer.getVariedad()+" "+String.valueOf(beer.getAlcohol())+"°");
        holder.tvPais.setText(beer.getPais());
        holder.ratingBar.setRating(beer.getCalificacion());

        byte[] imgBeer = beer.getUriFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgBeer, 0, imgBeer.length);
        holder.imageView.setImageBitmap(bitmap);
*/
        holder.bind(beer);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
/*
    public void setFilter(List<Beer> beers) {
        itemList = new ArrayList<>();
        itemList.addAll(beers);
        notifyDataSetChanged();
    }
*/

    public void update(ArrayList<Beer> datas){
        //Log.i("DATABASE", "HAGO UPDATE DE "+datas.get(10).getNombre());
        itemList.clear();
       // itemList=new List<>();
        itemList.addAll(datas);
        this.notifyDataSetChanged();
    }


}


