package com.ubicua.smartstreet.Data;

import java.sql.Time;

public class HoraPunta {
    private int idZonaCalle, codigoCiudadZonaCalle;
    private String nombreCalle;
    private String horaInicio, horaFin;
    private boolean fijo;

    public HoraPunta(int idZonaCalle, int codigoCiudadZonaCalle, String nombreCalle, String horaInicio, String horaFin, boolean fijo) {
        this.idZonaCalle = idZonaCalle;
        this.codigoCiudadZonaCalle = codigoCiudadZonaCalle;
        this.nombreCalle = nombreCalle;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public int getIdZonaCalle() {
        return idZonaCalle;
    }

    public void setIdZonaCalle(int idZonaCalle) {
        this.idZonaCalle = idZonaCalle;
    }

    public int getCodigoCiudadZonaCalle() {
        return codigoCiudadZonaCalle;
    }

    public void setCodigoCiudadZonaCalle(int codigoCiudadZonaCalle) {
        this.codigoCiudadZonaCalle = codigoCiudadZonaCalle;
    }

    public String getNombreCalle() {
        return nombreCalle;
    }

    public void setNombreCalle(String nombreCalle) {
        this.nombreCalle = nombreCalle;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
}
