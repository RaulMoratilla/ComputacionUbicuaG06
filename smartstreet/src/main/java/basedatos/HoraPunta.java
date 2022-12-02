package basedatos;
//import datetime
import java.time.LocalTime;

public class HoraPunta {
    private LocalTime _horaInicio, _horaFin;
    private String _nombreCalle, _idZonaCalle, _codigoCiudadCalle;

    //ctor.
    public HoraPunta(LocalTime p_horaInicio, LocalTime p_horaFin, String p_nombreCalle, String p_idZonaCalle, String p_codigoCiudadCalle) {
        this._horaInicio = p_horaInicio;
        this._horaFin = p_horaFin;
        this._nombreCalle = p_nombreCalle;
        this._idZonaCalle = p_idZonaCalle;
        this._codigoCiudadCalle = p_codigoCiudadCalle;
    }

    //getters and setters
    public LocalTime getHoraInicio() {
        return _horaInicio;
    }

    public void setHoraInicio(LocalTime p_horaInicio) {
        this._horaInicio = p_horaInicio;
    }

    public LocalTime getHoraFin() {
        return _horaFin;
    }

    public void setHoraFin(LocalTime p_horaFin) {
        this._horaFin = p_horaFin;
    }

    public String getNombreCalle() {
        return _nombreCalle;
    }

    public void setNombreCalle(String p_nombreCalle) {
        this._nombreCalle = p_nombreCalle;
    }

    public String getIdZonaCalle() {
        return _idZonaCalle;
    }

    public void setIdZonaCalle(String p_idZonaCalle) {
        this._idZonaCalle = p_idZonaCalle;
    }

    public String getCodigoCiudadCalle() {
        return _codigoCiudadCalle;
    }

    public void setCodigoCiudadCalle(String p_codigoCiudadCalle) {
        this._codigoCiudadCalle = p_codigoCiudadCalle;
    }

    //toString
    @Override
    public String toString() {
        return "HoraPunta{" + "_horaInicio=" + _horaInicio + ", _horaFin=" + _horaFin + ", _nombreCalle=" + _nombreCalle + '}';
    }


}
