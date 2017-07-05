package com.example.jean.collectbeer;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * Created by Jean on 27-06-2017.
 */

public class Beer implements Serializable{
    private int id;
    private Timestamp fecha;
    private String nombre, variedad,  pais, otro;
    private byte[] uriFoto;
    private Float calificacion, alcohol;

    public Beer() {
    }

    public Beer(String nombre, String variedad, String pais, String otro, byte[] uriFoto, Float calificacion, Float alcohol) {
        this.nombre = nombre;
        this.variedad = variedad;
        this.pais = pais;
        this.otro = otro;
        this.uriFoto = uriFoto;
        this.calificacion = calificacion;
        this.alcohol = alcohol;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVariedad() {
        return variedad;
    }

    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getOtro() {
        return otro;
    }

    public void setOtro(String otro) {
        this.otro = otro;
    }

    public byte[] getUriFoto() {
        return uriFoto;
    }

    public void setUriFoto(byte[] uriFoto) {
        this.uriFoto = uriFoto;
    }

    public Float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Float calificacion) {
        this.calificacion = calificacion;
    }

    public Float getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(Float alcohol) {
        this.alcohol = alcohol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Beer beer = (Beer) o;

        if (!nombre.equals(beer.nombre)) return false;
        if (variedad != null ? !variedad.equals(beer.variedad) : beer.variedad != null)
            return false;
        if (pais != null ? !pais.equals(beer.pais) : beer.pais != null) return false;
        if (otro != null ? !otro.equals(beer.otro) : beer.otro != null) return false;
        if (calificacion != null ? !calificacion.equals(beer.calificacion) : beer.calificacion != null)
            return false;
        return alcohol != null ? alcohol.equals(beer.alcohol) : beer.alcohol == null;

    }

    @Override
    public int hashCode() {
        int result = nombre.hashCode();
        result = 31 * result + (variedad != null ? variedad.hashCode() : 0);
        result = 31 * result + (pais != null ? pais.hashCode() : 0);
        result = 31 * result + (otro != null ? otro.hashCode() : 0);
        result = 31 * result + (calificacion != null ? calificacion.hashCode() : 0);
        result = 31 * result + (alcohol != null ? alcohol.hashCode() : 0);
        return result;
    }


}
