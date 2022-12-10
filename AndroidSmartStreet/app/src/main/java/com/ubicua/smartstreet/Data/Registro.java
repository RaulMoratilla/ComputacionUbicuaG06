package com.ubicua.smartstreet.Data;

import java.sql.Timestamp;

public class Registro {
    int codigoCiudadZonaCalleSensor, idZonaCalleSensor;
    float valor;
    String nombreCalleSensor, tipoSensor,unidadMedida;
    Timestamp marcaTemporal;

    public Registro(int codigoCiudadZonaCalleSensor, int idZonaCalleSensor, float valor, String nombreCalleSensor, String tipoSensor, String unidadMedida, Timestamp marcaTemporal) {
        this.codigoCiudadZonaCalleSensor = codigoCiudadZonaCalleSensor;
        this.idZonaCalleSensor = idZonaCalleSensor;
        this.valor = valor;
        this.nombreCalleSensor = nombreCalleSensor;
        this.tipoSensor = tipoSensor;
        this.unidadMedida = unidadMedida;
        this.marcaTemporal = marcaTemporal;
    }

    public int getCodigoCiudadZonaCalleSensor() {
        return codigoCiudadZonaCalleSensor;
    }

    public void setCodigoCiudadZonaCalleSensor(int codigoCiudadZonaCalleSensor) {
        this.codigoCiudadZonaCalleSensor = codigoCiudadZonaCalleSensor;
    }

    public int getIdZonaCalleSensor() {
        return idZonaCalleSensor;
    }

    public void setIdZonaCalleSensor(int idZonaCalleSensor) {
        this.idZonaCalleSensor = idZonaCalleSensor;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getNombreCalleSensor() {
        return nombreCalleSensor;
    }

    public void setNombreCalleSensor(String nombreCalleSensor) {
        this.nombreCalleSensor = nombreCalleSensor;
    }

    public String getTipoSensor() {
        return tipoSensor;
    }

    public void setTipoSensor(String tipoSensor) {
        this.tipoSensor = tipoSensor;
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
}
