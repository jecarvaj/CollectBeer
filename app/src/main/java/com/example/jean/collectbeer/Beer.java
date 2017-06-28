package com.example.jean.collectbeer;

import java.sql.Timestamp;

/**
 * Created by Jean on 27-06-2017.
 */

public class Beer {
    private int id;
    private Timestamp fecha;
    private String nombre, variedad,  pais, otro,  uriFoto;
    private Float calificacion, alcohol;

    public Beer() {
    }

    public Beer(String nombre, String variedad, String pais, Float alcohol, String otro, String uriFoto, Float calificacion) {
        this.nombre = nombre;
        this.variedad = variedad;
        this.pais = pais;
        this.alcohol = alcohol;
        this.otro = otro;
        this.uriFoto = uriFoto;
        this.calificacion = calificacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
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

    public Float getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(Float alcohol) {
        this.alcohol = alcohol;
    }

    public String getOtro() {
        return otro;
    }

    public void setOtro(String otro) {
        this.otro = otro;
    }

    public String getUriFoto() {
        return uriFoto;
    }

    public void setUriFoto(String uriFoto) {
        this.uriFoto = uriFoto;
    }

    public Float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Float calificacion) {
        this.calificacion = calificacion;
    }
}
