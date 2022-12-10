package com.ubicua.smartstreet.Data;

public class Calle {
    private int id, codigoCiudadZona;
    String nombre;

    public Calle(int id, int codigoCiudadZona, String nombre) {
        this.id = id;
        this.codigoCiudadZona = codigoCiudadZona;
        this.nombre = nombre;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getCodigoCiudadZona()
    {
        return codigoCiudadZona;
    }

    public void setCodigoCiudadZona(int codigoCiudadZona)
    {
        this.codigoCiudadZona = codigoCiudadZona;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
}
