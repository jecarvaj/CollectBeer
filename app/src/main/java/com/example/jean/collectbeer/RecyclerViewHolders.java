package com.example.jean.collectbeer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.IntentCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jean.collectbeer.activity.DetalleActivity;
import com.example.jean.collectbeer.activity.MostrarActivity;

/**
 * Created by Jean on 30-06-2017.
 */

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imageView;
    public RatingBar ratingBar;
    public TextView tvNombre, tvVariedad, tvPais;
    private Beer currentBeer;


    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        tvNombre=(TextView) itemView.findViewById(R.id.tvNombre);
        tvVariedad=(TextView) itemView.findViewById(R.id.tvVariedad);
        tvPais=(TextView) itemView.findViewById(R.id.tvPais);
        ratingBar=(RatingBar) itemView.findViewById(R.id.ratingBarItem);
        imageView=(ImageView) itemView.findViewById(R.id.imgBeer);

        if (ratingBar.getProgressDrawable() instanceof LayerDrawable) {
            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            DrawableCompat.setTint(stars.getDrawable(2), Color.YELLOW);
        }
        else {
            // for Android 4.3, ratingBar.getProgressDrawable()=DrawableWrapperHoneycomb
            DrawableCompat.setTint(ratingBar.getProgressDrawable(), Color.YELLOW);
        }

    }

    void bind (Beer item) {  //<--bind method allows the ViewHolder to bind to the data it is displaying
        tvNombre.setText(item.getNombre());
        tvVariedad.setText(item.getVariedad()+" "+String.valueOf(item.getAlcohol())+"°");
        tvPais.setText(item.getPais());
        ratingBar.setRating(item.getCalificacion());

        byte[] imgBeer = item.getUriFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgBeer, 0, imgBeer.length);
        imageView.setImageBitmap(bitmap);

        currentBeer = item; //<-- keep a reference to the current item
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), DetalleActivity.class);

        intent.putExtra("currentBeer",currentBeer);
        v.getContext().startActivity(intent);
        ((Activity)v.getContext()).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        //((Activity)v.getContext()).finish();

    }

    /*@Override
    public void onClick(View view) {

     /*   Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(view.getContext(), DetalleActivity.class);
        intent.putExtra("nombre","JEAN");
        view.getContext().startActivity(intent);
    } */
}
