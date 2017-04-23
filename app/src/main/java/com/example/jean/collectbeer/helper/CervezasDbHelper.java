package com.example.jean.collectbeer.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jean on 22-04-2017.
 */



public class CervezasDbHelper extends SQLiteOpenHelper {



    public CervezasDbHelper(Context context) {
        super(context, CervezasDbContract.DB_NAME, null, CervezasDbContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DATABASESS", "Creando base de datos");

        db.execSQL(CervezasDbContract.Cervezas.CREATE_TABLE);


        Log.i("DATABASESS", "Tabla CERVEZAS creada");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CervezasDbContract.Cervezas.DELETE_TABLE);
        Log.i("DATABASESS", "Tabla CERVEZAS BORRADADADADADAD ONUPGRADE");
        onCreate(db);

    }
}
