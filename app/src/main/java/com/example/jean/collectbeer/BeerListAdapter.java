package com.example.jean.collectbeer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Jean on 27-06-2017.
 */

public class BeerListAdapter extends BaseAdapter {

    private Context contexto;
    private int layout;
    private ArrayList<Beer> beerList;


    public BeerListAdapter(Context contexto, int layout, ArrayList<Beer> beerList) {
        Log.i("PRUEBA", "BEERLISTADAPTER CONSTRUCTOR");
        this.contexto = contexto;
        this.layout = layout;
        this.beerList = beerList;
    }

    @Override
    public int getCount() {
        return beerList.size();
    }

    @Override
    public Object getItem(int position) {
        return beerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }




    private class ViewHolder{
        ImageView imageView;
        RatingBar ratingBar;
        TextView tvNombre, tvVariedad, tvPais;//, tvFecha, tvRating;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("PRUEBA", "DENTRO INICIO GETVIEW. PASO A CREAR VIEHOLDER");
        View row=convertView;
        ViewHolder holder=new ViewHolder();
        Log.i("PRUEBA", "TERMINA DE CREAR VIEWHOLDERR. PASA A IF");
        if(row==null){
           LayoutInflater inflater=(LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(layout, null);
            /*LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
            row = inflater.inflate(layout, parent, false);*/

            holder.tvNombre=(TextView) row.findViewById(R.id.tvNombre);
           // holder.tvAlcohol=(TextView) row.findViewById(R.id.tvAlcohol);
            holder.tvVariedad=(TextView) row.findViewById(R.id.tvVariedad);
            holder.tvPais=(TextView) row.findViewById(R.id.tvPais);
           // holder.tvFecha=(TextView) row.findViewById(R.id.tvFecha);
            //holder.tvRating=(TextView) row.findViewById(R.id.tvRating);
            holder.ratingBar=(RatingBar) row.findViewById(R.id.ratingBarItem);
            holder.imageView=(ImageView) row.findViewById(R.id.imgBeer);
            row.setTag(holder);
        }else{
            holder=(ViewHolder) row.getTag();
        }
            Beer beer=beerList.get(position);
            holder.tvNombre.setText(beer.getNombre());
           // holder.tvAlcohol.setText(String.valueOf(beer.getAlcohol())+"°");
           holder.tvVariedad.setText(beer.getVariedad()+" "+String.valueOf(beer.getAlcohol())+"°");
            holder.tvPais.setText(beer.getPais());
            holder.ratingBar.setRating(beer.getCalificacion());
            //holder.tvFecha.setText(String.valueOf(beer.getFecha()));
            //holder.tvRating.setText(String.valueOf(beer.getCalificacion()));


        byte[] imgBeer = beer.getUriFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgBeer, 0, imgBeer.length);
        holder.imageView.setImageBitmap(bitmap);



        return row;
    }





}
