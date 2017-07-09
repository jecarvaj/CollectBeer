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
    private CervezasDbHelper dbHelper;
    public static int NUM_ROW=2;
    public static int LAYOUT_ITEM=R.layout.beer_item_grid;
    MenuItem itemRow1;//=menu.findItem(R.id.action_row_1);
    MenuItem itemRow2;//=menu.findItem(R.id.action_row_2);
    MenuItem itemRow3, itemSettings, itemView, itemFilter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);

        Helper.initToolbar(this);
        initRecycler(NUM_ROW);
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



    private void initRecycler(int num_rows) {
       int card_view=R.id.card_view;
        LAYOUT_ITEM=R.layout.beer_item_grid;
        if(num_rows==1){
            card_view=R.id.card_view_list;
            LAYOUT_ITEM=R.layout.beer_item_list;
        }

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.reciclerView);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, num_rows);
        mRecyclerView.setLayoutManager(mLayoutManager);

        dbHelper = CervezasDbHelper.getInstance(getApplicationContext());
        mAdapter = new BeerListAdapterRecycler(this, card_view, dbHelper.getAllData());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        final MenuItem itemSearch = menu.findItem(R.id.action_search);
        itemRow1=menu.findItem(R.id.action_row_1);
        itemRow2=menu.findItem(R.id.action_row_2);
        itemRow3=menu.findItem(R.id.action_row_3);
        itemSettings=menu.findItem(R.id.action_settings);
        //itemView=menu.findItem(R.id.action_view);
        //itemFilter=menu.findItem(R.id.action_filter);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(itemSearch);
        searchView.setOnQueryTextListener(this);


        MenuItemCompat.setOnActionExpandListener(itemSearch,
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
           case R.id.action_row_1:
                setRow(1);
                return true;
            case R.id.action_row_2:
                setRow(2);
                return true;
            case R.id.action_row_3:
                setRow(3);
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setRow(int i) {
        Toast.makeText(this, i+" columna", Toast.LENGTH_SHORT).show();
        switch (i){
            case 1:
                itemRow1.setChecked(true);
                itemRow2.setChecked(false);
                itemRow3.setChecked(false);
                break;
            case 2:
                itemRow1.setChecked(false);
                itemRow2.setChecked(true);
                itemRow3.setChecked(false);
                break;
            case 3:
                itemRow1.setChecked(false);
                itemRow2.setChecked(false);
                itemRow3.setChecked(true);
                break;
        }
        initRecycler(i);
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

