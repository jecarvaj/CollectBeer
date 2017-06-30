package com.example.jean.collectbeer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.example.jean.collectbeer.Beer;
import com.example.jean.collectbeer.BeerListAdapter;
import com.example.jean.collectbeer.R;
import com.example.jean.collectbeer.db.CervezasDbHelper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MostrarActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<Beer> list;
    BeerListAdapter adapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);

        gridView=(GridView) findViewById(R.id.gridBeer);
        //list = new ArrayList<>();
        CervezasDbHelper dbHelper = new CervezasDbHelper(getApplicationContext());
        list=dbHelper.getAllData();
        adapter=new BeerListAdapter(this, R.layout.beer_item2, list);
        gridView.setAdapter(adapter);


        adapter.notifyDataSetChanged();
        mostrarTodo();
    }

    private void mostrarTodo() {
        List<Beer> cervezas=list;
        for(int x=0; x<cervezas.size(); x++){
            Log.i("DATABASE", "ID      :"+String.valueOf(cervezas.get(x).getId()));
            Log.i("DATABASE", "NOMBRE  :"+cervezas.get(x).getNombre());
            Log.i("DATABASE", "OTRO    :"+cervezas.get(x).getOtro());
            Log.i("DATABASE", "FOTO    :"+cervezas.get(x).getUriFoto());
            Log.i("DATABASE", "FECHA   :"+String.valueOf(cervezas.get(x).getFecha()));
            Log.i("DATABASE", "ALCOHOL :"+String.valueOf(cervezas.get(x).getAlcohol()));
            Log.i("DATABASE", "RATING  :"+String.valueOf(cervezas.get(x).getCalificacion()));
            Log.i("DATABASE", "----------------------------------------------------------------");
        }

    }
}
