package basedatos;

public class Calle {
    private String _nombre;
    private String _codigoCiudadZona, _idZona;

    //ctor.
    public Calle(int p_id, String p_nombre, String p_codigoCiudad, String p_codigoZona) {
        this._nombre = p_nombre;
        this._codigoCiudadZona = p_codigoCiudad;
        this._idZona = p_codigoZona;
    }

    //getters and setters
    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String p_nombre) {
        this._nombre = p_nombre;
    }

    public String getCodigoCiudad() {
        return _codigoCiudadZona;
    }

    public void setCodigoCiudad(String p_codigoCiudad) {
        this._codigoCiudadZona = p_codigoCiudad;
    }

    public String getCodigoZona() {
        return _idZona;
    }

    public void setCodigoZona(String p_codigoZona) {
        this._idZona = p_codigoZona;
    }

    @Override
    public String toString() {
        return "Calle " + _nombre + ": {codigoCiudad=" + _codigoCiudadZona + ", codigoZona=" + _idZona + "}";
    }
}
