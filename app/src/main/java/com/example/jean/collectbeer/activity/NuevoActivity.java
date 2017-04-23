package com.example.jean.collectbeer.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.jean.collectbeer.R;
import com.example.jean.collectbeer.helper.CervezasDbContract;
import com.example.jean.collectbeer.helper.CervezasDbHelper;

public class NuevoActivity extends AppCompatActivity {
    private EditText etNombre, etVariedad, etPais, etAlcohol, etOtro;
    private RatingBar ratingBar;
    private Button btGuardar;
    String nombre;
    String variedad;
    String pais;
    String otro;
    String alcohol;
    float calificacion;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        etNombre=(EditText)findViewById(R.id.etNombre);
        etVariedad=(EditText)findViewById(R.id.etVariedad);
        etPais=(EditText)findViewById(R.id.etPais);
        etAlcohol=(EditText)findViewById(R.id.etAlcohol);
        etOtro=(EditText)findViewById(R.id.etOtro);
        ratingBar=(RatingBar) findViewById(R.id.ratingBar);
        btGuardar=(Button) findViewById(R.id.btGuardar);

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre=etNombre.getText().toString();
                variedad=etVariedad.getText().toString();
                pais=etVariedad.getText().toString();
                alcohol=etAlcohol.getText().toString();
                otro=etOtro.getText().toString();
                calificacion= ratingBar.getRating();

                if(nombre.isEmpty()){
                    Toast.makeText(NuevoActivity.this, "Debe especificar por lo menos un nombre!", Toast.LENGTH_SHORT).show();
                }else{
                    agregarCerveza();
                }
            }
        });
    }

    private void agregarCerveza() {
        CervezasDbHelper dbHelper=new CervezasDbHelper(getApplicationContext());
        Log.i("DATABASESS", "Comienza getwritableatabase");
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Log.i("DATABASESS", "TERMINA getwritableatabase");

        ContentValues valores=new ContentValues();
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_NOMBRE, nombre);
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_VARIEDAD, variedad);
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_PAIS, pais);
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_ALCOHOL, alcohol);
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_OTRO, otro);
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_CALIFICACION, calificacion);


        long insertado=db.insert(CervezasDbContract.Cervezas.TABLE_NAME, null, valores);
        if(insertado>0)
            Log.i("DATABASESS", "INSERTADO DATO TABLA");
        else
            Log.i("DATABASESS", "ERROR INSETRTADO");
    }
}
