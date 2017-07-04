package com.example.jean.collectbeer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jean.collectbeer.Beer;
import com.example.jean.collectbeer.BeerListAdapterRecycler;
import com.example.jean.collectbeer.R;
import com.example.jean.collectbeer.db.CervezasDbHelper;

import java.util.ArrayList;

public class MostrarActivity extends AppCompatActivity {
/*
    GridView gridView;
    ArrayList<Beer> list;
    BeerListAdapter adapter=null;*/

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Beer> myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);


        mRecyclerView = (RecyclerView) findViewById(R.id.reciclerView);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        CervezasDbHelper dbHelper = CervezasDbHelper.getInstance(getApplicationContext());
        myDataset = dbHelper.getAllData();
        // specify an adapter (see also next example)
        mAdapter = new BeerListAdapterRecycler(this, R.id.card_view, myDataset);
        mRecyclerView.setAdapter(mAdapter);


    }
}

