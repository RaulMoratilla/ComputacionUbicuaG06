package com.ubicua.smartstreet.Data;

import java.sql.Timestamp;

public class Sensor {
    int codigoCiudadZonaCalle, idZonaCalle;
    double valor;
    String nombreCalle, tipo, unidadMedida;
    Timestamp marcaTemporal;

    public Sensor(int codigoCiudadZonaCalle, int idZonaCalle, double valor, String nombreCalle, String tipo, String unidadMedida) {
        this.codigoCiudadZonaCalle = codigoCiudadZonaCalle;
        this.idZonaCalle = idZonaCalle;
        this.valor = valor;
        this.nombreCalle = nombreCalle;
        this.tipo = tipo;
        this.unidadMedida = unidadMedida;
        this.marcaTemporal = marcaTemporal;
    }

    public int getCodigoCiudadZonaCalle() {
        return codigoCiudadZonaCalle;
    }

    public void setCodigoCiudadZonaCalle(int codigoCiudadZonaCalle) {
        this.codigoCiudadZonaCalle = codigoCiudadZonaCalle;
    }

    public int getIdZonaCalle() {
        return idZonaCalle;
    }

    public void setIdZonaCalle(int idZonaCalle) {
        this.idZonaCalle = idZonaCalle;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getNombreCalle() {
        return nombreCalle;
    }

    public void setNombreCalle(String nombreCalle) {
        this.nombreCalle = nombreCalle;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Timestamp getMarcaTemporal() {
        return marcaTemporal;
    }

    public void setMarcaTemporal(Timestamp marcaTemporal) {
        this.marcaTemporal = marcaTemporal;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "codigoCiudadZonaCalle=" + codigoCiudadZonaCalle +
                ", idZonaCalle=" + idZonaCalle +
                ", valor=" + valor +
                ", nombreCalle='" + nombreCalle + '\'' +
                ", tipo='" + tipo + '\'' +
                ", unidadMedida='" + unidadMedida + '\'' +
                ", marcaTemporal=" + marcaTemporal +
                '}';
    }
}
