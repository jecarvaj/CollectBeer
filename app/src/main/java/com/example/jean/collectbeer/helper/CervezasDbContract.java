package com.example.jean.collectbeer.helper;

import android.provider.BaseColumns;

/**
 * Created by Jean on 22-04-2017.
 */

public final class CervezasDbContract {
    public static final int DB_VERSION=1;
    public static final String DB_NAME="collectbeer.db";
    public static final String TEXT_TYPE=" TEXT";
    public static final String REAL_TYPE=" REAL";
    public static final String COMMA_SEP=",";


    //privado, para evitar que  alguien la instancie por accidente
    private CervezasDbContract(){}

    public static class Cervezas implements BaseColumns{
        public static final String TABLE_NAME="CERVEZAS";
        public static final String COLUMN_NAME_NOMBRE="nombre";
        public static final String COLUMN_NAME_VARIEDAD="variedad";
        public static final String COLUMN_NAME_PAIS="pais";
        public static final String COLUMN_NAME_ALCOHOL="alcohol";
        public static final String COLUMN_NAME_OTRO="otro";
        public static final String COLUMN_NAME_CALIFICACION="calificacion";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_NOMBRE + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_VARIEDAD + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_ALCOHOL + REAL_TYPE + COMMA_SEP +
                COLUMN_NAME_CALIFICACION + REAL_TYPE + COMMA_SEP +
                COLUMN_NAME_OTRO + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_PAIS + TEXT_TYPE + " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    }

}
