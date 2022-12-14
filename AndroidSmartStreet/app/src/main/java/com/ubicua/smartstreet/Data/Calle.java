package com.ubicua.smartstreet.Data;

public class Calle {
    private int idZona, codigoCiudadZona;
    private String nombre;

    public Calle(int id, int codigoCiudadZona, String nombre) {
        this.idZona = id;
        this.codigoCiudadZona = codigoCiudadZona;
        this.nombre = nombre;
    }

    public int getId()
    {
        return idZona;
    }

    public void setId(int id)
    {
        this.idZona = id;
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
