package com.ubicua.smartstreet.Data;

public class Zona {
    private int id,codigoCiudad;
    private String nombre;

    public Zona(int id, int codigoCiudad, String nombre) {
        this.id = id;
        this.codigoCiudad = codigoCiudad;
        this.nombre = nombre;
    }

    public Zona(int id, String name) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigoCiudad() {
        return codigoCiudad;
    }

    public void setCodigoCiudad(int codigoCiudad) {
        this.codigoCiudad = codigoCiudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
