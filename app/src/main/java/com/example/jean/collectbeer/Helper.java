package com.example.jean.collectbeer;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;


/**
 * Created by Jean on 05-07-2017.
 */

public class Helper {

    public Helper() {
    }

    public static void initToolbar(AppCompatActivity a){
        Toolbar myToolbar = (Toolbar) a.findViewById(R.id.my_toolbar);
        a.setSupportActionBar(myToolbar);
    }

    public static void initFAB(AppCompatActivity a, int id, final Runnable function){
        FloatingActionButton floatingButton=(FloatingActionButton)a.findViewById(id);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                function.run();
            }
        });
    }


    /*-------------------------------------------------------------------------------------------
       * FUNCION ARREGLA PROBLEMA DEL RATING BAR
       * PARA PODER VER LAS ESTRELLAS EN COLORES
       * ANDROID 5, API 21 Y 22
       */
    public static void colorRatingBar(AppCompatActivity a, RatingBar ratingBar){
        if (ratingBar.getProgressDrawable() instanceof LayerDrawable) {
            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            DrawableCompat.setTint(stars.getDrawable(2), ContextCompat.getColor(a, R.color.colorAccent));
        }
        else {
            // for Android 4.3, ratingBar.getProgressDrawable()=DrawableWrapperHoneycomb
            DrawableCompat.setTint(ratingBar.getProgressDrawable(), ContextCompat.getColor(a, R.color.colorAccent));
        }
    }


    public static void openCamera(AppCompatActivity a, int PHOTO_CODE){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        a.startActivityForResult(cameraIntent, PHOTO_CODE);
    }

}

