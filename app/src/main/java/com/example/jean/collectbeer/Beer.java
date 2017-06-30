package com.example.jean.collectbeer;

import java.sql.Timestamp;

/**
 * Created by Jean on 27-06-2017.
 */

public class Beer {
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
}
