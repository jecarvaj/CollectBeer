package com.example.jean.collectbeer.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jean.collectbeer.R;
import com.example.jean.collectbeer.db.CervezasDbContract;
import com.example.jean.collectbeer.db.CervezasDbHelper;

public class ConsultaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
    }


}
