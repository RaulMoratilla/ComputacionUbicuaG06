package basedatos;

public class Topicos {
    private String _idTopico; 
    private String _idCiudad;
    private String _idZona; 
    private String _idCalle;
    private String _idSensores;
    private String _idHoraConf, _idPasoP;
    private String value;

    public Topicos() {
        this._idCalle = null;
        this._idCiudad = null;
        this._idZona = null;
        this._idTopico = null;
        this._idSensores = null;
        this._idPasoP = null;
        this.value = null;
        this._idHoraConf = null;
    }
    
    //ctor.
    public Topicos(String p_idTopico, String p_idCiudad, String p_idZona, String p_idCalle, String p_idSensores, String p_idPasoP, String p_value, String p_idHoraConf) {
        this._idTopico = p_idTopico;
        this._idCiudad = p_idCiudad;
        this._idZona = p_idZona;
        this._idCalle = p_idCalle;
        this._idSensores = p_idSensores;
        this._idPasoP = p_idPasoP;
        this.value = p_value;
        this._idHoraConf = p_idHoraConf;
    }

    //getters and setters
    public String get_idTopico() {
        return _idTopico;
    }

    public void set_idTopico(String _idTopico) {
        this._idTopico = _idTopico;
    }

    public String get_idCiudad() {
        return _idCiudad;
    }

    public void set_idCiudad(String _idCiudad) {
        this._idCiudad = _idCiudad;
    }

    public String get_idZona() {
        return _idZona;
    }

    public void set_idZona(String _idZona) {
        this._idZona = _idZona;
    }

    public String get_idCalle() {
        return _idCalle;
    }

    public void set_idCalle(String _idCalle) {
        this._idCalle = _idCalle;
    }

    public String get_idSensores() {
        return _idSensores;
    }

    public void set_idSensores(String _idSensores) {
        this._idSensores = _idSensores;
    }

    public String get_idPasoP() {
        return _idPasoP;
    }

    public void set_idPasoP(String _idPasoP) {
        this._idPasoP = _idPasoP;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String get_idHoraConf() {
        return _idHoraConf;
    }

    public void set_idHoraConf(String _idHoraConf) {
        this._idHoraConf = _idHoraConf;
    }

    @Override
    public String toString() {
        return "Topicos{" + "_idTopico=" + _idTopico + ", _idCiudad=" + _idCiudad + ", _idZona=" + _idZona + ", _idCalle=" + _idCalle + ", _idSensores=" + _idSensores + ", _idPasoP=" + _idPasoP + ", value=" + value + ", _idHoraConf=" + _idHoraConf + '}';
    }
}
