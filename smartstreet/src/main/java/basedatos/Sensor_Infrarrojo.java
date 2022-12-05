package basedatos;

public class Sensor_Infrarrojo {
    private int _id, _idPasoPeatones;
    private String _unidadMedida, _nombreCallePasoPeatones;

    //ctor.
    public Sensor_Infrarrojo(int p_id, int p_idFarola, String p_unidadMedida, String p_nombreCalleFarola) {
        this._id = p_id;
        this._idPasoPeatones = p_idFarola;
        this._unidadMedida = p_unidadMedida;
        this._nombreCallePasoPeatones = p_nombreCalleFarola;
    }
    
    //getters and setters
    public int getId() {
        return _id;
    }

    public void setId(int p_id) {
        this._id = p_id;
    }

    public int getIdFarola() {
        return _idPasoPeatones;
    }

    public void setIdFarola(int p_idFarola) {
        this._idPasoPeatones = p_idFarola;
    }

    public String getUnidadMedida() {
        return _unidadMedida;
    }

    public void setUnidadMedida(String p_unidadMedida) {
        this._unidadMedida = p_unidadMedida;
    }

    public String getNombreCalleFarola() {
        return _nombreCallePasoPeatones;
    }

    public void setNombreCalleFarola(String p_nombreCalleFarola) {
        this._nombreCallePasoPeatones = p_nombreCalleFarola;
    }

    @Override
    public String toString() {
        return "Sensor_Infrarrojo{" +
                "_id=" + _id +
                ", _idPasoPeatones=" + _idPasoPeatones +
                ", _unidadMedida='" + _unidadMedida + '\'' +
                ", _nombreCallePasoPeatones='" + _nombreCallePasoPeatones + '\'' +
                '}';
    }
}

