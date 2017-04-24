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

    private void llenarLista(){
        CervezasDbHelper dbHelper=new CervezasDbHelper(getApplicationContext());
        SQLiteDatabase db=dbHelper.getReadableDatabase();

        //columnas que mostrare
        String[] projection={
                CervezasDbContract.Cervezas.COLUMN_NAME_NOMBRE,
                CervezasDbContract.Cervezas.COLUMN_NAME_VARIEDAD,
                CervezasDbContract.Cervezas.COLUMN_NAME_ALCOHOL,
                CervezasDbContract.Cervezas.COLUMN_NAME_CALIFICACION,
                CervezasDbContract.Cervezas.COLUMN_NAME_PAIS
        };

        Cursor c=db.rawQuery(CervezasDbContract.Cervezas.SHOW_TABLE, null);
    }

}
