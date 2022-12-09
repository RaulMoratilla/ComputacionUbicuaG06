package basedatos;

public class Topicos {
    private String _idTopico, _idCiudad, _idZona, _idCalle;
    private String _idLuz, _idInfra, _idMov, _idLluv, _idTemp;
    private String value;

    public Topicos() {
        this._idCalle = null;
        this._idCiudad = null;
        this._idZona = null;
        this._idTopico = null;
        this._idLuz = null;
        this._idInfra = null;
        this._idMov = null;
        this._idLluv = null;
        this._idTemp = null;
        this.value = null;
    }
    
    //ctor.
    public Topicos(String p_idTopico, String p_idCiudad, String p_idZona, String p_idCalle, String p_idSLuz, String p_idSInfra, String p_idSMov, String p_idSLluv, String p_idSTemp, String p_value) {
        this._idTopico = p_idTopico;
        this._idCiudad = p_idCiudad;
        this._idZona = p_idZona;
        this._idCalle = p_idCalle;
        this._idLuz = p_idSLuz;
        this._idInfra = p_idSInfra;
        this._idMov = p_idSMov;
        this._idLluv = p_idSLluv;
        this._idTemp = p_idSTemp;
        this.value = p_value;
    }

    //getters and setters
    public String getIdTopico() {
        return _idTopico;
    }

    public void setIdTopico(String p_idTopico) {
        this._idTopico = p_idTopico;
    }

    public String getIdCiudad() {
        return _idCiudad;
    }

    public void setIdCiudad(String p_idCiudad) {
        this._idCiudad = p_idCiudad;
    }

    public String getIdZona() {
        return _idZona;
    }

    public void setIdZona(String p_idZona) {
        this._idZona = p_idZona;
    }

    public String getIdCalle() {
        return _idCalle;
    }

    public void setIdCalle(String p_idCalle) {
        this._idCalle = p_idCalle;
    }

    public String getIdSLuz() {
        return _idLuz;
    }

    public void setIdSLuz(String p_idSLuz) {
        this._idLuz = p_idSLuz;
    }

    public String getIdSInfra() {
        return _idInfra;
    }

    public void setIdSInfra(String p_idSInfra) {
        this._idInfra = p_idSInfra;
    }

    public String getIdSMov() {
        return _idMov;
    }

    public void setIdSMov(String p_idSMov) {
        this._idMov = p_idSMov;
    }

    public String getIdSLluv() {
        return _idLluv;
    }

    public void setIdSLluv(String p_idSLluv) {
        this._idLluv = p_idSLluv;
    }

    public String getIdSTemp() {
        return _idTemp;
    }

    public void setIdSTemp(String p_idSTemp) {
        this._idTemp = p_idSTemp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String p_value) {
        this.value = p_value;
    }

    @Override
    public String toString() {
        return "Topicos{" + "_idTopico=" + _idTopico + ", _idCiudad=" + _idCiudad + ", _idZona=" + _idZona + ", _idCalle=" + _idCalle + ", _idSLuz=" + _idLuz + ", _idSInfra=" + _idInfra + ", _idSMov=" + _idMov + ", _idSLluv=" + _idLluv + ", _idSTemp=" + _idTemp + ", value=" + value + '}';
    }

}
