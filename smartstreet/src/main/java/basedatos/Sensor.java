package basedatos;

import java.sql.Timestamp;

public class Sensor {
    private int _idZonaCalle, _codigoCiudadZonaCalle;
    private String _tipo, _unidadMedida, _nombreCalle;
    private float _valor;
    private Timestamp _marcaTemporal;

    //ctor.
    public Sensor(int p_codigoCiudadZonaCalle, int p_idZonaCalle, String p_nombreCalle, String p_tipo, String p_unidadMedida, Timestamp p_marcaTemporal, float p_valor) {
        this._idZonaCalle = p_idZonaCalle;
        this._tipo = p_tipo;
        this._unidadMedida = p_unidadMedida;
        this._nombreCalle = p_nombreCalle;
        this._valor = p_valor;
        this._codigoCiudadZonaCalle = p_codigoCiudadZonaCalle;
        this._marcaTemporal = p_marcaTemporal;
    }

    //getters and setters
    public int getIdZonaCalle() {
        return _idZonaCalle;
    }

    public void setIdZonaCalle(int p_idZonaCalle) {
        this._idZonaCalle = p_idZonaCalle;
    }

    public String getUnidadMedida() {
        return _unidadMedida;
    }

    public void setUnidadMedida(String p_unidadMedida) {
        this._unidadMedida = p_unidadMedida;
    }

    public String getNombreCalle() {
        return _nombreCalle;
    }

    public void setNombreCalle(String p_nombreCalle) {
        this._nombreCalle = p_nombreCalle;
    }

    public float getValor() {
        return _valor;
    }

    public void setValor(float p_valor) {
        this._valor = p_valor;
    }

    public int getCodigoCiudadZonaCalle() {
        return _codigoCiudadZonaCalle;
    }

    public void setCodigoCiudadZonaCalle(int p_codigoCiudadZonaCalle) {
        this._codigoCiudadZonaCalle = p_codigoCiudadZonaCalle;
    }

    public String getTipo() {
        return _tipo;
    }

    public void setTipo(String p_tipo) {
        this._tipo = p_tipo;
    }

    public Timestamp getMarcaTemporal() {
        return _marcaTemporal;
    }

    public void setMarcaTemporal(Timestamp p_marcaTemporal) {
        this._marcaTemporal = p_marcaTemporal;
    }

    @Override
    public String toString() {
        return "Sensor{" + "_idZonaCalle=" + _idZonaCalle + ", _codigoCiudadZonaCalle=" + _codigoCiudadZonaCalle + ", _tipo=" + _tipo + ", _unidadMedida=" + _unidadMedida + ", _nombreCalle=" + _nombreCalle + ", _valor=" + _valor + ", _marcaTemporal=" + _marcaTemporal + '}';
    }
}
