package com.example.jean.collectbeer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jean.collectbeer.Beer;
import com.example.jean.collectbeer.Helper;
import com.example.jean.collectbeer.recyclerview.BeerListAdapterRecycler;
import com.example.jean.collectbeer.R;
import com.example.jean.collectbeer.db.CervezasDbHelper;

import java.util.ArrayList;

public class MostrarActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    public static final int REQUEST=900;
    private BeerListAdapterRecycler mAdapter;
    //private FloatingActionButton fab;
    private CervezasDbHelper dbHelper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);

        Helper.initToolbar(this);
        initRecycler();
        Helper.initFAB(this, R.id.fab, intentNew);
    }

    Runnable intentNew=new Runnable() {
        @Override
        public void run() {
            Intent intent=new Intent(getApplicationContext(), NuevoActivity.class);
            startActivityForResult(intent,REQUEST);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST){
            if (resultCode==RESULT_OK) {
                Snackbar.make(getCurrentFocus(), "Guardado correctamente!", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("DATABASE1", "MOSTRAR ACTICITY");
        mAdapter.update(dbHelper.getAllData());
    }



    private void initRecycler() {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.reciclerView);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        dbHelper = CervezasDbHelper.getInstance(getApplicationContext());
        mAdapter = new BeerListAdapterRecycler(this, R.id.card_view, dbHelper.getAllData());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        Log.i("TOOLBAR","----------COLLAPSED");
                       mAdapter.setFilter(dbHelper.getAllData());
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        Log.i("TOOLBAR","----------EXPANDEEEDD");
                        return true;
                    }
                });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        prepareFilter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        prepareFilter(newText);
        return true;
    }

    private void prepareFilter(String newText){
        ArrayList<Beer> filteredModelList = filter(dbHelper.getAllData(), newText);
        mAdapter.setFilter(filteredModelList);
    }

    private ArrayList<Beer> filter(ArrayList<Beer> beers, String query) {
        query = query.toLowerCase();
        ArrayList<Beer> filteredModelList = new ArrayList<>();
        for (Beer beer : beers) {
            String nombre = beer.getNombre().toLowerCase();
            String variedad=beer.getVariedad().toLowerCase();
            String pais=beer.getPais().toLowerCase();
            if (nombre.contains(query)
                    || variedad.contains(query)
                    || pais.contains(query)) {
                filteredModelList.add(beer);
            }
        }
        return filteredModelList;
    }
}

