package basedatos;

import java.sql.Timestamp;

public class Medicion {
    private int _id, _valor, _idSensor;
    private Timestamp _marcaTemporal;

    //ctor.
    public Medicion(int p_id, int p_valor, Timestamp p_marcaTemporal, int p_idSensor) {
        this._id = p_id;
        this._valor = p_valor;
        this._marcaTemporal = p_marcaTemporal;
        this._idSensor = p_idSensor;
    }

    //getters and setters
    public int getId() {
        return _id;
    }

    public void setId(int p_id) {
        this._id = p_id;
    }

    public int getValor() {
        return _valor;
    }

    public void setValor(int p_valor) {
        this._valor = p_valor;
    }

    public Timestamp getMarcaTemporal() {
        return _marcaTemporal;
    }

    public void setMarcaTemporal(Timestamp p_marcaTemporal) {
        this._marcaTemporal = p_marcaTemporal;
    }

    public int getIdSensor() {
        return _idSensor;
    }

    public void setIdSensor(int p_idSensor) {
        this._idSensor = p_idSensor;
    }

    @Override
    public String toString() {
        return "Medicion{" + "_id=" + _id + ", _valor=" + _valor + ", _marcaTemporal=" + _marcaTemporal + ", _idSensor=" + _idSensor + '}';
    }
}
