package basedatos;
import java.sql.Time;

public class HoraPunta {
    private Time _horaInicio, _horaFin;
    private String _nombreCalle;
    private int _idZonaCalle, _codigoCiudadCalle;
    private boolean _fijo;

    //ctor.
    public HoraPunta(int p_codigoCiudadCalle, int p_idZonaCalle, String p_nombreCalle,  Time p_horaInicio, Time p_horaFin, boolean p_fijo) {
        this._horaInicio = p_horaInicio;
        this._horaFin = p_horaFin;
        this._nombreCalle = p_nombreCalle;
        this._idZonaCalle = p_idZonaCalle;
        this._codigoCiudadCalle = p_codigoCiudadCalle;
        this._fijo = p_fijo;
    }

    //getters and setters
    public Time getHoraInicio() {
        return _horaInicio;
    }

    public void setHoraInicio(Time p_horaInicio) {
        this._horaInicio = p_horaInicio;
    }

    public Time getHoraFin() {
        return _horaFin;
    }

    public void setHoraFin(Time p_horaFin) {
        this._horaFin = p_horaFin;
    }

    public String getNombreCalle() {
        return _nombreCalle;
    }

    public void setNombreCalle(String p_nombreCalle) {
        this._nombreCalle = p_nombreCalle;
    }

    public int getIdZonaCalle() {
        return _idZonaCalle;
    }

    public void setIdZonaCalle(int p_idZonaCalle) {
        this._idZonaCalle = p_idZonaCalle;
    }

    public int getCodigoCiudadCalle() {
        return _codigoCiudadCalle;
    }

    public void setCodigoCiudadCalle(int p_codigoCiudadCalle) {
        this._codigoCiudadCalle = p_codigoCiudadCalle;
    }

    public boolean isFijo() {
        return _fijo;
    }

    public void setFijo(boolean p_fijo) {
        this._fijo = p_fijo;
    }

    //toString
    @Override
    public String toString() {
        return "HoraPunta{" + "_horaInicio=" + _horaInicio + ", _horaFin=" + _horaFin + ", _nombreCalle=" + _nombreCalle + '}';
    }


}
