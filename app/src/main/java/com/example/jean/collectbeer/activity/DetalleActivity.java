package com.example.jean.collectbeer.activity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.jean.collectbeer.Beer;
import com.example.jean.collectbeer.Helper;
import com.example.jean.collectbeer.R;
import com.example.jean.collectbeer.db.CervezasDbHelper;

import java.io.File;

public class DetalleActivity extends AppCompatActivity {
    ImageView imagen;
    EditText nombre, variedad, pais, otro, alcohol;
    RatingBar ratinBar;
    String oldNombre, oldVariedad, oldPais, oldOtro;
    Float oldRatinBar, oldAlcohol;
    Beer beerOld, beerNew;
    Uri imgBeer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle2);
        Helper.initToolbar(this);
        init();
        Helper.colorRatingBar(this, ratinBar);

        imagen=(ImageView) findViewById(R.id.imageViewDetalle);
        beerOld=(Beer) getIntent().getExtras().getSerializable("currentBeer");

        //si beer tiene una imagen asociado la muestro en el activity
        if(beerOld.getUriFoto()!=null){
            imgBeer = Uri.parse(beerOld.getUriFoto());
            imagen.setImageURI(imgBeer);
        }

        nombre.setText(beerOld.getNombre());
        variedad.setText(beerOld.getVariedad());
        pais.setText(beerOld.getPais());
        otro.setText(beerOld.getOtro());
        ratinBar.setRating(beerOld.getCalificacion());
        alcohol.setText(String.valueOf(beerOld.getAlcohol()));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalle, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_delete:
                removeBeer(beerOld.getId());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void removeBeer(final int id) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("¿Seguro que desea eliminar "+beerOld.getNombre()+"?");
        builder.setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CervezasDbHelper db=CervezasDbHelper.getInstance(getApplicationContext());
                if(db.delete(id)>0){
                    deletePhoto();
                    finish();
                    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                    Toast.makeText(getApplicationContext(), "Eliminado correctamente!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void deletePhoto() {

        if(beerOld.getUriFoto()!=null){
            File fdelete = new File(imgBeer.getPath());
            fdelete.delete();
        }

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
        String foto;
        if(beerOld.getUriFoto()==null){
             foto=null;
        }else{
             foto=beerOld.getUriFoto();
        }
        beerNew=new Beer(oldNombre, oldVariedad, oldPais, oldOtro,foto,oldRatinBar, oldAlcohol );


        if(beerNew.equals(beerOld)){
            DetalleActivity.this.finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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
                        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                        //startActivity(intent);
                    }
                });
        alertbox.show();
    }

    private void updateBeer() {
            CervezasDbHelper dbHelper = CervezasDbHelper.getInstance(getApplicationContext());
            long insertado=dbHelper.update(beerNew,beerOld.getId());

            if ( insertado>0){
                Toast.makeText(DetalleActivity.this, "Guardado correctamente", Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

            }
    }

}
