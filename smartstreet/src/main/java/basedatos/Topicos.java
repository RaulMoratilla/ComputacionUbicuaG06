package basedatos;

public class Topicos {
    private String _idTopico, _idCiudad, _idZona, _idCalle;
    private String _idPPeat, _idSenal, _idFarola;
    private String _idSLuz, _idSInfra, _idSMov, _idSLluv, _idSTemp;
    private String value;

    public Topicos() {
        this._idCalle = null;
        this._idCiudad = null;
        this._idZona = null;
        this._idTopico = null;
        this._idPPeat = null;
        this._idSenal = null;
        this._idFarola = null;
        this._idSLuz = null;
        this._idSInfra = null;
        this._idSMov = null;
        this._idSLluv = null;
        this._idSTemp = null;
        this.value = null;
    }
    
    //ctor.
    public Topicos(String p_idTopico, String p_idCiudad, String p_idZona, String p_idCalle, String p_idPPeat, String p_idSenal, String p_idFarola, String p_idSLuz, String p_idSInfra, String p_idSMov, String p_idSLluv, String p_idSTemp, String p_value) {
        this._idTopico = p_idTopico;
        this._idCiudad = p_idCiudad;
        this._idZona = p_idZona;
        this._idCalle = p_idCalle;
        this._idPPeat = p_idPPeat;
        this._idSenal = p_idSenal;
        this._idFarola = p_idFarola;
        this._idSLuz = p_idSLuz;
        this._idSInfra = p_idSInfra;
        this._idSMov = p_idSMov;
        this._idSLluv = p_idSLluv;
        this._idSTemp = p_idSTemp;
        this.value = p_value;
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

    public String get_idPPeat() {
        return _idPPeat;
    }

    public void set_idPPeat(String _idPPeat) {
        this._idPPeat = _idPPeat;
    }

    public String get_idSenal() {
        return _idSenal;
    }

    public void set_idSenal(String _idSenal) {
        this._idSenal = _idSenal;
    }

    public String get_idFarola() {
        return _idFarola;
    }

    public void set_idFarola(String _idFarola) {
        this._idFarola = _idFarola;
    }

    public String get_idSLuz() {
        return _idSLuz;
    }

    public void set_idSLuz(String _idSLuz) {
        this._idSLuz = _idSLuz;
    }

    public String get_idSInfra() {
        return _idSInfra;
    }

    public void set_idSInfra(String _idSInfra) {
        this._idSInfra = _idSInfra;
    }

    public String get_idSMov() {
        return _idSMov;
    }

    public void set_idSMov(String _idSMov) {
        this._idSMov = _idSMov;
    }

    public String get_idSLluv() {
        return _idSLluv;
    }

    public void set_idSLluv(String _idSLluv) {
        this._idSLluv = _idSLluv;
    }

    public String get_idSTemp() {
        return _idSTemp;
    }

    public void set_idSTemp(String _idSTemp) {
        this._idSTemp = _idSTemp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Topicos{" + "_idTopico=" + _idTopico + ", _idCiudad=" + _idCiudad + ", _idZona=" + _idZona + ", _idCalle=" + _idCalle + ", _idPPeat=" + _idPPeat + ", _idSenal=" + _idSenal + ", _idFarola=" + _idFarola + ", _idSLuz=" + _idSLuz + ", _idSInfra=" + _idSInfra + ", _idSMov=" + _idSMov + ", _idSLluv=" + _idSLluv + ", _idSTemp=" + _idSTemp + ", value=" + value + '}';
    }

}
