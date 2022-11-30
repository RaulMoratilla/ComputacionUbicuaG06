package basedatos;

public class Sensor_Luz {
    private int _id, _idFarola;
    private String _tipo, _unidadMedida, _nombreCalleFarola;

    //ctor.
    public Sensor_Luz(int p_id, int p_idFarola, String p_tipo, String p_unidadMedida, String p_nombreCalleFarola) {
        this._id = p_id;
        this._idFarola = p_idFarola;
        this._tipo = p_tipo;
        this._unidadMedida = p_unidadMedida;
        this._nombreCalleFarola = p_nombreCalleFarola;
    }
    
    //getters and setters
    public int getId() {
        return _id;
    }

    public void setId(int p_id) {
        this._id = p_id;
    }

    public int getIdFarola() {
        return _idFarola;
    }

    public void setIdFarola(int p_idFarola) {
        this._idFarola = p_idFarola;
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

    public String getNombreCalleFarola() {
        return _nombreCalleFarola;
    }

    public void setNombreCalleFarola(String p_nombreCalleFarola) {
        this._nombreCalleFarola = p_nombreCalleFarola;
    }

    @Override
    public String toString() {
        return "Sensor_Luz " + _id + ": {idFarola=" + _idFarola + ", tipo=" + _tipo + ", unidadMedida=" + _unidadMedida + ", nombreCalleFarola=" + _nombreCalleFarola + "}";
    }
}
