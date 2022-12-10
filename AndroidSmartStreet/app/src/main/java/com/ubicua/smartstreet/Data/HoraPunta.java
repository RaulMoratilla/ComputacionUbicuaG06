package com.ubicua.smartstreet.Data;

import java.sql.Time;

public class HoraPunta {
    int idZonaCalle, codigoCiudadZonaCalle;
    String nombreCalle;
    Time horaInicio, horaFin;

    public HoraPunta(int idZonaCalle, int codigoCiudadZonaCalle, String nombreCalle, Time horaInicio, Time horaFin) {
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

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }
}
