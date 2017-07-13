package com.example.jean.collectbeer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jean.collectbeer.Beer;
import com.example.jean.collectbeer.activity.MostrarActivity;
import com.example.jean.collectbeer.db.CervezasDbContract;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jean on 22-04-2017.
 */



public class CervezasDbHelper extends SQLiteOpenHelper {

    private static CervezasDbHelper mInstance = null;

    public static CervezasDbHelper getInstance(Context ctx) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new CervezasDbHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    private CervezasDbHelper(Context context) {
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

    public Beer getBeer(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        if(db==null){
            return null;
        }
        Beer beer = new Beer();
        String[] args = new String[] {String.valueOf(id)};
        Cursor cursor = db.rawQuery(" SELECT * FROM "+CervezasDbContract.Cervezas.TABLE_NAME+" WHERE "+CervezasDbContract.Cervezas._ID+"=? ", args);
        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                beer.setId(Integer.parseInt(cursor.getString(0)));
                beer.setNombre(cursor.getString(1));
                beer.setFecha(Timestamp.valueOf(cursor.getString(2)));
                beer.setVariedad(cursor.getString(3));
                beer.setUriFoto(cursor.getBlob(4));
                beer.setAlcohol(Float.parseFloat(cursor.getString(5)));
                beer.setCalificacion(Float.parseFloat(cursor.getString(6)));
                beer.setOtro(cursor.getString(7));
                beer.setPais(cursor.getString(8));
                Log.i("DATABASE", "OBTENGO EL id"+beer.getId()+"NOMBRE "+beer.getNombre());
            } while(cursor.moveToNext());
        }
        cursor.close();
        return beer;
    }

    public ArrayList<Beer> getAllData() {
        ArrayList<Beer> beerList = new ArrayList<Beer>();
        String sortOrder;
        SQLiteDatabase db = this.getWritableDatabase();

        switch (MostrarActivity.SORT_OPTION){
            case "date":
                sortOrder=CervezasDbContract.Cervezas.COLUMN_NAME_FECHA+" DESC";
                break;
            case "name":
                sortOrder=CervezasDbContract.Cervezas.COLUMN_NAME_NOMBRE+" ASC";
                break;
            case "rating":
                sortOrder=CervezasDbContract.Cervezas.COLUMN_NAME_CALIFICACION+" DESC";
                break;
            default:
                sortOrder=null;
                break;
        }


        Cursor cursor = db.query(
                CervezasDbContract.Cervezas.TABLE_NAME,                     // The table to query
                null,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (cursor.moveToFirst()) {
            do {
                Beer beer = new Beer();
                beer.setId(Integer.parseInt(cursor.getString(0)));
                beer.setNombre(cursor.getString(1));
                beer.setFecha(Timestamp.valueOf(cursor.getString(2)));
                beer.setVariedad(cursor.getString(3));
                beer.setUriFoto(cursor.getBlob(4));
                if(cursor.getString(5).equals("")){
                    beer.setAlcohol(Float.parseFloat("0.0"));
                }else{
                    beer.setAlcohol(Float.parseFloat(cursor.getString(5)));
                }
                beer.setCalificacion(Float.parseFloat(cursor.getString(6)));
                beer.setOtro(cursor.getString(7));
                beer.setPais(cursor.getString(8));

                beerList.add(beer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return beerList;
    }


    public long update(Beer beer, int id){
        SQLiteDatabase db = this.getReadableDatabase();
        if(db==null){
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
        return db.update(CervezasDbContract.Cervezas.TABLE_NAME, valores, CervezasDbContract.Cervezas._ID+"="+String.valueOf(id),null);
    }

    public long delete(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        String selection = CervezasDbContract.Cervezas._ID+ " = ?";
        String[] selectionArgs = { String.valueOf(id) };
       return db.delete(CervezasDbContract.Cervezas.TABLE_NAME, selection, selectionArgs);
    }
}
