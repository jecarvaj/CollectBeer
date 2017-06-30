package com.example.jean.collectbeer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jean.collectbeer.Beer;
import com.example.jean.collectbeer.db.CervezasDbContract;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


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



    public long addBeer(Beer beer) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db == null) {
            return 0;
        }
        ContentValues valores = new ContentValues();
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_NOMBRE, beer.getNombre());
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_VARIEDAD, beer.getVariedad());
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_PAIS, beer.getPais());
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_ALCOHOL, beer.getAlcohol());
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_OTRO, beer.getOtro());
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_FOTO, beer.getUriFoto());
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_CALIFICACION, beer.getCalificacion());
        long insertado = db.insert(CervezasDbContract.Cervezas.TABLE_NAME, null, valores);
        db.close();
        return insertado;
    }

    public ArrayList<Beer> getAllData() {
        ArrayList<Beer> beerList = new ArrayList<Beer>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + CervezasDbContract.Cervezas.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Beer beer = new Beer();
                beer.setId(Integer.parseInt(cursor.getString(0)));
                beer.setNombre(cursor.getString(1));
                beer.setFecha(Timestamp.valueOf(cursor.getString(2)));
                beer.setVariedad(cursor.getString(3));
                beer.setUriFoto(cursor.getBlob(4));
                beer.setAlcohol(Float.parseFloat(cursor.getString(5)));
                beer.setCalificacion(Float.parseFloat(cursor.getString(6)));
                beer.setOtro(cursor.getString(7));
                beer.setPais(cursor.getString(8));

                // Adding contact to list
                beerList.add(beer);
            } while (cursor.moveToNext());
        }

        // return contact list
        return beerList;
    }
}
