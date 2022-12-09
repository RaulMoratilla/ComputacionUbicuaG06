package basedatos;

public class Calle {
    private String _nombre;
    private int _codigoCiudadZona, _idZona;

    //ctor.
    public Calle(int p_codigoCiudad, int p_codigoZona, String p_nombre) {
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

    public int getCodigoCiudad() {
        return _codigoCiudadZona;
    }

    public void setCodigoCiudad(int p_codigoCiudad) {
        this._codigoCiudadZona = p_codigoCiudad;
    }

    public int getCodigoZona() {
        return _idZona;
    }

    public void setCodigoZona(int p_codigoZona) {
        this._idZona = p_codigoZona;
    }

    @Override
    public String toString() {
        return "Calle " + _nombre + ": {codigoCiudad=" + _codigoCiudadZona + ", codigoZona=" + _idZona + "}";
    }
}
