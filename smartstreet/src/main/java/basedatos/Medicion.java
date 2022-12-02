package basedatos;

public class Medicion {
    private String _idSensor, _tipoSensor, _valor, _marcaTemporal;

    //ctor.
    public Medicion(String p_idSensor, String p_tipoSensor, String p_valor, String p_marcaTemporal) {
        this._idSensor = p_idSensor;
        this._tipoSensor = p_tipoSensor;
        this._valor = p_valor;
        this._marcaTemporal = p_marcaTemporal;
    }

    //getters and setters

    public String get_idSensor() {
        return _idSensor;
    }

    public void set_idSensor(String _idSensor) {
        this._idSensor = _idSensor;
    }

    public String get_tipoSensor() {
        return _tipoSensor;
    }

    public void set_tipoSensor(String _tipoSensor) {
        this._tipoSensor = _tipoSensor;
    }

    public String get_valor() {
        return _valor;
    }

    public void set_valor(String _valor) {
        this._valor = _valor;
    }

    public String get_marcaTemporal() {
        return _marcaTemporal;
    }

    public void set_marcaTemporal(String _marcaTemporal) {
        this._marcaTemporal = _marcaTemporal;
    }

    @Override
    public String toString() {
        return "Medicion{" + "_idSensor=" + _idSensor + ", _tipoSensor=" + _tipoSensor + ", _valor=" + _valor + ", _marcaTemporal=" + _marcaTemporal + '}';
    }
}
