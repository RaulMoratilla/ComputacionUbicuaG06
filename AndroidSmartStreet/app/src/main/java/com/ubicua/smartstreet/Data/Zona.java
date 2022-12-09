package com.ubicua.smartstreet.Data;

public class Zona {
    private int id;
    private String nombre;

    public Zona() {

    }

    public Zona(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
