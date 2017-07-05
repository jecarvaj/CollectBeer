package com.example.jean.collectbeer.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jean.collectbeer.Beer;
import com.example.jean.collectbeer.R;
import com.example.jean.collectbeer.db.CervezasDbHelper;

import java.io.Serializable;
import java.util.ArrayList;

public class DetalleActivity extends AppCompatActivity {
     ImageView imagen;
     EditText nombre, variedad, pais, otro, alcohol;
     RatingBar ratinBar;
    String oldNombre, oldVariedad, oldPais, oldOtro;
    Float oldRatinBar, oldAlcohol;
    Beer beerOld, beerNew;
    byte[] imgBeer;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle2);

        init();


         /*-------------------------------------------------------------------------------------------
         * INICIO ARREGLO PROBLEMA DEL RATING BAR
         * PARA PODER VER LAS ESTRELLAS EN COLORES
         * ANDROID 5, API 21 Y 22
         */
        if (ratinBar.getProgressDrawable() instanceof LayerDrawable) {
            LayerDrawable stars = (LayerDrawable) ratinBar.getProgressDrawable();
            DrawableCompat.setTint(stars.getDrawable(2), ContextCompat.getColor(this, R.color.colorAccent));
        }
        else {
            // for Android 4.3, ratingBar.getProgressDrawable()=DrawableWrapperHoneycomb
            DrawableCompat.setTint(ratinBar.getProgressDrawable(), ContextCompat.getColor(this, R.color.colorAccent));
        }

        /*
         * FIN ARREGLO RATINGBAR
         --------------------------------------------------------------------------------------------*/

        imagen=(ImageView) findViewById(R.id.imageViewDetalle);

      // id=getIntent().getExtras().getInt("id");
        //CervezasDbHelper dbHelper = CervezasDbHelper.getInstance(getApplicationContext());
      //beerOld=dbHelper.getBeer(id);
        //ArrayList<Beer> myDataset = dbHelper.getAllData();
        beerOld=(Beer) getIntent().getExtras().getSerializable("currentBeer");
        imgBeer = beerOld.getUriFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgBeer, 0, imgBeer.length);

        imagen.setImageBitmap(bitmap);
        nombre.setText(beerOld.getNombre());
        variedad.setText(beerOld.getVariedad());
        pais.setText(beerOld.getPais());
        otro.setText(beerOld.getOtro());
        ratinBar.setRating(beerOld.getCalificacion());
        alcohol.setText(String.valueOf(beerOld.getAlcohol()));



        Log.i("DATABASE", "NOMBRE:: "+beerOld.getNombre()+"--> ID"+beerOld.getId()+"-->IDACTIVITY:"+id);






    }

    private void init() {

        nombre=(EditText) findViewById(R.id.etNombreDetalle);
        variedad=(EditText) findViewById(R.id.etVariedadDetalle);
        pais=(EditText) findViewById(R.id.etPaisDetalle);
        otro=(EditText) findViewById(R.id.etOtroDetalle);
        ratinBar=(RatingBar) findViewById(R.id.ratingBarDetalle);
        alcohol=(EditText) findViewById(R.id.etAlcoholDetalle);





    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            checkCambios();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    private void checkCambios() {
        init();
        oldNombre=nombre.getText().toString();
        oldVariedad=variedad.getText().toString();
        oldPais=pais.getText().toString();
        oldOtro=otro.getText().toString();
        oldRatinBar=ratinBar.getRating();
        oldAlcohol=Float.parseFloat(alcohol.getText().toString());

        beerNew=new Beer(oldNombre, oldVariedad, oldPais, oldOtro,imgBeer,oldRatinBar, oldAlcohol );

        /*if(nombre.getText().toString().equals(oldNombre)
                && variedad.getText().toString().equals(oldVariedad)
                && pais.getText().toString().equals(oldPais)
                && otro.getText().toString().equals(oldOtro)
                && ratinBar.getRating()==oldRatinBar
                && alcohol.getText().toString().equals(oldAlcohol.toString())){
            DetalleActivity.this.finish();
        }else{
            mostrarDialogo();
        }*/

        Log.i("DATABASE", "id OLD= "+beerOld.getId()+" --- NEW= "+beerNew.getId());
        Log.i("DATABASE", "FECHA OLD= "+beerOld.getFecha()+" --- NEW= "+beerNew.getFecha());
        Log.i("DATABASE", "NM OLD= "+beerOld.getNombre()+" --- NEW= "+beerNew.getNombre());
        Log.i("DATABASE", "VARIEDAd OLD= "+beerOld.getVariedad()+" --- EW= "+beerNew.getVariedad());
        Log.i("DATABASE", "PAIS OLD= "+beerOld.getPais()+" --- NEW= "+beerNew.getPais());
        Log.i("DATABASE", "ALCOHOL OLD= "+beerOld.getAlcohol()+" --- NEW= "+beerNew.getAlcohol());
        Log.i("DATABASE", "RATING OLD= "+beerOld.getCalificacion()+" --- NEW= "+beerNew.getCalificacion());
        if(beerNew.equals(beerOld)){
            DetalleActivity.this.finish();
            //Intent intent=new Intent(DetalleActivity.this, MostrarActivity.class);
            //overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            //startActivity(intent);
        }else{
            mostrarDialogo();
        }
    }

    private void mostrarDialogo(){
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setTitle("Salir");
        alertbox.setMessage("¿Guardar cambios? ");

        alertbox.setNeutralButton("Sí, guardar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        updateBeer();
                    }
                });

        alertbox.setPositiveButton("No, salir",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        DetalleActivity.this.finish();
                       // Intent intent=new Intent(DetalleActivity.this, MostrarActivity.class);
                        //overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                        //startActivity(intent);
                    }
                });




        alertbox.show();
    }

    private void updateBeer() {
            CervezasDbHelper dbHelper = CervezasDbHelper.getInstance(getApplicationContext());
            long insertado=dbHelper.update(beerNew,beerOld.getId());
            if ( insertado>0){
                Log.i("DATABASE", "UPDATEEE DATO TABLA");
                Toast.makeText(DetalleActivity.this, "Guardado correctamente", Toast.LENGTH_SHORT).show();
                 //Intent intent=new Intent(DetalleActivity.this, MostrarActivity.class);

                //startActivity(intent);
                //overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                finish();
            }else{
                Log.i("DATABASE", "ERROR UPDATE");
            }

    }

}