package basedatos;

public class Ciudad {
    private int _codigo;
    private String _nombre, _pais;

    //ctor.

    
    public Ciudad(int p_codigo, String p_nombre, String p_pais) {
        this._codigo = p_codigo;
        this._nombre = p_nombre;
        this._pais = p_pais;
    }

    //getters and setters
    public int getCodigo() {
        return _codigo;
    }

    public void setCodigo(int p_codigo) {
        this._codigo = p_codigo;
    }

    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String p_nombre) {
        this._nombre = p_nombre;
    }

    public String getPais() {
        return _pais;
    }

    public void setPais(String p_pais) {
        this._pais = p_pais;
    }

    @Override
    public String toString() {
        return "Ciudad " + _codigo + ": {pais=" + _pais + ", nombre=" + _nombre + "}";
    }
}
