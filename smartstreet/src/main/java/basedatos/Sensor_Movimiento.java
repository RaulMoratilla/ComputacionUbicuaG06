package basedatos;

public class Sensor_Movimiento {
    private int _id, _idZonaCalle;
    private String _tipo, _unidadMedida, _nombreCalle;

    //ctor.
    public Sensor_Movimiento(int p_id, String p_tipo, String p_unidadMedida, String p_nombreCalle, int p_idZonaCalle) {
        this._id = p_id;
        this._tipo = p_tipo;
        this._unidadMedida = p_unidadMedida;
        this._nombreCalle = p_nombreCalle;
        this._idZonaCalle = p_idZonaCalle;
    }

    //getters and setters
    public int getId() {
        return _id;
    }

    public void setId(int p_id) {
        this._id = p_id;
    }

    public String getTipo() {
        return _tipo;
    }

    public void setTipo(String p_tipo) {
        this._tipo = p_tipo;
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

    public int getIdZonaCalle() {
        return _idZonaCalle;
    }

    public void setIdZonaCalle(int p_idZonaCalle) {
        this._idZonaCalle = p_idZonaCalle;
    }

    @Override
    public String toString() {
        return "Sensor_Lluvia " + _id + ": {tipo=" + _tipo + ", unidadMedida=" + _unidadMedida + ", nombreCalle=" + _nombreCalle + ", idZonaCalle=" + _idZonaCalle + "}";
    }
    
}
