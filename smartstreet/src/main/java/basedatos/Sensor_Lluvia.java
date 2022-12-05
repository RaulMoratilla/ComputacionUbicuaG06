package basedatos;

public class Sensor_Lluvia {
    private int _id, _idZonaCalle;
    private String _unidadMedida, _nombreCalle;

    //ctor.
    public Sensor_Lluvia(int p_id, String p_unidadMedida, String p_nombreCalle, int p_idZonaCalle) {
        this._id = p_id;
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
        return "Sensor_Lluvia{" +
                "_id=" + _id +
                ", _idZonaCalle=" + _idZonaCalle +
                ", _unidadMedida='" + _unidadMedida + '\'' +
                ", _nombreCalle='" + _nombreCalle + '\'' +
                '}';
    }
}
